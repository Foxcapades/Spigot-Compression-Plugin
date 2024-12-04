package io.foxcapades.spigot.bcp.consts

internal object Permission {
  private const val PluginPrefix  = "block-compression"

  private const val CommandPrefix = "$PluginPrefix.cmd"
  const val ZipCommand    = "$CommandPrefix.compress"
  const val GiveCommand   = "$CommandPrefix.give"
  const val ReloadCommand = "$CommandPrefix.reload"

  private const val ItemPrefix = "$PluginPrefix.item"
  const val ToolCraft = "$ItemPrefix.tool.craft"
  const val ToolUse   = "$ItemPrefix.tool.use"
}
