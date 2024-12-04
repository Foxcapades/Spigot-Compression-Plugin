package io.foxcapades.spigot.block.compression.util

internal abstract class Observable {
  private var observers = emptyArray<Observer>()

  private var propagating = false

  fun observe(observer: Observer) {
    observers = observers.append(observer)
  }

  fun observe(fn: (UInt) -> Unit) {
    observe(object : Observer() { override fun handleChange(change: UInt) = fn(change) })
  }

  fun commit(change: UInt) {
    if (propagating)
      throw IllegalStateException("cannot modify an observable value during change propagation")

    propagating = true
    observers.forEach { it.onChange(change) }
    propagating = false
  }
}
