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
            if (env.BRANCH_NAME == "master") {
                println "Executing $step from $env.BRANCH_NAME!"
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
