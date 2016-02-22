package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import io.github.databob.Generators
import java.lang.reflect.Type

class LanguageConstructsGenerator : Generator {

    private val generator = CompositeGenerator(
            Generators.ofType { d -> Exception(d.mk(String::class)) },
            Generators.ofType { d -> RuntimeException(d.mk(String::class)) },
            object : Generator {
                override fun mk(type: Type, databob: Databob): Any? = when {
                    Class.forName(type.typeName).isEnum -> Class.forName(type.typeName).enumConstants[0]
                    else -> null
                }
            }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}