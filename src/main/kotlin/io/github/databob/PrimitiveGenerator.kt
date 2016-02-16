package io.github.databob

import kotlin.reflect.KType
import kotlin.reflect.defaultType

class PrimitiveGenerator : Generator {

    private val lookup: Map<KType, () -> Any> = mapOf(
            Char::class.defaultType to { -> 'c' },
            Long::class.defaultType to { -> 1L },
            Float::class.defaultType to { -> 1.0f },
            Double::class.defaultType to { -> 1.toDouble() },
            java.lang.Double::class.defaultType to { -> 1.toDouble() },
            Boolean::class.defaultType to { -> true },
            String::class.defaultType to { -> "bob" },
            Int::class.defaultType to { -> 1 }
    )

    override fun get(type: KType, instance: Databob): Any? {
        return lookup[type]?.invoke()
    }
}