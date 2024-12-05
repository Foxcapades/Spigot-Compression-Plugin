package io.foxcapades.mc.bcp.config

import io.foxcapades.mc.bcp.consts.Default
import java.io.InputStream

internal fun <T> withDisallowedBlockRules(fn: (Sequence<Pair<String, String>>) -> T): T =
  ListFiles.openDisallowedBlockList().processItemRules(fn)

internal fun <T> withAllowedBlockRules(fn: (Sequence<Pair<String, String>>) -> T): T =
  ListFiles.openAllowedBlockList().processItemRules(fn)

internal fun <T> withDisallowedItemRules(fn: (Sequence<Pair<String, String>>) -> T): T =
  ListFiles.openDisallowedItemList().processItemRules(fn)

internal fun <T> withAllowedItemRules(fn: (Sequence<Pair<String, String>>) -> T): T =
  ListFiles.openAllowedItemList().processItemRules(fn)

private fun <T> InputStream.processItemRules(fn: (Sequence<Pair<String, String>>) -> T): T = bufferedReader().use {
  fn(sequence {
    var line = it.readLine()?.trim()

    while (line != null) {
      if (line.isEmpty())
        continue

      when (val i = line.indexOf(':')) {
        -1   -> yield(Default.Namespace to line)
        0    -> yield(Default.Namespace to line.substring(1))
        else -> yield(line.substring(0, i) to line.substring(i+1))
      }

      line = it.readLine()?.trim()
    }
  })
}
