plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java-library")

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

tasks.register<Tar>("packageRelease") {
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
    destinationDirectory.set(layout.buildDirectory.dir("releases"))
    from(layout.projectDirectory.dir("deploy/scripts")) {
        exclude("**/*.md")
    }
    from(layout.buildDirectory.file("libs/${rootProject.name}-${archiveVersion}.jar"))
}

tasks.register<tasks.FirstTask>("first") {
    doLast {
        // do something
    }
}

