plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib"))

    api(project(":channel"))
    api(project(":telegram"))

    api("com.just-ai.jaicf:core:1.2.4")

    api("io.ktor:ktor-client-cio:1.5.1")
    api("io.ktor:ktor-client-serialization:1.5.1")
    api("io.ktor:ktor-client-gson:1.5.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}