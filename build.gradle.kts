import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.0"
  `java-library`
  id("com.github.johnrengelman.shadow") version "7.1.0"
}

group   = "io.foxcapades"
version = "1.0.1"

repositories {
  mavenCentral()
  maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
  maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
  implementation("org.jetbrains.kotlin", "kotlin-stdlib", "1.6.0")
//  implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", "1.6.0")
  compileOnly("org.spigotmc", "spigot-api", "1.18-R0.1-SNAPSHOT")

  testImplementation(kotlin("test"))
  testImplementation(platform("org.junit:junit-bom:5.8.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.mockito:mockito-core:4.0.0")
  testImplementation("io.mockk:mockk:1.12.1")
  testCompileOnly("org.spigotmc", "spigot-api", "1.18-R0.1-SNAPSHOT")
}

kotlin {
  jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(16))
  }
}

tasks.withType(KotlinCompile::class.java).all {
  this.requiredServices

  kotlinOptions {
    jvmTarget = "16"
    freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
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
  outjars("build/libs/spigot-block-compression-$version-release.jar")
}