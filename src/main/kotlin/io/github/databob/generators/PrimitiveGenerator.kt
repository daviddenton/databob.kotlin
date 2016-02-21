package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class PrimitiveGenerator : Generator {

    private val lookup: Map<Type, (Databob) -> Any> = mapOf(
            Double::class.defaultType.javaType to { d -> 0.0 },
            String::class.defaultType.javaType to { d -> "bob" },
            Boolean::class.defaultType.javaType to { d -> true },
            Char::class.defaultType.javaType to { d -> d.mk(Int::class).toChar() },
            Byte::class.defaultType.javaType to { d -> d.mk(Int::class).toByte() },
            Float::class.defaultType.javaType to { d -> d.mk(Double::class).toFloat() },
            Short::class.defaultType.javaType to { d -> d.mk(Int::class).toShort() },
            Long::class.defaultType.javaType to { d -> d.mk(Int::class).toLong() },
            Int::class.defaultType.javaType to { d -> d.mk(Double::class).toInt() },
            BigInteger::class.defaultType.javaType to { d -> BigInteger.valueOf(d.mk(Long::class)) },
            BigDecimal::class.defaultType.javaType to { d -> BigDecimal.valueOf(d.mk(Double::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = lookup[type]?.invoke(databob)
}