import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.collections.listOf

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application {
    mainClass.set("com.tdbot.server.TdServerKt")
}

val platforms = arrayOf("osx-amd64", "windows-amd64", "linux-amd64", "linux-aarch64", "linux-x86")

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":scenarios"))
    implementation(project(":runtime"))

    implementation("ch.qos.logback:logback-classic:1.3.0")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")

    implementation("com.diogonunes:JColor:5.5.1")
    implementation("com.github.spullara.mustache.java:compiler:0.9.10")

    platforms.forEach { platform ->
        implementation("it.tdlight:tdlight-natives-$platform")
    }
}

listOf(project.configurations.implementation.get(), project.configurations.runtimeOnly.get())
    .onEach { it.isCanBeResolved = true }
    .also { configs ->
        platforms.forEach { platform ->
            tasks {
                register<ShadowJar>("shadowJar-$platform") {
                    archiveFileName.set("tdbot.jar")
                    configurations = configs
                    dependencies {
                        platforms.filterNot { it == platform }.forEach {
                            exclude(dependency("it.tdlight:tdlight-natives-$it"))
                        }
                    }
                }

                register<Zip>("dist-$platform") {
                    val dir = "server-${project.version}"
                    dependsOn("shadowJar-$platform")
                    archiveBaseName.set("server-$platform")
                    destinationDirectory.set(layout.buildDirectory.dir("dist"))
                    from(layout.buildDirectory.dir("libs")) {
                        include("tdbot.jar")
                        into(dir)
                    }
                    from(layout.projectDirectory.dir("scenarios")) {
                        into("$dir/scenarios")
                    }
                    from(layout.projectDirectory.dir("dist")) {
                        into(dir)
                    }
                    from(layout.projectDirectory.file("logback.xml")) {
                        into(dir)
                    }
                }
            }
        }
    }

tasks {
    register("dist") {
        platforms.forEach {
            dependsOn("dist-$it")
        }
    }
}