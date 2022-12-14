allprojects {
    group = "com.tdbot"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://mvn.mchv.eu/repository/mchv/")
        maven(uri("https://jitpack.io"))
    }
}

tasks.register<Copy>("generate") {
    from(".template")
    into("bot")
}