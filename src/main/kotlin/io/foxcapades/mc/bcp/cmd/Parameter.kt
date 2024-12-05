package io.foxcapades.mc.bcp.cmd

internal class Parameter(val name: String, val required: Boolean, val tabCompleter: (String) -> Sequence<String>) {
  enum class Type {
    Player,
    Item,
    Integer,
  }

  inline val formattedName
    get() = when {
      required -> "<$name>"
      else     -> "[$name]"
    }

  override fun toString() = formattedName
}
