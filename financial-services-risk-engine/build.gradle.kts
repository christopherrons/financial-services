
dependencies {
    // Project Modules
    implementation(project(":financial-services-common"))
    implementation(project(":financial-services-refdata-engine"))

    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
    implementation(libs.commons.math)
}
