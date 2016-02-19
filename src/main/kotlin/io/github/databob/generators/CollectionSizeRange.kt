package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.util.*
import kotlin.reflect.KType

data class CollectionSizeRange(val min: Int, val max: Int) {

    init {
        if (min > max) throw IllegalArgumentException("Cannot construct negative sized range")
    }

    object generators {
        fun empty(): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? = CollectionSizeRange(0, 0)
        }

        fun exactly(value: Int): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? = CollectionSizeRange(value, value)
        }

        fun between(min: Int, max: Int): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? = CollectionSizeRange(min, max)
        }

        fun atMost(value: Int): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? = CollectionSizeRange(0, value)
        }
    }

    fun toRandomRange(): IntRange = when {
        min == 0 && max == 0 -> IntRange.EMPTY
        min == max -> IntRange(0, max - 1)
        else -> IntRange(0, min - 1 + Random().nextInt(max - min))
    }
}
