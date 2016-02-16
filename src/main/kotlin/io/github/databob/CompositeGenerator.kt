package io.github.databob

import kotlin.reflect.KType

class CompositeGenerator private constructor(private val generators: List<Generator>) : Generator {

    constructor(generator: Generator) : this(listOf(generator))

    fun with(newGenerator: Generator): CompositeGenerator {
        return CompositeGenerator(generators.plus(newGenerator))
    }

    override fun get(type: KType): Any? {
        return generators.find { g -> g.get(type) != null }?.get(type)
    }
}
