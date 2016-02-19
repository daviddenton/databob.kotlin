package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import kotlin.reflect.KType
import kotlin.reflect.defaultType

class MiscellaneousGenerator : Generator {

    private val lookup: Map<KType, (Databob) -> Any> = mapOf(
            Exception::class.defaultType to { d -> Exception(d.mk(String::class)) },
            RuntimeException::class.defaultType to { d -> RuntimeException(d.mk(String::class)) }
    )

    override fun mk(type: KType, databob: Databob): Any? = lookup[type]?.invoke(databob)
}