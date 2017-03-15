package io.github.databob.unit

import io.github.databob.CoinToss
import io.github.databob.Databob
import org.junit.Test
import kotlin.reflect.full.defaultType
import kotlin.reflect.jvm.javaType
import kotlin.test.assertTrue

class CoinTossTest {

    @Test
    fun alwaysHeads() {
        CoinToss::class.defaultType
        assertTrue(IntRange(0, 100)
            .map { CoinToss.generators.alwaysHeads.mk(CoinToss::class.defaultType.javaType, Databob()) as CoinToss }
            .map { it.toss() }
            .all { it })
    }

    @Test
    fun alwaysTails() {
        assertTrue(IntRange(0, 100)
            .map { CoinToss.generators.alwaysTails.mk(CoinToss::class.defaultType.javaType, Databob()) as CoinToss }
            .map { it.toss() }
            .all { !it })
    }

    @Test
    fun even() {
        assertTrue(IntRange(0, 100)
            .map { CoinToss.generators.even.mk(CoinToss::class.defaultType.javaType, Databob()) as CoinToss }
            .groupBy { it.toss() }
            .entries.iterator().next()
            .value.size > 40)
    }
}

