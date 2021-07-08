import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.4.10"
    id("com.google.cloud.tools.jib") version "2.7.1"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
}

group = "com.donghyukki"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

jib.to.image = "kderr2791/korello-app:latest"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    //OAuth
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    //Open API
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.0")

    //MySQL
    runtimeOnly("mysql:mysql-connector-java")

    //AssertJ
    testImplementation("org.assertj:assertj-core:3.13.2")

    //MariaDB
    implementation("org.mariadb.jdbc:mariadb-java-client")

    //logback
    implementation("net.logstash.logback:logstash-logback-encoder:5.1")

    //jwt
    implementation("io.jsonwebtoken:jjwt:0.7.0")

    //kafka
    implementation("org.springframework.kafka:spring-kafka")

    //archunit
    testImplementation ("com.tngtech.archunit:archunit-junit5:0.20.0")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
