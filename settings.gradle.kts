rootProject.name = "spigot-block-compression"

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention").version("0.10.0")
}

buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath("com.guardsquare:proguard-gradle:7.4.0")
  }
}
