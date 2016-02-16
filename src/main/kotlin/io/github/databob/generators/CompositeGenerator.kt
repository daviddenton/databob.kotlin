package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import kotlin.reflect.KType

class CompositeGenerator private constructor(private val generators: List<Generator>) : Generator {
    constructor() : this(listOf())

    fun with(newGenerator: Generator): CompositeGenerator {
        return CompositeGenerator(generators.plus(newGenerator))
    }

    override fun mk(type: KType, databob: Databob): Any? {
        return generators.find { it.mk(type, databob) != null }?.mk(type, databob)
    }
}
