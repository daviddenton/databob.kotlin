package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

data class CollectionSizeRange(val min: Int, val max: Int) {

    init {
        if (min > max) throw IllegalArgumentException("Cannot construct negative sized range")
    }

    object generators {
        private fun generatorFor(min: Int, max: Int) = object : Generator {
            override fun mk(type: Type, databob: Databob): Any? =
                    if (type == CollectionSizeRange::class.defaultType.javaType) {
                        CollectionSizeRange(min, max)
                    } else {
                        null
                    }
        }

        val empty = generatorFor(0, 0)

        fun exactly(value: Int): Generator = generatorFor(value, value)

        fun between(min: Int, max: Int): Generator = generatorFor(min, max)

        fun atMost(max: Int): Generator = generatorFor(0, max)
    }

    fun toRandomRange(): IntRange = when {
        min == 0 && max == 0 -> IntRange.EMPTY
        min == max -> IntRange(0, max - 1)
        else -> IntRange(0, min - 1 + Random().nextInt(max - min))
    }
}
