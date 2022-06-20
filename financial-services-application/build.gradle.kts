dependencies {
    // Project Modules
    implementation(project(":financial-services-rest-api"))
    implementation(project(":financial-services-risk-engine"))
    implementation(project(":financial-services-surveillance-engine"))
    implementation(project(":financial-services-refdata-service"))
    implementation(project(":financial-services-pricing-engine"))
    implementation(project(":financial-services-websocket"))
    implementation(project(":financial-services-common"))
    implementation(project(":financial-services-marketdata-service"))
    implementation(project(":financial-services-trading-engine"))
    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
    implementation(libs.spring.websocket)
    implementation(libs.spring.messaging)

    // External Test Libs
    testImplementation(testlibs.junit.jupiter.api)
    testImplementation(testlibs.junit.jupiter.engine)
    testImplementation(testlibs.spring.boot.starter.test)
}

