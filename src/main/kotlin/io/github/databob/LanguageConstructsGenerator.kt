package io.github.databob

import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

class LanguageConstructsGenerator : Generator {

    private val generator = CompositeGenerator(
        Generators.ofType { d -> Exception(d.mk<String>()) },
        Generators.ofType { d -> RuntimeException(d.mk<String>()) },
        object : Generator {
            override fun mk(type: KType, databob: Databob): Any? = when {
                    Class.forName(type.javaType.typeName).isEnum -> Class.forName(type.javaType.typeName).enumConstants[0]
                else -> null
            }
        }
    )

    override fun mk(type: KType, databob: Databob): Any? = generator.mk(type, databob)
}