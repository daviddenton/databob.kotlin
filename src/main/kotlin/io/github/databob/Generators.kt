package io.github.databob

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

object Generators {

    fun ofType(clazz: KClass<*>, fn: (Array<Type>, Databob) -> Any): Generator {
        return object : Generator {
            override fun mk(type: Type, databob: Databob): Any? = when {
                type is ParameterizedType && clazz.defaultType.javaType == type.rawType -> {
                    fn(type.actualTypeArguments, databob)
                }
                else -> null
            }
        }
    }


    inline fun <reified T> ofType(crossinline fn: () -> T): Generator = object : Generator {
        override fun mk(type: Type, databob: Databob): Any? {
            if (type == T::class.defaultType.javaType) {
                return fn()
            } else {
                return null
            }
        }
    }

    inline fun <reified T> ofType(crossinline fn: (Databob) -> T): Generator = object : Generator {
        override fun mk(type: Type, databob: Databob): Any? {
            if (type == T::class.defaultType.javaType) {
                return fn(databob)
            } else {
                return null
            }
        }
    }
}