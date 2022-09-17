properties([pipelineTriggers([pollSCM('* * * * *')])])

node {
    checkout scm
    println("CURRENT JAVA VERSION: ")
    sh "java --version"

    def analyzeChanges = load "$env.WORKSPACE/common/jenkins/analyzeChanges.groovy"

    println("Analyzing Changes...")
    def servicesToRun = analyzeChanges()
    println("Finished Analyzing Changes")

    println("Running: $servicesToRun")

    def pipeline = load "$env.WORKSPACE/common/jenkins/pipeline.groovy"
    def runner = load "$env.WORKSPACE/common/jenkins/runner.groovy"

    println("Run Pipeline...")
    runner(servicesToRun, pipeline)    
}