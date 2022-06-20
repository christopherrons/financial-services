dependencies {
    // Project Modules
    implementation(project(":financial-services-common"))
    implementation(project(":financial-services-marketdata-service"))
    implementation(project(":financial-services-refdata-service"))

    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
    implementation(libs.org.springdoc)
}