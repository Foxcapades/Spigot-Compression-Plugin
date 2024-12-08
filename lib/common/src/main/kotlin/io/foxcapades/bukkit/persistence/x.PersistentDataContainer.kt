@file:JvmName("PersistentDataContainerExtensions")
package io.foxcapades.bukkit.persistence

import io.foxcapades.bukkit.utils.serial.Gson
import io.foxcapades.bukkit.utils.toInt
import org.bukkit.NamespacedKey
import org.bukkit.persistence.ListPersistentDataType
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import javax.naming.OperationNotSupportedException
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun PersistentDataContainer.toSlimSerializable(): Any {
  val copy = FakeContainer(keys.size)
  val out = arrayOfNulls<Any?>(keys.size)

  keys.forEach { key ->

  }

}

private data class FakeData<C : Any>(val key: NamespacedKey, val type: PersistentDataType<out Any, C>, val value: C) {
  @OptIn(ExperimentalEncodingApi::class)
  fun toPrimitive(context: PersistentDataAdapterContext): Any {
    return when {
      // If the type converts to a byte array, base64 encode it.
      type.primitiveType == ByteArray::class.java -> arrayOf(
        "ba",
        Base64.encode(type.toPrimitive(value, context) as ByteArray)
      )

      // Recurse!
      type.primitiveType.isAssignableFrom(PersistentDataContainer::class.java) -> arrayOf(
        "pdc",
        (type.toPrimitive(value, context) as PersistentDataContainer).toSlimSerializable()
      )

      // If it's a primitive type
      type.isPrimitive() -> {
        val v = type.toPrimitive(value, context)

        when (type.primitiveType.kotlin) {
          Int::class     -> arrayOf("i", v)
          Double::class  -> arrayOf("d", v)
          Boolean::class -> arrayOf("B",(v as Boolean).toInt())
          Long::class    -> arrayOf("l", v)
          Float::class   -> arrayOf("f", v)
          Byte::class    -> arrayOf("b", v)
          Short::class   -> arrayOf("s", v)
          Char::class    -> arrayOf("c", v)
          else           -> throw IllegalStateException("unrecognized primitive type ${type.primitiveType}")
        }
      }

      // If it's a string.
      CharSequence::class.java.isAssignableFrom(type.primitiveType) ->
        arrayOf("S", type.toPrimitive(value, context))

      // If it's a list of values.
      type is ListPersistentDataType<*, *> -> arrayOf(
        "lpd",
        (type.toPrimitive(value, context) as List<*>)
          .takeUnless { it.isEmpty() }
          ?.toTypedArray<Any?>()
          ?.also(::fixArray)
          ?: emptyArray<Any>()
      )

      // If it's an array of primitives
      type.primitiveType.isArray && (type.primitiveType.arrayType().isPrimitive || BoxedPrimitives.contains(type.primitiveType)) -> {
        val v = type.toPrimitive(value, context)

        when (type.primitiveType.arrayType().kotlin) {
          Int::class     -> arrayOf("ia", v)
          Double::class  -> arrayOf("da", v)
          Boolean::class -> arrayOf("Ba",(v as Boolean).toInt())
          Long::class    -> arrayOf("la", v)
          Float::class   -> arrayOf("fa", v)
          Short::class   -> arrayOf("sa", v)
          Char::class    -> arrayOf("ca", v)
          else           -> throw IllegalStateException("unrecognized primitive array type ${type.primitiveType}")
        }
      }

      else -> out
    }
  }
}

private class FakeContainer(size: Int) : PersistentDataContainer {
  val values = ArrayList<FakeData<*>>(size)

  override fun <P : Any, C : Any> set(key: NamespacedKey, type: PersistentDataType<P, C>, value: C) {
    values.add(FakeData(key, type, value))
  }

  override fun <P : Any, C : Any> has(key: NamespacedKey, type: PersistentDataType<P, C>): Boolean {
    throw OperationNotSupportedException()
  }

  override fun has(key: NamespacedKey): Boolean {
    throw OperationNotSupportedException()
  }

  override fun <P : Any, C : Any> get(key: NamespacedKey, type: PersistentDataType<P, C>): C? {
    throw OperationNotSupportedException()
  }

  override fun <P : Any, C : Any> getOrDefault(
    key: NamespacedKey,
    type: PersistentDataType<P, C>,
    defaultValue: C,
  ): C {
    throw OperationNotSupportedException()
  }

  override fun getKeys(): MutableSet<NamespacedKey> {
    throw OperationNotSupportedException()
  }

  override fun remove(key: NamespacedKey) {
    throw OperationNotSupportedException()
  }

  override fun isEmpty(): Boolean {
    throw OperationNotSupportedException()
  }

  override fun copyTo(other: PersistentDataContainer, replace: Boolean) {
    throw OperationNotSupportedException()
  }

  override fun getAdapterContext(): PersistentDataAdapterContext {
    throw OperationNotSupportedException()
  }
}

private val BoxedPrimitives = setOf<Class<*>>(
  Boolean::class.java,
  Byte::class.java,
  Short::class.java,
  Int::class.java,
  Long::class.java,
  Float::class.java,
  Double::class.java,
  Char::class.java,
)

private fun PersistentDataType<*, *>.isPrimitive(): Boolean =
  primitiveType.isPrimitive || BoxedPrimitives.contains(primitiveType)

@OptIn(ExperimentalEncodingApi::class)
private fun fixArray(it: Array<Any?>) {
  var i = -1

  while (i++ < it.size) {
    if (it[i] == null)
      continue

    if (it[i] is PersistentDataContainer) {
      do {
        it[i] = (it[i] as PersistentDataContainer?)?.toSlimSerializable()
      } while (++i < it.size)
    } else if (it[i] is ByteArray) {
      do {
        it[i] = (it[i] as ByteArray?)?.let(Base64::encode)
      } while (++i < it.size)
    } else if (it[i]!!::class.java.isPrimitive) {
      // This is fine
      break
    } else {
      it.stringifyInline(i)
    }
  }
}

private fun handleUnknown(type: PersistentDataType<*, *>, value: Any?): Any =
  arrayOf(
    "X",
    type.javaClass.canonicalName ?: type.javaClass.name,
    type.primitiveType.canonicalName ?: type.primitiveType.name,
    value?.stringify(),
  )

private fun Array<Any?>.stringifyInline(start: Int = 0) {
  var i = start

  try {
    do {
      this[i] = this[i]?.let(Gson::toJson)
    } while (++i < size)
  } catch (e: Throwable) {
    i = i

    do {
      this[i] = this[i]?.toString()
    } while (++i < size)
  }
}

private fun Any.stringify(): Any =
  try {
    Gson.toJson(this)
  } catch (e: Throwable) {
    toString()
  }
