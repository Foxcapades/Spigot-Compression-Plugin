JDK_VERSION=17
JDK_DIR=jdk/$(JDK_VERSION)
LANGUAGE_FILE := en_us.json
LANGUAGE_CODE := $(basename $(notdir $(LANGUAGE_FILE)))

.PHONY: nothing
nothing:

.PHONY: build
build: jdk/17/bin/javac
	gradle shadowJar -Dorg.gradle.java.home=${PWD}/$(JDK_DIR)

.PHONY: release
release: jdk/17/bin/javac
	gradle shadowJar compress -Dorg.gradle.java.home=${PWD}/$(JDK_DIR)

$(JDK_DIR)/bin/javac:
	@mkdir -p jdk/
	@curl -L https://corretto.aws/downloads/latest/amazon-corretto-$(JDK_VERSION)-x64-linux-jdk.tar.gz -o jdk/jdk.tgz
	@cd jdk && tar -xf jdk.tgz && mv `tar -tf jdk.tgz | head -n1` $(JDK_VERSION) && rm jdk.tgz

.PHONY: default
default:
	@echo "no"

.PHONY: generate-language-files
generate-language-files: $(LANGUAGE_FILE)
	@gradle :plugin:build-language-files --language-file=$< --language-code='$(LANGUAGE_CODE)'
