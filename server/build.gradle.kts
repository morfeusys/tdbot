plugins {
    application
    kotlin("jvm") version "1.7.21"
}

application {
    mainClass.set("com.tdbot.server.TdServerKt")
}

distributions {
    main {
        distributionBaseName.set("tdbot-server")
        contents {
            into("scenarios") {
                from(layout.projectDirectory.dir("scenarios"))
            }
            into(".") {
                from(layout.projectDirectory.file("logback.xml"))
                from(layout.projectDirectory.file("dist/tdbot.properties"))
            }
        }
    }
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
    startScripts {
        doLast {
            unixScript.writeText(unixScript.readText().replace("exec \"\$JAVACMD\" \"\$@\"", "export GRAMLIN_HOME=\$APP_HOME\nexec \"\$JAVACMD\" \"\$@\""))
            windowsScript.writeText(windowsScript.readText().replace("@rem Execute server", "setx GRAMLIN_HOME %APP_HOME%"))
        }
    }
}