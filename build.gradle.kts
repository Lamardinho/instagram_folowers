import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    war
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
    kotlin("kapt") version "1.5.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.1")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.1")
    implementation("io.swagger.core.v3:swagger-annotations:2.1.6")
    implementation("org.postgresql:postgresql:42.2.9")
    implementation(kotlin("stdlib"))
    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    implementation("org.liquibase:liquibase-core")
    implementation("com.vladmihalcea:hibernate-types-52:2.2.2")
    // JasperReports
    implementation("net.sf.jasperreports:jasperreports:6.16.0")
    implementation("com.lowagie:itext:2.1.7")
    // Локальные библиотеки. Используется для шрифтов
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    // Guava
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.mapstruct:mapstruct-processor:1.4.1.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("com.h2database:h2")
    testImplementation("org.mockito:mockito-core:3.6.28")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
