rootProject.name = "spigot-block-compression"

buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath("com.guardsquare:proguard-gradle:7.6.0")
  }
}

include("common")
project(":common").apply {
  projectDir = file("lib/common")
  name = "common"
}

