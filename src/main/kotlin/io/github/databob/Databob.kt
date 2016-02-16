package io.github.databob

import kotlin.reflect.KClass
import kotlin.reflect.defaultType

class Databob {

    private val lookup = CompositeGenerator(PrimitiveGenerator()).with(CollectionGenerator())

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> doIt(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map { k -> lookup.get(k.type, this) ?: mk(Class.forName(k.type.toString())) }
                .map { b -> b!! }
        return constructor.call(*generatedParameters.toTypedArray())

    }

    fun <R : Any> mk(c: Class<R>): R {
        val get: Any = lookup.get(c.kotlin.defaultType, this) ?: doIt(c)
        return get as R
    }
}
