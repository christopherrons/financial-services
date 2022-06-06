
dependencies {
    // Project Modules
    implementation(project(":financial-services-refdata-engine"))
    implementation(project(":financial-services-common"))
    implementation(project(":financial-services-marketdata-engine"))

    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
}