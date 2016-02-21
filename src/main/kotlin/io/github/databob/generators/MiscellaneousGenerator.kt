package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class MiscellaneousGenerator : Generator {

    private val lookup: Map<Type, (Databob) -> Any> = mapOf(
            Exception::class.defaultType.javaType to { d -> Exception(d.mk(String::class)) },
            RuntimeException::class.defaultType.javaType to { d -> RuntimeException(d.mk(String::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = lookup[type]?.invoke(databob)
}