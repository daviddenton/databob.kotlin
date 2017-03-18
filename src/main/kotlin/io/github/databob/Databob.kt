package io.github.databob

import kotlin.reflect.KParameter
import kotlin.reflect.full.defaultType
import kotlin.reflect.jvm.javaType

class Databob(vararg overrides: Generator) {
    private val defaults = listOf(
            LanguageConstructsGenerator(),
            DateTimeGenerator.instances.random,
            FunktionaleGenerator(),
            CollectionGenerator.instances.random,
            PrimitiveGenerator(),
            JdkCommonsGenerator(),
            CoinToss.generators.even)

    private val generator = defaults.plus(overrides.toList()).fold(CompositeGenerator()) { memo, next -> memo.with(next) }

    inline fun <reified R : Any> mk(): R {
        return mk(R::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun <R : Any> mk(c: Class<R>): R = (generator.mk(c.kotlin.defaultType.javaType, this) ?: fallback(c)) as R

    private fun <R : Any> fallback(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val generatedParameters = constructor.parameters
                .map {
                    if (it.type.isMarkedNullable) {
                        if (mk<CoinToss>().toss()) {
                            convert(it)
                        } else {
                            null
                        }
                    } else {
                        convert(it)
                    }
                }
        return constructor.call(*generatedParameters.toTypedArray())
    }

    private fun convert(it: KParameter) = generator.mk(it.type.javaType, this) ?: mk(Class.forName(it.type.toString().replace("?", "")))
}
