.PHONY: nothing
nothing:

.PHONY: build
build:
	gradle shadowJar

.PHONY: release
release:
	gradle shadowJar compress

.PHONY: bump-major
bump-major:
	@bld/version-bump.sh major

.PHONY: bump-minor
bump-minor:
	@bld/version-bump.sh minor

.PHONY: bump-patch
bump-patch:
	@bld/version-bump.sh patch
