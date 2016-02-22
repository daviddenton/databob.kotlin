package io.github.databob.unit

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isWithin
import io.github.databob.CollectionSizeRange
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class CollectionSizeRangeTest {

    @Test
    fun cannotBeNegative() {
        assertTrue(assertFails { -> CollectionSizeRange(2, 1) } is IllegalArgumentException)
    }

    @Test
    fun zeroSizedRange() {
        assertThat(CollectionSizeRange(0, 0).toRandomRange().toList(), equalTo(listOf()))
    }

    @Test
    fun exactRange() {
        assertThat(CollectionSizeRange(5, 5).toRandomRange().toList().size, equalTo(5))
    }

    @Test
    fun randomRangeInBounds() {
        assertThat(CollectionSizeRange(2, 10).toRandomRange().toList().size, isWithin(IntRange(2, 10)))
    }
}

