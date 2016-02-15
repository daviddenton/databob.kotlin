package io.github.databob

import kotlin.reflect.KClass

class DataBobInstance {
    private val lookup = PrimitiveGenerator()

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> mk(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val map = constructor.parameters
                .map { k -> lookup.get(k.type) ?: mk(Class.forName(k.type.toString())) }
        return constructor.call(*map.toTypedArray())
    }
}