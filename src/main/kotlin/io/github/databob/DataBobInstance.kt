package io.github.databob

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.defaultType

class DataBobInstance {
    private val lookup: Map<KType, Any> = mapOf(
            Char::class.defaultType to 'c',
            Long::class.defaultType to 1L,
            Float::class.defaultType to 1.0f,
            Double::class.defaultType to 1.0,
            Boolean::class.defaultType to true,
            String::class.defaultType to "bob",
            Int::class.defaultType to 1
    )

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> mk(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val map = constructor.parameters
                .map { k ->
                    lookup.getOrElse(k.type, { -> mk(Class.forName(k.type.toString())) })
                }
        return constructor.call(*map.toTypedArray())
    }
}