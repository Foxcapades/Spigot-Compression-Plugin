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
project(":common").projectDir = file("lib/common")

include("unsafe-1.21.3")
project(":unsafe-1.21.3").projectDir = file("lib/unsafe/1.21.3")
