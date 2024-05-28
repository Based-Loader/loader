plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.kloader"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(17)
}