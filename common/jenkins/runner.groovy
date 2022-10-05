import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
import groovy.lang.Binding

def runForAllServices(command, step) {
    def mvn_version = 'Maven'
    if (env.BRANCH_NAME == "master" && step == "Deploy") {
        authenticateToDocker()
        withEnv(["PATH+MAVEN=${tool mvn_version}/bin"]) {
            sh "$command"
        }
    } else {
        withEnv(["PATH+MAVEN=${tool mvn_version}/bin"]) {
            sh "$command"
        }
    }
}

def runForIndividualServices(service, command, step) {
    dir("services/$service") {
        def mvn_version = 'Maven'
        if (env.BRANCH_NAME == "master" && step == "Deploy") {
            authenticateToDocker()
            withEnv(["PATH+MAVEN=${tool mvn_version}/bin"]) {
                sh "$command"
            }
        } else {
            withEnv(["PATH+MAVEN=${tool mvn_version}/bin"]) {
                sh "$command"
            }
        }
    }
}

def authenticateToDocker() {
    withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REGISTRY_PASSWORD', usernameVariable: 'REGISTRY_USERNAME')]) {
        sh "docker login -u ${env.REGISTRY_USERNAME} -p ${env.REGISTRY_PASSWORD}"
    }
}

def runSharedStep(command) {
    sh "make $command"
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
