package io.github.databob

import io.github.databob.generators.*
import kotlin.reflect.KClass
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class Databob(vararg overrides: Generator) {
    private val defaults = listOf(
            LanugageConstructsGenerator(),
            PrimitiveGenerator(),
            DateTimeGenerator(),
            FunktionaleGenerator(),
            CollectionGenerator.instances.random,
            CoinToss.instances.even)

    private val generator = defaults.plus(overrides.toList()).fold(CompositeGenerator()) { memo, next -> memo.with(next) }

    fun <R : Any> mk(c: KClass<R>): R = mk(c.java)

    @Suppress("UNCHECKED_CAST")
    fun <R : Any> mk(c: Class<R>): R = (generator.mk(c.kotlin.defaultType.javaType, this) ?: fallback(c)) as R

    private fun <R : Any> fallback(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map {
                    if (it.type.isMarkedNullable) {
                        if (mk(CoinToss::class).toss()) {
                            generator.mk(it.type.javaType, this) ?: mk(Class.forName(it.type.toString().replace("?", "")))
                        } else {
                            null
                        }
                    } else {
                        generator.mk(it.type.javaType, this) ?: mk(Class.forName(it.type.toString()))
                    }
                }
        return constructor.call(*generatedParameters.toTypedArray())
    }
}
