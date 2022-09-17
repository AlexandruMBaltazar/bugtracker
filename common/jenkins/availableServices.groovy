final def config = load "$WORKSPACE/common/jenkins/config.groovy"

def listServices(servicesFolder) {
    def services = sh(script: "ls -1 $WORKSPACE/$servicesFolder/", returnStdout: true)
            .split()
            .findAll {!it.endsWith('@tmp')}
    println "Availabe services:\n*${services.join('\n*')}"
    services
}

return {
    def allServices = listServices(config['servicesFolder'])
    allServices
}