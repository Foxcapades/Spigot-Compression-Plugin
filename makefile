.PHONY: nothing
nothing:

.PHONY: build
build:
	gradle shadowJar -Dorg.gradle.java.home=/home/ellie/.jdks/corretto-16.0.2

.PHONY: release
release:
	gradle shadowJar compress -Dorg.gradle.java.home=/home/ellie/.jdks/corretto-16.0.2

.PHONY: bump-major
bump-major:
	@bld/version-bump.sh major

.PHONY: bump-minor
bump-minor:
	@bld/version-bump.sh minor

.PHONY: bump-patch
bump-patch:
	@bld/version-bump.sh patch
