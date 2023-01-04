plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":api"))

    api("com.vdurmont:emoji-java:5.1.1")

    api("io.ktor:ktor-client-apache:1.5.1")
    api("io.ktor:ktor-client-serialization:1.5.1")
    api("io.ktor:ktor-client-gson:1.5.1")
}