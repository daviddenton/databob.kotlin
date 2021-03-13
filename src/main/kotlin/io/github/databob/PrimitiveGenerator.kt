package io.github.databob

import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Random
import java.util.UUID

class PrimitiveGenerator : Generator {

    private val lookup: Map<String, (Databob) -> Any> = mapOf(
        "string" to { UUID.randomUUID().toString() },
        "java.lang.String" to { UUID.randomUUID().toString() },

        "short" to { Random().nextInt(Short.MAX_VALUE.toInt()).toShort() },
        "java.lang.Short" to { Random().nextInt(Short.MAX_VALUE.toInt()).toShort() },

        "char" to { Random().nextInt(256).toChar() },
        "java.lang.Character" to { Random().nextInt(256).toChar() },

        "double" to { Random().nextDouble() * Int.MAX_VALUE },
        "java.lang.Double" to { Random().nextDouble() * Int.MAX_VALUE },

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

    override fun mk(type: Type, databob: Databob): Any? = lookup[type.typeName]?.invoke(databob)
}