plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    id("org.hidetake.ssh") version "2.10.1"
}
// Configs
allprojects {
    repositories {
        mavenCentral()
        google()
    }

    apply(plugin = "java")
    // apply(plugin = "io.spring.dependency-management")
    // apply(plugin = "java-library")

    group = "com.christopherrons.financial-services"
    version = "0.0.1-SNAPSHOT"
    if (project.hasProperty("releaseVersion")) {
        val releaseVersion: String by project
        version = releaseVersion
    }
}

springBoot {
    mainClass.set("com.christopherrons.FinancialServicesApplication")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":financial-services-application"))
}

// Tasks
val releaseDirName = "releases"
tasks.register<Tar>("buildAndPackage") {
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

