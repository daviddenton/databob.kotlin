package io.github.databob

import kotlin.reflect.KType

class CompositeGenerator(private val generators: List<Generator>) : Generator {
    constructor(vararg generators: Generator) : this(generators.asList())

    fun with(newGenerator: Generator): CompositeGenerator = CompositeGenerator(listOf(newGenerator).plus(generators))

    override fun mk(type: KType, databob: Databob): Any? = generators.find { it.mk(type, databob) != null }?.mk(type, databob)
}
