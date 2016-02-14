package io.github.databob

import kotlin.reflect.KClass

object Databob {

    private val lookup: Map<String?, Any> = mapOf(
            Boolean::class.qualifiedName to true,
            String::class.qualifiedName to "bob",
            Int::class.qualifiedName to 1
    )

    fun <R : Any> mk(c: KClass<R>): R {

        val next = c.constructors.iterator().next()
        val map = next.parameters
                .map { k ->
                    if (lookup.containsKey(k.type.toString())) {
                        lookup.getOrElse(k.type.toString(), { -> true })
                    } else {
                        false
                    }
                }
        return next.call(*map.toTypedArray())
    }
}