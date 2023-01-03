plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api(platform("it.tdlight:tdlight-java-bom:2.8.10.1"))
    api("it.tdlight:tdlight-java-8")

    implementation("com.just-ai.jaicf:core:1.2.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.4.2-native-mt")
}
