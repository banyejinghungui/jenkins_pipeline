import jenkins.model.*
class AntJob {
    static String artifactName(String qualifier) {
        qualifier.split(/-/)[0]
    }
    static String virutalAddressHandle(String qualifier) {
        qualifier.split(/-/)[1]
    }
    static String jdkVersion(String qualifier) {
        qualifier.split(/-/)[2]
    }
    static String appEnvironment(String qualifier) {
        qualifier.split(/-/)[3]
    }
    static String appEnvironmentFolder(String qualifier) {
        qualifier.split(/-/)[4]
    }
}

[
        [qualifier: "ChanceWeb-chanceweb-jdk8u192-DEV-DEV"],
        [qualifier: "ChanceWeb-chanceweb-jdk8u193-QAT-QAT"]
].each { Map antApp ->
    for (computer in Jenkins.instance.getComputers().name) {
        jenkins_hostname = computer
    }

    String artifact_name = AntJob.artifactName("${antApp.qualifier}")
    String vah = AntJob.virtualAddressHandle("${antApp.qualifier}")
    String environment = AntJob.appEnvironment("${antApp.qualifier}")
    String environment_folder = AntJob.appEnviromentFolder("${antApp.qualifier}")
    String app_jdk = AntJob.jdkVersion("${antApp.qualifier}")

    folder("${environment}")

    pipelineJob("${artifact_name}-pipeline") {
        description{
            scriptPath("pipeline-scripts/first_pipeline")
        }
    }

}

