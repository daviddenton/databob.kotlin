package io.github.databob

import java.lang.reflect.Type

class LanguageConstructsGenerator : Generator {

    private val generator = CompositeGenerator(
        Generators.ofType { d -> Exception(d.mk<String>()) },
        Generators.ofType { d -> RuntimeException(d.mk<String>()) },
        object : Generator {
            override fun mk(type: Type, databob: Databob): Any? = when {
                Class.forName(type.typeName).isEnum -> Class.forName(type.typeName).enumConstants[0]
                else -> null
            }
        }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}