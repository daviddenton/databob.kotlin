package io.github.databob

import java.lang.reflect.Type
import java.net.URL

class JdkCommonsGenerator : Generator {

    private val generator = CompositeGenerator(
        Generators.ofType { d -> URL("http", d.mk<String>(), d.mk<Int>(), d.mk<String>()) },
        Generators.ofType { d -> Exception(d.mk<String>()) },
        Generators.ofType { d -> RuntimeException(d.mk<String>()) }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}