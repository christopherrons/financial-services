
dependencies {
    // Project Modules
    implementation(project(":financial-services-common"))

    // External Libs
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.parent)
    implementation(libs.javax.json.api)
    implementation(libs.javax.json)
    implementation(libs.tyrus.standalone.client)
}
