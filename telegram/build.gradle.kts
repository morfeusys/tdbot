plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.just-ai.jaicf:core:1.2.2")

    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.8.0")
}