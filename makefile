.PHONY: build
build:


.PHONY: release
release:
	gradle clean shadowJar compress -Dorg.gradle.java.home=/usr/lib/jvm/java-16-openjdk-amd64
