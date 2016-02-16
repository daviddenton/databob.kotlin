package io.github.databob

import kotlin.reflect.KType
import kotlin.reflect.defaultType

class CollectionGenerator : Generator {
    private val lookup: Map<KType, (KType) -> Any> = mapOf(
            Set::class.defaultType to { t -> setOf("") },
            List::class.defaultType to { t -> listOf("") },
            Map::class.defaultType to { t -> mapOf("" to "") }
    )

    override fun get(type: KType): Any? {
        println("CollectionGenerator" + type)
        return lookup[type]?.invoke(type)
    }
}