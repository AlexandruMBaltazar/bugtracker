import jenkins.model.*;
import hudson.model.*;
import hudson.util.*;
import hudson.tasks.*;
import hudson.plugins.git.*;
import hudson.scm.*
import jenkins.scm.*
import groovy.transform.Field
final def config = load "$WORKSPACE/common/jenkins/config.groovy"

def getFilesChanged(chgSets) {
  def filesList = []
  def changeLogSets = chgSets
  for (int i = 0; i < changeLogSets.size(); i++) {
    def entries = changeLogSets[i].items
    for (int j = 0; j < entries.length; j++) {
      def entry = entries[j]
      def files = new ArrayList(entry.affectedFiles)
      for (int k = 0; k < files.size(); k++) {
        def file = files[k]
        filesList.add(file.path)
      }
    }
  }
  return filesList
}

def allChangeSetsFromLastSuccessfulBuild() {
  println "JOB NAME:"
  println env.JOB_NAME
  def job = Jenkins.instance.getItem(env.JOB_NAME)
  def lastSuccessBuild = job.lastSuccessfulBuild.number as int
  def currentBuildId = "$BUILD_ID" as int

  def changeSets = []

  for(int i = lastSuccessBuild + 1; i < currentBuildId; i++) {
    echo "Getting Change Set for the Build ID : ${i}"
    def chageSet = job.getBuildByNumber(i).getChangeSets()
    changeSets.addAll(chageSet)
  }
  changeSets.addAll(currentBuild.changeSets) // Add the current Changeset
  return changeSets
}

def changedFilesSinceLastPass() {
    def files = []
    def lastSuccessfulBuildNumber = currentBuild.previousSuccessfulBuild?.number

  if (lastSuccessfulBuildNumber == null) {
    println 'No successful builds in history. Building everything'
    return []
  }

  println "Fetching changed files since build: $lastSuccessfulBuildNumber"

  def build = currentBuild
  while (build.number > lastSuccessfulBuildNumber) {
    files = getFilesChanged(allChangeSetsFromLastSuccessfulBuild())
    build = build.getPreviousBuild()
  }
  files.unique()
}

def shouldRunAll(files, runAllLocations) {
  def hasChangesInCommon = files.any { file -> runAllLocations.any { file.matches("$it.*") } }
  def hasChangesInRoot = files.any { !it.contains('/') }
  print "hasChangesInCommon: $hasChangesInCommon, hasChangesInRoot: $hasChangesInRoot, files.isEmpty(): ${files.isEmpty()}"
  hasChangesInCommon || hasChangesInRoot || files.isEmpty()
}

def changedServices(allServices, changedFiles, servicesFolder) {
  allServices.findAll { service -> changedFiles.any { it.contains("$servicesFolder/$service") } }
}

return {
  def availableServices = load "$env.WORKSPACE/common/jenkins/availableServices.groovy"
  def allServices = availableServices()
  def changedFiles = changedFilesSinceLastPass()

    shouldRunAll(changedFiles, config['runAllLocations']) ? allServices : changedServices(allServices, changedFiles, config['servicesFolder'])
}
