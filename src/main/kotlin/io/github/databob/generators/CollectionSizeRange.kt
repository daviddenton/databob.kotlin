package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.util.*
import kotlin.reflect.KType
import kotlin.reflect.defaultType

data class CollectionSizeRange(val min: Int, val max: Int) {

    init {
        if (min > max) throw IllegalArgumentException("Cannot construct negative sized range")
    }

    object generators {
        val empty = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? =
                    if (type == CollectionSizeRange::class.defaultType) {
                        CollectionSizeRange(0, 0)
                    } else {
                        null
                    }
        }

        fun exactly(value: Int): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? =
                    if (type == CollectionSizeRange::class.defaultType) {
                        null
                    } else {
                        CollectionSizeRange(value, value)
                    }
        }

        fun between(min: Int, max: Int): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? =
                    if (type == CollectionSizeRange::class.defaultType) {
                        CollectionSizeRange(min, max)
                    } else {
                        null
                    }
        }

        fun atMost(max: Int): Generator = object : Generator {
            override fun mk(type: KType, databob: Databob): Any? =
                    if (type == CollectionSizeRange::class.defaultType) {
                        CollectionSizeRange(0, max)
                    } else {
                        null
                    }
        }
    }

    fun toRandomRange(): IntRange = when {
        min == 0 && max == 0 -> IntRange.EMPTY
        min == max -> IntRange(0, max - 1)
        else -> IntRange(0, min - 1 + Random().nextInt(max - min))
    }
}
