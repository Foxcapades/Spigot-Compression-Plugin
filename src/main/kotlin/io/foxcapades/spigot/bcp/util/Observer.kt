package io.foxcapades.spigot.bcp.util

internal abstract class Observer {
  private var lastChange = 0u

  fun onChange(change: UInt) {
    if (change > lastChange) {
      handleChange(change)
      lastChange = change
    }
  }

  protected abstract fun handleChange(change: UInt)
}

