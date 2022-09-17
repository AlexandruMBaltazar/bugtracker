properties([pipelineTriggers([pollSCM('* * * * *')])])

node {
    checkout scm
    println("CURRENT JAVA VERSION: ")
    sh "java --version"

    def availableServices = load "$env.WORKSPACE/common/jenkins/availableServices.groovy"
    println("Current Services: $availableServices")

    def analyzeChanges = load "$env.WORKSPACE/common/jenkins/analyzeChanges.groovy"

    println("Analyzing Changes...:")
    def servicesToRun = analyzeChanges()

    println("Running: $servicesToRun")

    def pipeline = load "$env.WORKSPACE/common/jenkins/pipeline.groovy"
    def runner = load "$env.WORKSPACE/common/jenkins/runner.groovy"

    runner(servicesToRun, pipeline)    
}