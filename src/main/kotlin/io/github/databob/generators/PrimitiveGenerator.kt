package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class PrimitiveGenerator : Generator {

    private val lookup: Map<Type, (Databob) -> Any> = mapOf(
            Boolean::class.defaultType.javaType to { d -> d.mk(CoinToss::class).toss() },
            Char::class.defaultType.javaType to { d -> Random().nextInt(256).toChar() },
            String::class.defaultType.javaType to { d -> UUID.randomUUID().toString() },
            Double::class.defaultType.javaType to { d -> Random().nextDouble() * Int.MAX_VALUE },
            Int::class.defaultType.javaType to { d -> d.mk(Double::class).toInt() },
            Long::class.defaultType.javaType to { d -> d.mk(Int::class).toLong() },
            Short::class.defaultType.javaType to { d -> d.mk(Int::class).toShort() },
            Byte::class.defaultType.javaType to { d -> d.mk(Char::class).toByte() },
            Float::class.defaultType.javaType to { d -> d.mk(Double::class).toFloat() },
            BigInteger::class.defaultType.javaType to { d -> BigInteger.valueOf(d.mk(Long::class)) },
            BigDecimal::class.defaultType.javaType to { d -> BigDecimal.valueOf(d.mk(Double::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = lookup[type]?.invoke(databob)
}