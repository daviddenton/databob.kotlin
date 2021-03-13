package io.github.databob

import java.io.File
import java.io.PrintStream
import java.io.StringReader
import java.io.StringWriter
import java.lang.reflect.Type
import java.net.URL
import java.util.UUID

class JdkCommonsGenerator : Generator {

    private val generator = CompositeGenerator(
        Generators.ofType { d -> URL("http", d.mk(), d.mk(), d.mk()) },
        Generators.ofType { d -> Exception(d.mk<String>()) },
        Generators.ofType { d -> RuntimeException(d.mk<String>()) },
        Generators.ofType { -> UUID.randomUUID() },
        Generators.ofType { d -> StringReader(d.mk()) },
        Generators.ofType { -> StringWriter() },
        Generators.ofType { d -> PrintStream(d.mk<String>()) },
        Generators.ofType { d -> File(d.mk<String>()) }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}