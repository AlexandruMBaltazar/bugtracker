import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def buildAll(command) {
    def mvn_version = 'Maven'
    withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
        sh "$command"
    }
}

def runServiceStep(service, command) {
    dir("services/$service") {
        def mvn_version = 'Maven'
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
            sh "$command"
        }
    }
}

def runSharedStep(command) {
    sh "make $command"
}

def execute(step, services) {
    def availableServices = load "$env.WORKSPACE/common/jenkins/availableServices.groovy"
    if (services === availableServices() && step === "Build") {
        stage(step.name) {
            buildAll(step.command)
        }
    } else {
        stage(step.name) {
            if (step.shared) {
                runSharedStep(step.command)
            } else {
                parallel services.collectEntries {service -> [service, {runServiceStep(service, step.command)}]}
            }
        }
    }
}

return { services, pipeline -> pipeline.each {step -> execute(step, services) } }