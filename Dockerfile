FROM openjdk:17-jdk-slim

RUN apt-get update
RUN apt-get install -y \
    unzip \
    curl \
    openssl \
    zlib1g

WORKDIR /gradle
RUN curl -L "https://services.gradle.org/distributions/gradle-7.6-bin.zip" -o gradle-7.6-bin.zip
RUN unzip gradle-7.6-bin.zip
RUN rm -f gradle-7.6-bin.zip
ENV GRADLE_HOME=/gradle/gradle-7.6
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /runtime
COPY . .
RUN rm -f .td

RUN gradle :bot:shadowJar

ENTRYPOINT ["java", "-jar", "./bot/build/libs/tdbot.jar"]
