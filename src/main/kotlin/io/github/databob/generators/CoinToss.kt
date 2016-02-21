package io.github.databob.generators

import io.github.databob.Generators
import java.util.*

class CoinToss(private val successRate: Int) {
    object generators {
        val even = Generators.ofType { -> CoinToss(50) }
        val alwaysTails = Generators.ofType { -> CoinToss(0) }
        val alwaysHeads = Generators.ofType { -> CoinToss(100) }
    }

    init {
        if (successRate < 0 || successRate > 100) throw IllegalArgumentException("Success rate % must be 0-100")
    }

    fun toss(): Boolean = Random().nextInt(100) < successRate
}


