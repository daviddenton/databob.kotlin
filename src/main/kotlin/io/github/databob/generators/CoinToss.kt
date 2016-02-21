package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class CoinToss(private val successRate: Int) {

    object instances {
        fun successRatioOf(rate: Int) = object : Generator {
            override fun mk(type: Type, databob: Databob): Any? =
                    if (type == CoinToss::class.defaultType.javaType) {
                        CoinToss(rate)
                    } else {
                        null
                    }
        }

        val even = successRatioOf(50)
        val alwaysTails = successRatioOf(0)
        val alwaysHeads = successRatioOf(100)
    }

    init {
        if (successRate < 0 || successRate > 100) throw IllegalArgumentException("Success rate % must be 0-100")

    }

    fun toss(): Boolean = Random().nextInt(100) < successRate
}


