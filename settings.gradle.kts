rootProject.name = "spigot-block-compression"

buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath("com.guardsquare:proguard-gradle:7.6.0")
  }
}

include(":plugin", ":resourcepack")
