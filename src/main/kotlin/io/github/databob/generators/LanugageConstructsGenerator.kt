package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import io.github.databob.Generators
import java.lang.reflect.Type

class LanugageConstructsGenerator : Generator {

    private val generator = CompositeGenerator(
            Generators.ofType { d -> Exception(d.mk(String::class)) },
            Generators.ofType { d -> RuntimeException(d.mk(String::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}