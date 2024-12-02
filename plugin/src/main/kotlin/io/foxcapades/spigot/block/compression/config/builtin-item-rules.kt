package io.foxcapades.spigot.block.compression.config

import java.io.InputStream

internal fun <T> withBuiltinItemRules(fn: (Sequence<String>) -> T): T =
  ListFiles.openBuiltinItemConfig().processItemConfig(fn)

internal fun <T> withBuiltinBlockRules(fn: (Sequence<String>) -> T): T =
  ListFiles.openBuiltinBlockConfig().processItemConfig(fn)

private fun <T> InputStream.processItemConfig(fn: (Sequence<String>) -> T): T = bufferedReader().use {
  // Skip the header line
  it.readLine()

  fn(sequence {
    var line: String? = it.readLine()

    while (line != null) {
      if (line.isBlank())
        continue

      val p = line.indexOf('\t')

      if (line[p+1] == '1')
        yield(line.substring(0, p))

      line = it.readLine()
    }
  })
}
