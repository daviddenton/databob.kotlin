package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KType
import kotlin.reflect.defaultType

class PrimitiveGenerator : Generator {

    private val lookup: Map<KType, (Databob) -> Any> = mapOf(
            Double::class.defaultType to { d -> 0.0 },
            String::class.defaultType to { d -> "bob" },
            Boolean::class.defaultType to { d -> true },
            Char::class.defaultType to { d -> d.mk(Int::class).toChar() },
            Byte::class.defaultType to { d -> d.mk(Int::class).toByte() },
            Float::class.defaultType to { d -> d.mk(Double::class).toFloat() },
            Short::class.defaultType to { d -> d.mk(Int::class).toShort() },
            Long::class.defaultType to { d -> d.mk(Int::class).toLong() },
            Int::class.defaultType to { d -> d.mk(Double::class).toInt() },
            BigInteger::class.defaultType to { d -> BigInteger.valueOf(d.mk(Long::class)) },
            BigDecimal::class.defaultType to { d -> BigDecimal.valueOf(d.mk(Double::class)) },
            Exception::class.defaultType to { d -> Exception(d.mk(String::class)) },
            RuntimeException::class.defaultType to { d -> RuntimeException(d.mk(String::class)) }
    )

    override fun mk(type: KType, databob: Databob): Any? = lookup[type]?.invoke(databob)
}