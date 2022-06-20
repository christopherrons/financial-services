
dependencies {
    // Project Modules
    implementation(project(":financial-services-common"))
    implementation(project(":financial-services-refdata-service"))
    implementation(project(":financial-services-trading-engine"))


    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
}

tasks.test {
    useJUnitPlatform()
}