package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import io.github.databob.Generators
import java.lang.reflect.Type
import java.sql.Timestamp
import java.time.*
import java.util.*

class DateTimeGenerator : Generator {

    private val generator = CompositeGenerator(
            Generators.ofType { d -> ZoneId.systemDefault() },
            Generators.ofType { d -> Date() },
            Generators.ofType { d -> LocalTime.now() },
            Generators.ofType { d -> LocalDate.now() },
            Generators.ofType { d -> LocalDateTime.of(d.mk(LocalDate::class), d.mk(LocalTime::class)) },
            Generators.ofType { d -> ZonedDateTime.of(d.mk(LocalDateTime::class), d.mk(ZoneId::class)) },
            Generators.ofType { d -> java.sql.Date(d.mk(Date::class).time) },
            Generators.ofType { d -> Timestamp.valueOf(d.mk(LocalDateTime::class)) },
            Generators.ofType { d -> Duration.ofMillis(d.mk(Long::class)) },
            Generators.ofType { d -> Period.between(d.mk(LocalDate::class), d.mk(LocalDate::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = generator.mk(type, databob)
}