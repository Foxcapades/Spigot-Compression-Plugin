package io.foxcapades.spigot.block.compression.util

internal data class MappingIterator<T, R>(
  private val rawIterator: ListIterator<T>,
  private val mappingFunc: (index: Int, value: T) -> R,
) : ListIterator<R> {
  private var pos = -1

  override fun hasPrevious() = pos > 0

  override fun previous() = mappingFunc(--pos, rawIterator.previous())

  override fun previousIndex() = pos-1

  override fun hasNext() = rawIterator.hasNext()

  override fun next() = mappingFunc(++pos, rawIterator.next())

  override fun nextIndex() = pos+1
}