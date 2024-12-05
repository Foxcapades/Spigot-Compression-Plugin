package extra.plugin.lang

import java.io.BufferedWriter

internal sealed class PropertyWriter(private val writer: BufferedWriter) : AutoCloseable {
  fun test(key: String, value: String) =
    !key.endsWith(".desc") && internalTest(key, value)

  fun write(key: String, value: String) {
    writer.write(formatKey(key))
    writer.write("=")
    writer.write(value.replace("\\\"", "\""))
    writer.newLine()
  }

  protected abstract fun formatKey(key: String): String

  protected abstract fun internalTest(key: String, value: String): Boolean

  override fun close() = writer.close()
}
