rootProject.name = "financial-services"
rootProject.buildFileName = "build.gradle.kts"

include("financial-services-common")
include("financial-services-pricing-engine")
include("financial-services-refdata-service")
include("financial-services-marketdata-service")
include("financial-services-risk-engine")
include("financial-services-surveillance-engine")
include("financial-services-trading-engine")
include("financial-services-rest-api")
include("financial-services-webclient")
include("financial-services-websocket")
include("financial-services-application")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("spring.boot.starter.web", "org.springframework.boot:spring-boot-starter-web:2.5.12")
            library("spring.boot.starter.parent", "org.springframework.boot:spring-boot-starter-parent:2.5.12")
            library("javax.json.api", "javax.json:javax.json-api:1.1.4")
            library("javax.json", "org.glassfish:javax.json:1.1.4")
            library("spring.websocket", "org.springframework:spring-websocket:5.3.14")
            library("spring.messaging", "org.springframework:spring-messaging:5.3.14")
            library("tyrus.standalone.client", "org.glassfish.tyrus.bundles:tyrus-standalone-client:1.18")
            library("jackson.databind", "com.fasterxml.jackson.core:jackson-databind:2.13.2.1")
            library("jackson.xml", "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
            library("org.springdoc", "org.springdoc:springdoc-openapi-ui:1.6.3")
            library("javafaker", "com.github.javafaker:javafaker:1.0.2")
            library("commons.math", "org.apache.commons:commons-math3:3.2")
            library("org.slf4j", "org.slf4j:slf4j-api:1.7.25")
        }



        create("testlibs") {
            library("spring.boot.starter.test", "org.springframework.boot:spring-boot-starter-test:2.5.2")
            library("junit.jupiter.api", "org.junit.jupiter:junit-jupiter-api:5.8.1")
            library("junit.jupiter.engine", "org.junit.jupiter:junit-jupiter-engine:5.8.1")
        }
    }
}
