package io.github.databob

import java.io.File
import java.io.PrintStream
import java.io.Reader
import java.io.StringReader
import java.io.StringWriter
import java.io.Writer
import java.net.URL
import java.util.UUID
import kotlin.reflect.KType

class JdkCommonsGenerator : Generator {

    private val generator = CompositeGenerator(
        Generators.ofType { d -> URL("http", d.mk<String>(), d.mk<Int>(), d.mk<String>()) },
        Generators.ofType { d -> Exception(d.mk<String>()) },
        Generators.ofType { d -> RuntimeException(d.mk<String>()) },
        Generators.ofType { -> UUID.randomUUID() },
        Generators.ofType { d -> StringReader(d.mk<String>()) as Reader },
        Generators.ofType { -> StringWriter() as Writer },
        Generators.ofType { d -> PrintStream(d.mk<String>()) },
        Generators.ofType { d -> File(d.mk<String>()) }
    )

    override fun mk(type: KType, databob: Databob): Any? = generator.mk(type, databob)
}