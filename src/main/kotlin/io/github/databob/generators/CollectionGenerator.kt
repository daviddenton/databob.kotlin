package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class CollectionGenerator : Generator {

    private val lookup: Map<Type, (List<Any>) -> Any> = mapOf(
            Set::class.defaultType.javaType to { t -> setOf(t[0]) },
            List::class.defaultType.javaType to { t -> listOf(t[0]) },
            Map::class.defaultType.javaType to { t -> mapOf(t[0] to t[1]) }
    )

    override fun get(type: KType, databob: Databob): Any? {
        return when {
            type.javaType is ParameterizedType -> {
                val coreType = type.javaType as ParameterizedType
                val key = coreType.rawType ?: ""
                return lookup[key]?.invoke(coreType.actualTypeArguments
                        .map { databob.mk(Class.forName(it.typeName)) ?: "" })
            }
            else -> null
        }
    }
}