package io.github.databob

import java.util.*

class CollectionSizeRange(private val min: Int, private val max: Int) {

    init {
        if (min > max) throw IllegalArgumentException("Cannot construct negative sized range")
    }

    object generators {
        val empty = Generators.ofType { -> CollectionSizeRange(0, 0) }

        fun exactly(value: Int): Generator = Generators.ofType { -> CollectionSizeRange(value, value) }

        fun between(min: Int, max: Int): Generator = Generators.ofType { -> CollectionSizeRange(min, max) }

        fun atMost(max: Int): Generator = Generators.ofType { -> CollectionSizeRange(0, max) }
    }

    fun toRandomRange(): IntRange = when {
        min == 0 && max == 0 -> IntRange.EMPTY
        min == max -> IntRange(0, max - 1)
        else -> IntRange(0, min - 1 + Random().nextInt(max - min))
    }
}
