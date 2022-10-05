node {
    checkout scm
    environment {
        FULL_PATH_BRANCH = "${sh(script:'git name-rev --name-only HEAD', returnStdout: true)}"
        GIT_BRANCH = FULL_PATH_BRANCH.substring(FULL_PATH_BRANCH.lastIndexOf('/') + 1, FULL_PATH_BRANCH.length())
    }
    println("Current Branch - $GIT_BRANCH")

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
