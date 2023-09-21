import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.application
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.course"
version = "1.0"
description = "Kotlin Training Labs"
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    }
}

repositories {
    mavenCentral()
}
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.spring") version "1.6.21"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
    application
}

object Versions {
    const val kotlin_version = "1.8.20"
    const val kotlinx_version = "1.7.3"
    const val spring_version = "3.1.3"
    const val jackson_version = "2.13.3"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:${Versions.kotlin_version}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinx_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinx_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Versions.kotlinx_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${Versions.kotlinx_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.7.3")
    implementation("org.springframework.boot:spring-boot-starter:${Versions.spring_version}")
    implementation("org.springframework.boot:spring-boot-starter-tomcat:${Versions.spring_version}")
    implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.spring_version}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson_version}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson_version}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Versions.jackson_version}")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.6")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:${Versions.spring_version}")
    implementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Versions.spring_version}")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.spring_version}")

}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
//    options.compilerArgs.addAll(listOf("--release", "19"))
//    options.compilerArgs.addAll(listOf("--enable-preview"))
//    options.compilerArgs.addAll(listOf("--add-modules", "jdk.incubator.concurrent"))
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "19"
}

application{
    applicationDefaultJvmArgs = listOf("--enable-preview", "--add-modules", "jdk.incubator.concurrent")
}

tasks.test {
    useJUnitPlatform()
    reports {
        junitXml.isOutputPerTestCase = true
    }
}

tasks.create<Delete>("cleanup") {
    delete(rootProject.buildDir)
}