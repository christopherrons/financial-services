
dependencies {
    // Project Modules
    implementation(project(":financial-services-refdata-service"))
    implementation(project(":financial-services-common"))
    implementation(project(":financial-services-marketdata-service"))

    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
}