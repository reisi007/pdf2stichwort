plugins {
    id("buildsrc.convention.kotlin-jvm")
}

dependencies {
    api(project(":logging"))
    implementation(libs.pdfbox)
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.testing)
    testFixturesApi(project(":logging"))
}