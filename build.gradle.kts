import extra.plugin.lang.BuildLanguageFiles
import extra.plugin.mojang.BuildPlayerHeadMeta
import extra.plugin.mojang.GetPlayerUUID

plugins {
  kotlin("jvm") version "2.0.21"
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
  mavenCentral()
  maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
  maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
  maven { url = uri("https://libraries.minecraft.net/")}
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")
  compileOnly("com.mojang:authlib:6.0.54")
  implementation(kotlin("reflect"))
}

kotlin {
  compilerOptions {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

group = "io.foxcapades.mc"
version = "2.0.0-SNAPSHOT"

val releaseDir = mkdir(layout.buildDirectory.dir("release"))
val libsDir = mkdir(layout.buildDirectory.dir("libs"))

val shadowJarFile = libsDir.resolve("shadow.jar")
val compressedJarFile = libsDir.resolve("compressed.jar")

tasks.test {
  useJUnitPlatform()
}

tasks.shadowJar {
  dependsOn(pushVersion)

  destinationDirectory = shadowJarFile.parentFile
  archiveFileName = shadowJarFile.name
}

tasks.register<BuildLanguageFiles>("build-language-files")

val pushVersion = tasks.register("update-versions") {
  doLast {
    with(ProcessBuilder(
      "sed",
      "-i",
      "s/^version:.\\+/version: ${project.version}/",
      project.projectDir.resolve("src/main/resources/plugin.yml").path
    ).start()) { require(waitFor() == 0) { "failed to update plugin version in plugin.yml: ${errorReader().use { it.readText() }}" } }
  }
}

val compress = tasks.register("compress", proguard.gradle.ProGuardTask::class.java) {
  dependsOn(tasks.shadowJar)

  inputs.files(shadowJarFile)
  outputs.files(compressedJarFile)

  configuration("extra/proguard-rules.pro")

  libraryjars(files(configurations.compileClasspath.get().files))

  injars(shadowJarFile)
  outjars(compressedJarFile)
}

tasks.register("release") {
  val releaseFile = releaseDir.resolve("BlockCompression-${project.version}.jar")

  dependsOn(compress)

  inputs.files(compressedJarFile)
  outputs.files(releaseFile)

  doLast { compressedJarFile.copyTo(target = releaseFile, overwrite = true) }
}

tasks.register<BuildPlayerHeadMeta>("buildPlayerHeadMeta")
tasks.register<GetPlayerUUID>("getPlayerUUID")
