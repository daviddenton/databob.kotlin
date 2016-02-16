package io.github.databob

import kotlin.reflect.KClass
import kotlin.reflect.defaultType

class Databob {

    private val generator = CompositeGenerator(PrimitiveGenerator()).with(CollectionGenerator())

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> doIt(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map { generator.get(it.type, this) ?: mk(Class.forName(it.type.toString())) }
                .map { it!! }
        return constructor.call(*generatedParameters.toTypedArray())

    }

    @Suppress("UNCHECKED_CAST")
    fun <R : Any> mk(c: Class<R>): R {
        return (generator.get(c.kotlin.defaultType, this) ?: doIt(c)) as R
    }
}
