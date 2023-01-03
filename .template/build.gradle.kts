import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.gradle.nativeplatform.platform.internal.Architectures

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application {
    mainClass.set("MainKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":scenarios"))
    implementation(project(":runtime"))

    implementation("ch.qos.logback:logback-classic:1.3.0")
}

tasks {
    shadowJar {
        archiveFileName.set("tdbot.jar")
    }
}

configurations {
    val os = DefaultNativePlatform.getCurrentOperatingSystem().let { when {
        it.isMacOsX -> "osx"
        it.isWindows -> "windows"
        else -> "linux"
    } }

    val arch = DefaultNativePlatform.getCurrentArchitecture().let { when {
        it.isAmd64 -> "amd64"
        it.isI386 -> "x86"
        Architectures.ARM_V7.isAlias(it.name) -> "armhf"
        Architectures.AARCH64.isAlias(it.name) -> "aarch64"
        else -> it.name
    } }

    val platform = System.getProperty("platform", "$os-$arch")

    println("Building with tdlight native [$platform]")

    runtimeClasspath.get().dependencies.add(project.dependencies.create("it.tdlight:tdlight-natives-$platform"))
}