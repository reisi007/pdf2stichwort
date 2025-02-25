plugins {
    id("buildsrc.convention.kotlin-jvm")
}

group = "pictures.reisinger"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.bundles.angus)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}