plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib"))

    api(platform("it.tdlight:tdlight-java-bom:2.8.8.2"))
    api("it.tdlight:tdlight-java")

    implementation("com.just-ai.jaicf:core:1.2.4")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.4.2-native-mt")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}