plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
    `java-library`
    signing
}

group = "io.github.integr-0"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "regex-builder"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name = "Regex Builder"
                description = "Write Regex patterns in a more readable way"
                url = "https://github.com/Integr-0/RegexBuilder"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "johnd"
                        name = "John Doe"
                        email = "john.doe@example.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/Integr-0/RegexBuilder.git"
                    developerConnection = "scm:git:ssh://github.com/Integr-0/RegexBuilder.git"
                    url = "https://github.com/Integr-0/RegexBuilder"
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}