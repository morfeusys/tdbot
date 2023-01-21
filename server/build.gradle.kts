plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":scenarios"))
    implementation(project(":runtime"))

    implementation("ch.qos.logback:logback-classic:1.3.0")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")

    implementation("com.diogonunes:JColor:5.5.1")
    implementation("com.github.spullara.mustache.java:compiler:0.9.10")

    implementation("it.tdlight:tdlight-natives-osx-amd64")
    implementation("it.tdlight:tdlight-natives-windows-amd64")
    implementation("it.tdlight:tdlight-natives-linux-amd64")
    implementation("it.tdlight:tdlight-natives-linux-x86")
    implementation("it.tdlight:tdlight-natives-linux-aarch64")
}

tasks {
    shadowJar {
        archiveFileName.set("gramlin.jar")
    }
    register<Zip>("dist") {
        dependsOn(shadowJar)
        archiveBaseName.set("gramlin-server")
        val dir = "gramlin-server-${project.version}"
        destinationDirectory.set(layout.buildDirectory.dir("dist"))
        into(dir) {
            from(layout.projectDirectory.dir("dist"))
            from(layout.projectDirectory.file("logback.xml"))
            from(layout.buildDirectory.dir("libs")) {
                include("gramlin.jar")
            }
        }
        into("$dir/scenarios") {
            from(layout.projectDirectory.dir("scenarios"))
        }
    }
}