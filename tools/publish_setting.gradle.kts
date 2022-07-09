apply(plugin = "maven-publish")

project.group = "com.kash"
project.version = "0.0.4"

configure<PublishingExtension> {
    repositories {
        maven {
            name = "KashPackage"
            url = uri("https://maven.pkg.github.com/DonizeteVida/kash")
            credentials {
                username = getCredentialEnvironment(keyEnvironment = "ENV_KASH_USERNAME", keyGlobalProperties = "KASH_USERNAME")
                password = getCredentialEnvironment(keyEnvironment = "ENV_KASH_PASSWORD", keyGlobalProperties = "KASH_PASSWORD")
            }
        }

        publications {
            create<MavenPublication>(project.name) {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                artifact("$buildDir/libs/${project.name}-${project.version}.jar")
            }
        }
    }
}

fun getCredentialEnvironment(
    keyEnvironment: String,
    keyGlobalProperties: String
) : String {
    val environment : String? = System.getenv(keyEnvironment)
    val global = project.findProperty(keyGlobalProperties) as String? ?: String()

    if (environment.isNullOrEmpty() && global.isEmpty()) {
        throw kotlin.Exception("credentials $keyEnvironment and $keyGlobalProperties not found")
    }

    return environment ?: global
}