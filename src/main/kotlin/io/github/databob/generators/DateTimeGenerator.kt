package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.sql.Timestamp
import java.time.*
import java.util.*
import kotlin.reflect.KType
import kotlin.reflect.defaultType

class DateTimeGenerator : Generator {

    private val lookup: Map<KType, (Databob) -> Any> = mapOf(
            ZoneId::class.defaultType to { d -> ZoneId.systemDefault() },
            Date::class.defaultType to { d -> Date() },
            LocalTime::class.defaultType to { d -> LocalTime.now() },
            LocalDate::class.defaultType to { d -> LocalDate.now() },
            LocalDateTime::class.defaultType to { d -> LocalDateTime.of(d.mk(LocalDate::class), d.mk(LocalTime::class)) },
            ZonedDateTime::class.defaultType to { d -> ZonedDateTime.of(d.mk(LocalDateTime::class), d.mk(ZoneId::class)) },
            java.sql.Date::class.defaultType to { d -> java.sql.Date(d.mk(Date::class).time) },
            Timestamp::class.defaultType to { d -> Timestamp.valueOf(d.mk(LocalDateTime::class)) },
            Duration::class.defaultType to { d -> Duration.ofMillis(d.mk(Long::class)) },
            Period::class.defaultType to { d -> Period.between(d.mk(LocalDate::class), d.mk(LocalDate::class)) }
    )

    override fun mk(type: KType, databob: Databob): Any? {
        return lookup[type]?.invoke(databob)
    }
}