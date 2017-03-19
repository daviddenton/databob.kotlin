package io.github.databob

import java.lang.reflect.Type

class CompositeGenerator(private val generators: List<Generator>) : Generator {
    constructor(vararg generators: Generator) : this(generators.asList())

    fun with(newGenerator: Generator): CompositeGenerator = CompositeGenerator(listOf(newGenerator).plus(generators))

    override fun mk(type: Type, databob: Databob): Any? = generators.find { it.mk(type, databob) != null }?.mk(type, databob)
}
