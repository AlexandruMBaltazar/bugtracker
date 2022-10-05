import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
import groovy.lang.Binding

def runForAllServices(command, step) {
    def mvn_version = 'Maven'
    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REGISTRY_PASSWORD', usernameVariable: 'REGISTRY_USERNAME')]) {
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
            executions(command, step, env.BRANCH_NAME)
        }
    }
}

def runForIndividualServices(service, command, step) {
    dir("services/$service") {
        def mvn_version = 'Maven'
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REGISTRY_PASSWORD', usernameVariable: 'REGISTRY_USERNAME')]) {
            withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                executions(command, step, env.BRANCH_NAME)
            }
        }
    }
}

def runSharedStep(command) {
    sh "make $command"
}

def executions(command, step, currentBranch) {
    switch (step) {
        case "Deploy":
            println "Executing $step from $currentBranch!"
            if (currentBranch == "master") {
                println "Executing $step from $currentBranch"
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

def execute(step, services) {
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
