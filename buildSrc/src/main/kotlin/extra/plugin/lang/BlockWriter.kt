package extra.plugin.lang

import java.io.BufferedWriter

internal class BlockWriter(writer: BufferedWriter) : PropertyWriter(writer) {
  override fun internalTest(key: String, value: String) =
    key.startsWith("block.minecraft.")
      && key.indexOf("bed.", 16) == -1
      && key.indexOf("beacon.", 16) == -1
      && key.indexOf("spawn.", 16) == -1
      && key.indexOf("spawner.", 16) == -1

  override fun formatKey(key: String) = key.substring(16)
}
