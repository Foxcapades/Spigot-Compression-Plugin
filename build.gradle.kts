import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.9.23"
  `java-library`
  id("com.github.johnrengelman.shadow") version "7.1.0"
}

group   = "io.foxcapades"
version = "1.10.0"

repositories {
  mavenCentral()
  maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
  maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
    vendor.set(JvmVendorSpec.AMAZON)
  }
}

tasks.withType(KotlinCompile::class.java).all {
  kotlinOptions {
    jvmTarget = "17"
    freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
  }
}

tasks.test {
  useJUnitPlatform()
}

tasks.register("compress", proguard.gradle.ProGuardTask::class.java) {
  dependsOn(":shadowJar")

  configuration("bld/proguard-rules.pro")

  libraryjars(files(configurations.compileClasspath.get().files))

  injars("build/libs/spigot-block-compression-$version-all.jar")
  outjars("build/libs/spigot-block-compression.jar")
}