package io.github.databob

import java.lang.reflect.Type
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

object Generators {
    inline fun <T> isAssignableFrom(clazz: Class<T> , crossinline fn: () -> T): Generator = object : Generator {
        override fun mk(type: Type, databob: Databob): Any? {
            if (Class.forName(type.typeName).isAssignableFrom(clazz)) {
                return fn()
            } else {
                return null
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