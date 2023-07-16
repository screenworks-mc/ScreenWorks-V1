plugins {
    id("java")
    id ("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "org.screenwork.minestomtest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.github.Minestom:Minestom:-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

