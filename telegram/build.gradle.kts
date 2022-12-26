plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // Networking
    api("com.squareup.retrofit2:retrofit:2.3.0")
    api("com.squareup.retrofit2:converter-gson:2.3.0")

    implementation("com.squareup.okhttp3:logging-interceptor:3.8.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}