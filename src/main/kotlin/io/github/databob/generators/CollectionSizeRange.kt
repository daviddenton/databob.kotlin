package io.github.databob.generators

import io.github.databob.Generator
import io.github.databob.Generators
import java.util.*

class CollectionSizeRange(private val min: Int, private val max: Int) {

    init {
        if (min > max) throw IllegalArgumentException("Cannot construct negative sized range")
    }

    object generators {
        val empty = Generators.matchingType { -> CollectionSizeRange(0, 0) }

        fun exactly(value: Int): Generator = Generators.matchingType { -> CollectionSizeRange(value, value) }

        fun between(min: Int, max: Int): Generator = Generators.matchingType { -> CollectionSizeRange(min, max) }

        fun atMost(max: Int): Generator = Generators.matchingType { -> CollectionSizeRange(0, max) }
    }

    fun toRandomRange(): IntRange = when {
        min == 0 && max == 0 -> IntRange.EMPTY
        min == max -> IntRange(0, max - 1)
        else -> IntRange(0, min - 1 + Random().nextInt(max - min))
    }
}
