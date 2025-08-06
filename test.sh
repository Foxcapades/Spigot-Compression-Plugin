#!/usr/bin/env bash

set -e

gradle clean shadowJar
mkdir build/release
cp build/libs/shadow.jar /home/ellie/Downloads/mc/minecraft/runtime/plugins/StackCompression-2.0.0-SNAPSHOT.jar
cd /home/ellie/Downloads/mc/minecraft/runtime
exec java -jar spigot-1.21.1.jar --nogui
