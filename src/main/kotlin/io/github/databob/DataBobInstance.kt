package io.github.databob

import kotlin.reflect.KClass

class DataBobInstance {
    private val lookup: Map<String?, Any> = mapOf(
            Char::class.qualifiedName to 'c',
            Long::class.qualifiedName to 1L,
            Float::class.qualifiedName to 1.0f,
            Double::class.qualifiedName to 1.0,
            Boolean::class.qualifiedName to true,
            String::class.qualifiedName to "bob",
            Int::class.qualifiedName to 1
    )

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> mk(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val map = constructor.parameters
                .map { k ->
                    lookup.getOrElse(k.type.toString(), { -> mk(Class.forName(k.type.toString())) })
                }
        return constructor.call(*map.toTypedArray())
    }
}