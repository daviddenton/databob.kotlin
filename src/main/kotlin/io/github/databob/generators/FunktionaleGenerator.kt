package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import org.funktionale.either.Either
import org.funktionale.either.toRight
import org.funktionale.option.Option
import org.funktionale.option.Option.Some
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class FunktionaleGenerator : Generator {

    private val lookup: Map<Type, (Array<Type>, Databob) -> Any> = mapOf(
            Option::class.defaultType.javaType to { t, d -> Some(d.mk(Class.forName(t[0].typeName))) },
            Either::class.defaultType.javaType to { t, d -> Pair(d.mk(Class.forName(t[0].typeName)), d.mk(Class.forName(t[1].typeName))).toRight() }
    )

    override fun mk(type: Type, databob: Databob): Any? = when (type) {
        is ParameterizedType -> {
            lookup[type.rawType]?.invoke(type.actualTypeArguments, databob)
        }
        else -> null
    }
}