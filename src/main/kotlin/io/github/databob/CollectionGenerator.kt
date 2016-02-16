package io.github.databob

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class CollectionGenerator : Generator {

    private val toSet = { t: List<Any> -> setOf(t[0]) }
    private val toList = { t: List<Any> -> listOf(t[0]) }
    private val toMap = { t: List<Any> -> mapOf(t[0] to t[1]) }

    private val lookup: Map<Type, (List<Any>) -> Any> = mapOf(
            Set::class.defaultType.javaType to toSet,
            List::class.defaultType.javaType to toList,
            Map::class.defaultType.javaType to toMap
    )

    override fun get(type: KType, instance: Databob): Any? {
        if (type.javaType is ParameterizedType) {
            val coreType = type.javaType as ParameterizedType
            val key = coreType.rawType ?: ""
            val map: List<Any> = coreType.actualTypeArguments.map { t ->
                instance.mk(Class.forName(t.typeName)) ?: ""
            }
            return lookup[key]?.invoke(map)
        } else {
            return null
        }
    }
}