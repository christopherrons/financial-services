plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-library")
    id("org.hidetake.ssh") version "2.10.1"
    id("maven-publish")
}

// Project Configs
allprojects {
    repositories {
        maven {
            name = "bytesafe"
            url = uri("https://herron.bytesafe.dev/maven/herron/")
            credentials {
                username = extra["username"] as String?
                password = extra["password"] as String?
            }
        }
    }

    apply(plugin = "maven-publish")
    apply(plugin = "java-library")

    group = "com.christopherrons.financial-services"
    version = "1.0.0-SNAPSHOT"
    if (project.hasProperty("releaseVersion")) {
        val releaseVersion: String by project
        version = releaseVersion
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        publications {
            create<MavenPublication>("financial-services") {
                artifactId = project.name
                // artifact("build/libs/${artifactId}-${version}.jar")
                from(components["java"])
            }
            repositories {
                maven {
                    name = "bytesafe"
                    url = uri("https://herron.bytesafe.dev/maven/herron/")
                    credentials {
                        username = extra["username"] as String?
                        password = extra["password"] as String?
                    }
                }
            }
        }
    }
}

springBoot {
    mainClass.set("com.christopherrons.FinancialServicesApplication")
}

dependencies {
    // Project Modules
    implementation(project(":financial-services-application"))
    implementation(project(":financial-services-rest-api"))
    implementation(project(":financial-services-risk-engine"))
    implementation(project(":financial-services-surveillance-engine"))
    implementation(project(":financial-services-refdata-service"))
    implementation(project(":financial-services-pricing-engine"))
    implementation(project(":financial-services-websocket"))
    api(project(":financial-services-common"))
    implementation(project(":financial-services-marketdata-service"))
    implementation(project(":financial-services-trading-engine"))

    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
    implementation(libs.javax.json.api)
    implementation(libs.javax.json)
    implementation(libs.spring.websocket)
    implementation(libs.spring.messaging)
    implementation(libs.tyrus.standalone.client)
    implementation(libs.jackson.databind)
    implementation(libs.org.springdoc)
    implementation(libs.javafaker)
    implementation(libs.commons.math)
    implementation(libs.org.slf4j)

    // External Test Libs
    testImplementation(testlibs.junit.jupiter.api)
    testImplementation(testlibs.junit.jupiter.engine)
    testImplementation(testlibs.spring.boot.starter.test)
}

// Tasks
val releaseDirName = "releases"
tasks.register<Tar>("buildAndPackage") {
    dependsOn("clean")
    dependsOn("build")
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
    destinationDirectory.set(layout.buildDirectory.dir(releaseDirName))
    from(layout.projectDirectory.dir("deploy/scripts")) {
        exclude("**/*.md")
    }
    from(layout.buildDirectory.file("libs/${rootProject.name}-${version}.jar"))
}

tasks.register("buildPackageDeploy") {
    dependsOn("buildAndPackage")
    remotes {
        withGroovyBuilder {
            "create"("webServer") {
                setProperty("host", "financial-services-1")
                setProperty("user", "herron")
                setProperty("agent", true)
            }
        }
    }

    doLast {
        ssh.run(delegateClosureOf<org.hidetake.groovy.ssh.core.RunHandler> {
            session(remotes, delegateClosureOf<org.hidetake.groovy.ssh.session.SessionHandler> {
                put(
                    hashMapOf(
                        "from" to "${layout.buildDirectory.get()}/${releaseDirName}/${rootProject.name}-${version}.tar.gz",
                        "into" to "/home/herron/deploy"
                    )
                )
                execute("tar -xf /home/herron/deploy/financial-services-${version}.tar.gz --directory /home/herron/deploy")
                execute("rm /home/herron/deploy/financial-services-${version}.tar.gz ")
                execute("cd /home/herron/deploy/ && bash /home/herron/deploy/bootstrap.sh")
            })
        })
    }
}

