package io.foxcapades.mc.bcp.util

import org.yaml.snakeyaml.introspector.Property

internal class YamlProperty<T>(val delegate: Property, val setter: T.(Any?) -> Unit)
  : Property(delegate.name, delegate.type)
{
  override fun getActualTypeArguments(): Array<Class<*>> = delegate.actualTypeArguments

  @Suppress("UNCHECKED_CAST")
  override fun set(`object`: Any, value: Any?) = setter(`object` as T, value)

  override fun get(`object`: Any): Any = delegate.get(`object`)

  override fun getAnnotations(): MutableList<Annotation> = delegate.annotations

  override fun <A : Annotation?> getAnnotation(annotationType: Class<A>?): A = delegate.getAnnotation(annotationType)

  override fun isWritable() = true
}
