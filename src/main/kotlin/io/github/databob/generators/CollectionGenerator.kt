package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import io.github.databob.Generators
import java.lang.reflect.Type

class CollectionGenerator : Generator {

    object instances {
        val random = CompositeGenerator(listOf(
                Generators.ofType { -> CollectionSizeRange(1, 5) },
                CollectionGenerator())
        )
    }

    private fun ctr(databob: Databob, type: Type): Any = databob.mk(Class.forName(type.typeName))

    private inline fun <reified T> construct(databob: Databob, ctrFn: () -> T): Array<T> {
        return databob.mk(CollectionSizeRange::class).toRandomRange()
                .map { ctrFn() }.toTypedArray()
    }

    private val generator = CompositeGenerator(
            Generators.ofType { t, d -> setOf(*construct (d) { ctr(d, t[0]) }) },
            Generators.ofType { t, d -> listOf(*construct (d) { ctr(d, t[0]) }) },
            Generators.ofType { t, d -> mapOf(*construct(d) { Pair(ctr(d, t[0]), ctr(d, t[1])) }) }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}