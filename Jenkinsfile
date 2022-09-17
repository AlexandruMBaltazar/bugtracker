properties([pipelineTriggers([pollSCM('* * * * *')])])

node {
    checkout scm
    println("CURRENT JAVA VERSION: ")
    sh "java --version"

    def analyzeChanges = load "$env.WORKSPACE/common/jenkins/analyzeChanges.groovy"

    def servicesToRun = analyzeChanges()
    def availableServices = load "$env.WORKSPACE/common/jenkins/availableServices.groovy"
    println("Running: $servicesToRun")
    println("Available Services: $availableServices")

    def pipeline = load "$env.WORKSPACE/common/jenkins/pipeline.groovy"
    def runner = load "$env.WORKSPACE/common/jenkins/runner.groovy"

    runner(servicesToRun, pipeline)    
}