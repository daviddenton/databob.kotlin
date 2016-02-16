package io.github.databob

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class CollectionGenerator : Generator {

    private val lookup: Map<Type, (DataBobInstance, ParameterizedType) -> Any> = mapOf(
            Set::class.defaultType.javaType to { d, t -> setOf(d.mk(t.javaClass)) },
            List::class.defaultType.javaType to { d, t -> listOf(d.mk(t.javaClass)) },
            Map::class.defaultType.javaType to { d, t -> mapOf("" to "") }
    )

    override fun get(type: KType, instance: DataBobInstance): Any? {
        val coreType = type.javaType as ParameterizedType
        val key = coreType.rawType ?: String::class.defaultType.javaType
        val invoke = lookup[key]?.invoke(instance, coreType)
        return invoke
    }
}