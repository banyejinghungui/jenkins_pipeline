import jenkins.model.*
import com.chance.jobdsl
import groovy.transform.Field

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
    String vah = AntJob.virutalAddressHandle("${antApp.qualifier}")
    String app_jdk = AntJob.jdkVersion("${antApp.qualifier}")
    String environment = AntJob.appEnvironment("${antApp.qualifier}")
    String environment_folder = AntJob.appEnvironmentFolder("${antApp.qualifier}")


    folder("${environment}")

    pipelineJob("${environment_folder}/${artifact_name}-pipeline") {
        definition {
                cpsScm {
                    scm {
                        JobUtils.addGitRepo_chance(delegate, "develop", delegate)
                        scriptPath("pipeline-scripts/first_pipeline")

                    }
                }
        }
    }

}

