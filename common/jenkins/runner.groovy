import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
import groovy.lang.Binding

def runForAllServices(command, step) {
    def mvn_version = 'Maven'
    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REGISTRY_PASSWORD', usernameVariable: 'REGISTRY_USERNAME')]) {
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
            executions(command, step)
        }
    }
}

def runForIndividualServices(service, command, step) {
    dir("services/$service") {
        def mvn_version = 'Maven'
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REGISTRY_PASSWORD', usernameVariable: 'REGISTRY_USERNAME')]) {
            withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                executions(command, step)
            }
        }
    }
}

def runSharedStep(command) {
    sh "make $command"
}

def executions(command, step) {
    switch (step) {
        case "Deploy":
            println "Executing $step!"
            if (getGitBranchName() == "master") {
                println "Executing $step from $env.GIT_BRANCH"
                sh "$command"
            }
            break
        case "Build":
            println "Executing $step!"
            sh "$command"
            break
        default:
            println "$step Executed Successfully!"
            break
    }
}

def getGitBranchName() {
    def branch_nem = scm.branches[0].name
    if (branch_nem.contains("*/")) {
        branch_nem = branch_nem.split("\\*/")[1]
    }
    return branch_nem
}

def execute(step, services) {
    println "CURRENT BRANCH: "
    println getGitBranchName()
    def availableServices = load "$env.WORKSPACE/common/jenkins/availableServices.groovy"
    if (services == availableServices()) {
        stage(step.name) {
            runForAllServices(step.command, step.name)
        }
    } else {
        stage(step.name) {
            if (step.shared) {
                runSharedStep(step.command)
            } else {
                parallel services.collectEntries {service -> [service, {runForIndividualServices(service, step.command, step.name)}]}
            }
        }
    }
}

return { services, pipeline -> pipeline.each {step -> execute(step, services) } }
