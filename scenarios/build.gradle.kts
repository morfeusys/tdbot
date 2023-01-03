plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":api"))
    api("com.vdurmont:emoji-java:5.1.1")
}