package io.github.databob

import java.math.BigDecimal
import java.math.BigInteger
import java.util.Random
import java.util.UUID
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

class PrimitiveGenerator : Generator {

    private val lookup: Map<String, (Databob) -> Any> = mapOf(
        "string" to { d -> UUID.randomUUID().toString() },
        "java.lang.String" to { d -> UUID.randomUUID().toString() },

        "short" to { d -> Random().nextInt(Short.MAX_VALUE.toInt()).toShort() },
        "java.lang.Short" to { d -> Random().nextInt(Short.MAX_VALUE.toInt()).toShort() },

        "char" to { d -> Random().nextInt(256).toChar() },
        "java.lang.Character" to { d -> Random().nextInt(256).toChar() },

        "double" to { d -> Random().nextDouble() * Int.MAX_VALUE },
        "java.lang.Double" to { d -> Random().nextDouble() * Int.MAX_VALUE },

        "boolean" to { d -> d.mk<CoinToss>().toss() },
        "java.lang.Boolean" to { d -> d.mk<CoinToss>().toss() },

        "int" to { d -> d.mk<Double>().toInt() },
        "java.lang.Integer" to { d -> d.mk<Double>().toInt() },

        "long" to { d -> d.mk<Int>().toLong() },
        "java.lang.Long" to { d -> d.mk<Int>().toLong() },

        "byte" to { d -> d.mk<Char>().toByte() },
        "java.lang.Byte" to { d -> d.mk<Char>().toByte() },

        "float" to { d -> d.mk<Double>().toFloat() },
        "java.lang.Float" to { d -> d.mk<Double>().toFloat() },

        "java.math.BigInteger" to { d -> BigInteger.valueOf(d.mk<Long>()) },
        "java.math.BigDecimal" to { d -> BigDecimal.valueOf(d.mk<Double>()) }
    )

    override fun mk(type: KType, databob: Databob): Any? = lookup[type.javaType.typeName]?.invoke(databob)
}