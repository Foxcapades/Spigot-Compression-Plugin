#!/usr/bin/env sh

getCurrentVersion() {
  grep -Eo 'version = "([0-9]+\.[0-9]+\.[0-9]+)"' build.gradle.kts | cut -d\" -f2
}

replaceVersion() {
  sed -i "s/^version = .\+/version = \"$1\"/" build.gradle.kts
  sed -i "s/^version: .\+/version: $1/" src/main/resources/plugin.yml
}

bumpMajor() {
  getCurrentVersion | awk -F. '{ print ++$1".0.0"}'
}

bumpMinor() {
  getCurrentVersion | awk -F. '{ print $1"."++$2".0"}'
}

bumpPatch() {
  getCurrentVersion | awk -F. '{ print $1"."$2"."++$3}'
}

case $1 in
  "patch")
    replaceVersion "$(bumpPatch)"
    ;;
  "minor")
    replaceVersion "$(bumpMinor)"
    ;;
  "major")
    replaceVersion "$(bumpMajor)"
    ;;
  *)
    echo "Usage: version-bump.sh major|minor|patch"
    exit 1
    ;;
esac
