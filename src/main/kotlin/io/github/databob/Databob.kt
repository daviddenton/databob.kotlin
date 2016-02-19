package io.github.databob

import io.github.databob.generators.*
import kotlin.reflect.KClass
import kotlin.reflect.defaultType

/**
 * Main entry point for generating instances
 */
class Databob(vararg generators: Generator) {
    private val generator = generators.fold(CompositeGenerator()) { memo, next -> memo.with(next) }

    constructor () : this(
            MiscellaneousGenerator(),
            PrimitiveGenerator(),
            CollectionGenerator(),
            DateTimeGenerator(),
            FunktionaleGenerator()
    )

    fun <R : Any> mk(c: KClass<R>): R = mk(c.java)

    @Suppress("UNCHECKED_CAST")
    fun <R : Any> mk(c: Class<R>): R = (generator.mk(c.kotlin.defaultType, this) ?: fallback(c)) as R

    private fun <R : Any> fallback(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map {
                    if (it.type.isMarkedNullable) null
                    else {
                        generator.mk(it.type, this) ?: mk(Class.forName(it.type.toString()))
                    }
                }
        return constructor.call(*generatedParameters.toTypedArray())
    }
}
