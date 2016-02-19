package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import org.funktionale.option.Option
import org.funktionale.option.Option.Some
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class FunktionaleGenerator : Generator {

    private val lookup: Map<Type, (List<Any>) -> Any> = mapOf(
            Option::class.defaultType.javaType to { t -> Some(t[0]) }
//            Either::class.defaultType.javaType to { t -> Right<*,*>(t[1]) }

    )

    override fun mk(type: KType, databob: Databob): Any? = when {
        type.javaType is ParameterizedType -> {
            val coreType = type.javaType as ParameterizedType
            lookup[coreType.rawType]?.invoke(coreType.actualTypeArguments
                    .map { databob.mk(Class.forName(it.typeName)) ?: "" })
        }
        else -> null
    }
}