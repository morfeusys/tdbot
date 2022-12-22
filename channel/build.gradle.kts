plugins {
    kotlin("jvm") version "1.7.21"
}

dependencies {
    implementation(kotlin("stdlib"))

    api(platform("it.tdlight:tdlight-java-bom:2.8.8.2"))
    api("it.tdlight:tdlight-java")

    implementation("com.just-ai.jaicf:core:1.2.4")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}