package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type

class CompositeGenerator (private val generators: List<Generator>) : Generator {
    constructor(vararg generators: Generator) : this(generators.asList())

    fun with(newGenerator: Generator): CompositeGenerator = CompositeGenerator(listOf(newGenerator).plus(generators))

    override fun mk(type: Type, databob: Databob): Any? = generators.find { it.mk(type, databob) != null }?.mk(type, databob)
}
