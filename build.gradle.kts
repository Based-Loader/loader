plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.kloader"
version = "0.0.1"

repositories {
    mavenCentral()

    maven("https://maven.fabricmc.net")
}

dependencies {
    implementation(libs.sponge.mixin)
    implementation(libs.gson)
    implementation(libs.bundles.log4j)
}

kotlin {
    jvmToolchain(17)
}