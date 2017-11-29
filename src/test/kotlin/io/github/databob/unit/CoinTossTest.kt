package io.github.databob.unit

import io.github.databob.CoinToss
import io.github.databob.Databob
import org.junit.Test
import kotlin.reflect.full.createType
import kotlin.test.assertTrue

class CoinTossTest {

    @Test
    fun alwaysHeads() {
        assertTrue(IntRange(0, 100)
            .map { CoinToss.generators.alwaysHeads.mk(CoinToss::class.createType(), Databob()) as CoinToss }
            .map { it.toss() }
            .all { it })
    }

    @Test
    fun alwaysTails() {
        assertTrue(IntRange(0, 100)
            .map { CoinToss.generators.alwaysTails.mk(CoinToss::class.createType(), Databob()) as CoinToss }
            .map { it.toss() }
            .all { !it })
    }

    @Test
    fun even() {
        assertTrue(IntRange(0, 100)
            .map { CoinToss.generators.even.mk(CoinToss::class.createType(), Databob()) as CoinToss }
            .groupBy { it.toss() }
            .entries.iterator().next()
            .value.size > 40)
    }
}

