plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // Networking
    api("com.squareup.retrofit2:retrofit:2.3.0")
    api("com.squareup.retrofit2:converter-gson:2.3.0")

    implementation("com.squareup.okhttp3:logging-interceptor:3.8.0")
}