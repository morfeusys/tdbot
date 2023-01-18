allprojects {
    group = "com.tdbot"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven(uri("http://mvn.mchv.eu/repository/mchv/")) {
            isAllowInsecureProtocol = true
        }
        maven(uri("https://jitpack.io"))
    }
}

tasks.register<Copy>("createBot") {
    from(".template")
    into("bot")

    duplicatesStrategy = DuplicatesStrategy.FAIL
}