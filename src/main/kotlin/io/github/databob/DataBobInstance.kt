package io.github.databob

import kotlin.reflect.KClass

class DataBobInstance {
    private val lookup = CompositeGenerator(PrimitiveGenerator()).with(CollectionGenerator())

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> mk(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map { k -> lookup.get(k.type, this) ?: mk(Class.forName(k.type.toString())) }
        return constructor.call(*generatedParameters.toTypedArray())
    }
}
