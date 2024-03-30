JDK_VERSION=17
JDK_DIR=jdk/$(JDK_VERSION)

.PHONY: nothing
nothing:

.PHONY: build
build: jdk/17/bin/javac
	gradle shadowJar -Dorg.gradle.java.home=${PWD}/$(JDK_DIR)

.PHONY: release
release: jdk/17/bin/javac
	gradle shadowJar compress -Dorg.gradle.java.home=${PWD}/$(JDK_DIR)

.PHONY: bump-major
bump-major:
	@bld/version-bump.sh major

.PHONY: bump-minor
bump-minor:
	@bld/version-bump.sh minor

.PHONY: bump-patch
bump-patch:
	@bld/version-bump.sh patch

$(JDK_DIR)/bin/javac:
	@mkdir -p jdk/
	@curl -L https://corretto.aws/downloads/latest/amazon-corretto-$(JDK_VERSION)-x64-linux-jdk.tar.gz -o jdk/jdk.tgz
	@cd jdk && tar -xf jdk.tgz && mv `tar -tf jdk.tgz | head -n1` $(JDK_VERSION) && rm jdk.tgz