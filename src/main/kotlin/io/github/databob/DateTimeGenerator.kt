package io.github.databob

import java.sql.Timestamp
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.Year
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import kotlin.reflect.KType

class DateTimeGenerator : Generator {

    /**
     * Pre-packed generator instances
     */
    object instances {
        private val defaults = CompositeGenerator(
            Generators.ofType { -> ZoneId.systemDefault() },
            Generators.ofType { d -> Year.of(d.mk<LocalDateTime>().year) },
            Generators.ofType { d -> d.mk<LocalDateTime>().dayOfWeek },
            Generators.ofType { d -> d.mk<LocalDateTime>().month },
            Generators.ofType { d -> LocalDateTime.of(d.mk<LocalDate>(), d.mk<LocalTime>()) },
            Generators.ofType { d -> ZonedDateTime.of(d.mk<LocalDateTime>(), d.mk<ZoneId>()) },
            Generators.ofType { d -> java.sql.Date(d.mk<Date>().time) },
            Generators.ofType { d -> Timestamp.valueOf(d.mk<LocalDateTime>()) },
            Generators.ofType { d -> Duration.ofMillis(d.mk<Long>()) },
            Generators.ofType { d -> Period.between(d.mk<LocalDate>(), d.mk<LocalDate>()) }
        )

        val random = CompositeGenerator(
            Generators.ofType { d -> Date(d.mk<Long>()) },
            Generators.ofType { d -> LocalTime.ofNanoOfDay(d.mk<Int>().toLong()) },
            Generators.ofType { d -> LocalDate.ofEpochDay(d.mk<Int>().toLong()) }
            ).with(defaults)

        val now = CompositeGenerator(
            Generators.ofType { -> Date() },
            Generators.ofType { d -> LocalTime.now(d.mk<ZoneId>()) },
            Generators.ofType { d -> LocalDate.now(d.mk<ZoneId>()) }
        ).with(defaults)

        val epoch = CompositeGenerator(
            Generators.ofType { -> Date(0) },
            Generators.ofType { -> LocalTime.ofSecondOfDay(0) },
            Generators.ofType { -> LocalDate.ofEpochDay(0) }
        ).with(defaults)
    }

    private val generator = CompositeGenerator(
        Generators.ofType { -> ZoneId.systemDefault() },
        Generators.ofType { d -> Date(d.mk<Long>()) },
        Generators.ofType { d -> LocalTime.ofNanoOfDay(d.mk<Int>().toLong()) },
        Generators.ofType { d -> LocalDate.ofEpochDay(d.mk<Short>().toLong()) },
        Generators.ofType { d -> LocalDateTime.of(d.mk<LocalDate>(), d.mk<LocalTime>()) },
        Generators.ofType { d -> ZonedDateTime.of(d.mk<LocalDateTime>(), d.mk<ZoneId>()) },
        Generators.ofType { d -> java.sql.Date(d.mk<Date>().time) },
        Generators.ofType { d -> Timestamp.valueOf(d.mk<LocalDateTime>()) },
        Generators.ofType { d -> Duration.ofMillis(d.mk<Long>()) },
        Generators.ofType { d -> Period.between(d.mk<LocalDate>(), d.mk<LocalDate>()) }
    )

    override fun mk(type: KType, databob: Databob): Any? = generator.mk(type, databob)
}