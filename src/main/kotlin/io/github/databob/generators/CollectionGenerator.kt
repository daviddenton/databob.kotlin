package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class CollectionGenerator : Generator {

    object instances {
        val empty = CompositeGenerator(listOf(
                CollectionSizeRange.generators.empty,
                CollectionGenerator())
        )
        val nonEmpty = CompositeGenerator(listOf(
                CollectionSizeRange.generators.exactly(1),
                CollectionGenerator())
        )
        val random = CompositeGenerator(listOf(
                CollectionSizeRange.generators.between(1, 5),
                CollectionGenerator())
        )
    }

    private fun ctr(databob: Databob, type: Type): Any = databob.mk(Class.forName(type.typeName)) ?: ""

    private inline fun <reified T> construct(databob: Databob, ctrFn: () -> T): Array<T> {
        return databob.mk(CollectionSizeRange::class).toRandomRange()
                .map { ctrFn() }.toTypedArray()
    }

    private val lookup: Map<Type, (Array<Type>, Databob) -> Any> = mapOf(
            Set::class.defaultType.javaType to { t, d -> setOf(*construct (d) { ctr(d, t[0]) }) },
            List::class.defaultType.javaType to { t, d -> listOf(*construct (d) { ctr(d, t[0]) }) },
            Map::class.defaultType.javaType to { t, d -> mapOf(*construct(d) { Pair(ctr(d, t[0]), ctr(d, t[1])) }) }
    )

    override fun mk(type: KType, databob: Databob): Any? = when {
        type.javaType is ParameterizedType -> {
            val coreType = type.javaType as ParameterizedType
            lookup[coreType.rawType]?.invoke(coreType.actualTypeArguments, databob)
        }
        else -> null
    }
}