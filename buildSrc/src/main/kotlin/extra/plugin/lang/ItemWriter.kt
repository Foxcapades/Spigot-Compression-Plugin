package extra.plugin.lang

import java.io.BufferedWriter

internal class ItemWriter(writer: BufferedWriter) : PropertyWriter(writer) {
  override fun internalTest(key: String, value: String) =
    key.startsWith("item.minecraft.")
      && key.indexOf("bundle.", 15) == -1
      && key.indexOf("crossbow.", 15) == -1
      && key.indexOf("debug_stick.", 15) == -1
      && key.indexOf("firework_rocket.", 15) == -1
      && key.indexOf("smithing_template.", 15) == -1

  override fun formatKey(key: String) = key.substring(15)
}

