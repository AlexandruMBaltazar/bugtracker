import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
import groovy.lang.Binding

def runForAllServices(command, step) {
    def mvn_version = 'Maven'
    withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
        sh "$command"
        deploy(command, step)
    }
}

def runForIndividualServices(service, command, step) {
    dir("services/$service") {
        def mvn_version = 'Maven'
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
            sh "$command"
            deploy(command, step)
        }
    }
}

def runSharedStep(command) {
    sh "make $command"
}

def deploy(command, step) {
    if (env.BRANCH_NAME == "master" && step == "Deploy") {
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'REGISTRY_PASSWORD', usernameVariable: 'REGISTRY_USERNAME')]) {
            sh "$command"
        }
    }
}

def execute(step, services) {
    def availableServices = load "$env.WORKSPACE/common/jenkins/availableServices.groovy"
    if (services == availableServices()) {
        stage(step.name) {
            withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
            }
            runForAllServices(step.command, step.name)
        }
    } else {
        stage(step.name) {
            withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
            }
            if (step.shared) {
                runSharedStep(step.command)
            } else {
                parallel services.collectEntries {service -> [service, {runForIndividualServices(service, step.command, step.name)}]}
            }
        }
    }
}

return { services, pipeline -> pipeline.each {step -> execute(step, services) } }
