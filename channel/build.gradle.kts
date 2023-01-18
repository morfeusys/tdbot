plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api(platform("it.tdlight:tdlight-java-bom:2.8.10.1"))
    api("it.tdlight:tdlight-java-8")

    api("com.just-ai.jaicf:core:1.2.5")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.4.2-native-mt")
    implementation("org.reflections:reflections:0.10.2")
}
