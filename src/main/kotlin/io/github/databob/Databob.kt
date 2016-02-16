package io.github.databob

import io.github.databob.generators.CollectionGenerator
import io.github.databob.generators.CompositeGenerator
import io.github.databob.generators.DateTimeGenerator
import io.github.databob.generators.PrimitiveGenerator
import kotlin.reflect.KClass
import kotlin.reflect.defaultType

class Databob(vararg generators: Generator) {
    private val generator = generators.fold(CompositeGenerator(), { memo, next -> memo.with(next) })

    constructor () : this(PrimitiveGenerator(), CollectionGenerator(), DateTimeGenerator())

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun <R : Any> mk(c: Class<R>): R {
        return (generator.get(c.kotlin.defaultType, this) ?: doIt(c)) as R
    }

    private fun <R : Any> doIt(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map { generator.get(it.type, this) ?: mk(Class.forName(it.type.toString())) }
                .map { it!! }
        return constructor.call(*generatedParameters.toTypedArray())
    }
}
