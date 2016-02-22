package io.github.databob

import org.funktionale.either.toRight
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class FunktionaleGenerator : io.github.databob.Generator {

    private val lookup: Map<java.lang.reflect.Type, (Array<java.lang.reflect.Type>, io.github.databob.Databob) -> Any> = mapOf(
            org.funktionale.option.Option::class.defaultType.javaType to { t, d -> org.funktionale.option.Option.Some(d.mk(Class.forName(t[0].typeName))) },
            org.funktionale.either.Either::class.defaultType.javaType to { t, d -> Pair(d.mk(Class.forName(t[0].typeName)), d.mk(Class.forName(t[1].typeName))).toRight() }
    )

    override fun mk(type: java.lang.reflect.Type, databob: io.github.databob.Databob): Any? = when (type) {
        is java.lang.reflect.ParameterizedType -> {
            lookup[type.rawType]?.invoke(type.actualTypeArguments, databob)
        }
        else -> null
    }
}