package io.github.databob

import kotlin.reflect.KType
import kotlin.reflect.defaultType

class CompositeGenerator : Generator {

    private val generators = listOf<Generator>()

    override fun get(type: KType): Any? {
        throw UnsupportedOperationException()
    }

}
class CollectionGenerator : Generator {

    private val lookup: Map<KType, () -> Any> = mapOf(
            List::class.defaultType to { -> listOf("") },
            Map::class.defaultType to { -> mapOf("" to "") }
    )

    override fun get(type: KType): Any? {
        return lookup[type]?.invoke()
    }

}