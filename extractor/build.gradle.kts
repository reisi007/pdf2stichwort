plugins {
    id("buildsrc.convention.kotlin-jvm")

}

dependencies {
    implementation(libs.pdfbox)
    testImplementation(kotlin("test"))
}