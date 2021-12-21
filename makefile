.PHONY: nothing
nothing:

.PHONY: build
build:
	gradle shadowJar -Dorg.gradle.java.home=/usr/lib/jvm/java-16-openjdk-amd64

.PHONY: release
release:
	gradle shadowJar compress -Dorg.gradle.java.home=/usr/lib/jvm/java-16-openjdk-amd64

.PHONY: bump-major
bump-major:
	@bld/version-bump.sh major

.PHONY: bump-minor
bump-minor:
	@bld/version-bump.sh minor

.PHONY: bump-patch
bump-patch:
	@bld/version-bump.sh patch
