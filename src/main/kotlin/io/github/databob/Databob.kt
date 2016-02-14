package io.github.databob

import kotlin.reflect.KClass

object Databob {

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
        val constructor = c.constructors.iterator().next()
        val map = constructor.parameters
                .map { k ->
                    lookup.getOrElse(k.type.toString(), { -> mk(Class.forName(k.type.toString()).kotlin) })
                }
        return constructor.call(*map.toTypedArray())
    }
}