plugins {
    id("buildsrc.convention.kotlin-jvm")
}

dependencies {
    api(libs.bundles.angus)
    api(project(":logging"))
    testImplementation(kotlin("test"))
    testFixturesApi(project(":logging"))
}
