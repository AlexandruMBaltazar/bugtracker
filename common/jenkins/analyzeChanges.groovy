import groovy.transform.Field
final def config = load "$WORKSPACE/common/jenkins/config.groovy"

def listFilesForBuild(build) {
  def files = []
  build.changeSets.each {
    it.items.each {
      it.affectedFiles.each {
        files << it.path
      }
    }
  }
  def currentBuild = build
  println "Build: $currentBuild"
  files
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
    files += listFilesForBuild(build)
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
