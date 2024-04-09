package io.foxcapades.spigot.zip.rules

import org.yaml.snakeyaml.Yaml
import java.io.File

internal data class Rule(val enabled: Boolean, val users: Set<String>, val permissions: Permissions)

fun loadRules() {
  Yaml().load(File(ClassLoader.getSystemResource("rules").file))
}