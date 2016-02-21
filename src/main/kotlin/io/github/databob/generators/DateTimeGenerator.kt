package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import io.github.databob.Generators
import java.lang.reflect.Type
import java.sql.Timestamp
import java.time.*
import java.util.*

class DateTimeGenerator : Generator {

    /**
     * Pre-packed generator instances
     */
    object instances {
        private val defaults = CompositeGenerator(
                Generators.ofType { d -> ZoneId.systemDefault() },
                Generators.ofType { d -> LocalDateTime.of(d.mk(LocalDate::class), d.mk(LocalTime::class)) },
                Generators.ofType { d -> ZonedDateTime.of(d.mk(LocalDateTime::class), d.mk(ZoneId::class)) },
                Generators.ofType { d -> java.sql.Date(d.mk(Date::class).time) },
                Generators.ofType { d -> Timestamp.valueOf(d.mk(LocalDateTime::class)) },
                Generators.ofType { d -> Duration.ofMillis(d.mk(Long::class)) },
                Generators.ofType { d -> Period.between(d.mk(LocalDate::class), d.mk(LocalDate::class)) }
        )

        val random = CompositeGenerator(
                Generators.ofType { d -> Date(d.mk(Long::class)) },
                Generators.ofType { d -> LocalTime.ofNanoOfDay(d.mk(Int::class).toLong()) },
                Generators.ofType { d -> LocalDate.ofEpochDay(d.mk(Short::class).toLong()) }
        ).with(defaults)

        val now = CompositeGenerator(
                Generators.ofType { -> Date() },
                Generators.ofType { d -> LocalTime.now(d.mk(ZoneId::class)) },
                Generators.ofType { d -> LocalDate.now(d.mk(ZoneId::class)) }
        ).with(defaults)

        val epoch = CompositeGenerator(
                Generators.ofType { -> Date(0) },
                Generators.ofType { -> LocalTime.ofSecondOfDay(0) },
                Generators.ofType { -> LocalDate.ofEpochDay(0) }
        ).with(defaults)
    }

    private val generator = CompositeGenerator(
            Generators.ofType { d -> ZoneId.systemDefault() },
            Generators.ofType { d -> Date(d.mk(Long::class)) },
            Generators.ofType { d -> LocalTime.ofNanoOfDay(d.mk(Int::class).toLong()) },
            Generators.ofType { d -> LocalDate.ofEpochDay(d.mk(Short::class).toLong()) },
            Generators.ofType { d -> LocalDateTime.of(d.mk(LocalDate::class), d.mk(LocalTime::class)) },
            Generators.ofType { d -> ZonedDateTime.of(d.mk(LocalDateTime::class), d.mk(ZoneId::class)) },
            Generators.ofType { d -> java.sql.Date(d.mk(Date::class).time) },
            Generators.ofType { d -> Timestamp.valueOf(d.mk(LocalDateTime::class)) },
            Generators.ofType { d -> Duration.ofMillis(d.mk(Long::class)) },
            Generators.ofType { d -> Period.between(d.mk(LocalDate::class), d.mk(LocalDate::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}