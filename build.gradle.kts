plugins {
    kotlin("jvm") version "2.1.20-Beta1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "kr.enchyrax.mc"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    /** Import Local Libraries **/
    compileOnly(fileTree(mapOf("dir" to "libs/exclude", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs/include", "include" to listOf("*.jar"))))
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn(tasks.shadowJar)

    doLast {
        copy {
            from(tasks.shadowJar.get().archiveFile)
            into("minecraft/paper/1.20.1/server/plugins")
        }
    }
}