plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

group = "com.christopherrons.shadoworderbook"
version = "0.0.1-SNAPSHOT"
if (project.hasProperty("releaseVersion")) {
    val releaseVersion: String by project
    version = releaseVersion
}

dependencies {
    //implementation("org.springframework.boot", "spring-boot-starter-data-jpa"
    //implementation("org.springframework.boot", "spring-boot-starter-mail"
    //implementation("org.flywaydb", "flyway-core"
    //runtimeOnly "org.postgresql", "postgresql"
    implementation("org.springframework.boot", "spring-boot-starter-web", "2.5.2")
    implementation("org.springframework.boot", "spring-boot-starter-parent", "2.5.2")
    testImplementation("org.springframework.boot", "spring-boot-starter-test", "2.5.2")
    implementation("javax.json", "javax.json-api", "1.1.4")
    implementation("org.apache.logging.log4j", "log4j-api", "2.17.0")
    implementation("org.glassfish", "javax.json", "1.1.4")
    implementation("org.springframework", "spring-websocket", "5.3.14")
    implementation("org.springframework", "spring-messaging", "5.3.14")
    implementation("org.glassfish.tyrus.bundles", "tyrus-standalone-client", "1.18")
    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.13.1")
    implementation("io.springfox", "springfox-boot-starter", "3.0.0")
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

tasks.register<PackageReleaseTask>("packageReleaseTest") {
}

