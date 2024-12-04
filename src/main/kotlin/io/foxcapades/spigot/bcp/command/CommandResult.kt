package io.foxcapades.spigot.bcp.command

@ConsistentCopyVisibility
internal data class CommandResult private constructor(val success: Boolean, val message: String? = null) {
  companion object {
    private val SimpleSuccess = CommandResult(true)
    private val SimpleFailure = CommandResult(false)

    fun ok(message: String? = null) =
      message?.let { CommandResult(true, message) } ?: SimpleSuccess

    fun fail(message: String? = null) =
      message?.let { CommandResult(false, message) } ?: SimpleFailure
  }
}
