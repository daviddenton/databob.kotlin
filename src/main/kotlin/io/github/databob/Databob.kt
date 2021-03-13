package io.github.databob

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.defaultType
import kotlin.reflect.jvm.javaType

class Databob(vararg overrides: Generator) {
    private val defaults = listOf(
        LanguageConstructsGenerator(),
        DateTimeGenerator.instances.random,
        CollectionGenerator.instances.random,
        PrimitiveGenerator(),
        JdkCommonsGenerator(),
        CoinToss.generators.even)

    private val generator = defaults.plus(overrides.toList()).fold(CompositeGenerator(), CompositeGenerator::with)

    inline fun <reified R : Any> mk(): R {
        return mk(R::class)
    }

    @Suppress("UNCHECKED_CAST")
    fun <R : Any> mk(c: KClass<R>): R = (generator.mk(c.defaultType.javaType, this) ?: fallback(c)) as R

    private fun <R : Any> fallback(c: KClass<R>): R {
        val constructor = c.constructors.iterator().next()
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

    private fun convert(it: KParameter) = generator.mk(it.type.javaType, this) ?: mk(Class.forName(it.type.toString().replace("?", "")).kotlin)
}
