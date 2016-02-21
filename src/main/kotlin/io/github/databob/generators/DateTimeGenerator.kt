package io.github.databob.generators

import io.github.databob.Databob
import io.github.databob.Generator
import java.lang.reflect.Type
import java.sql.Timestamp
import java.time.*
import java.util.*
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

class DateTimeGenerator : Generator {

    private val lookup: Map<Type, (Databob) -> Any> = mapOf(
            ZoneId::class.defaultType.javaType to { d -> ZoneId.systemDefault() },
            Date::class.defaultType.javaType to { d -> Date() },
            LocalTime::class.defaultType.javaType to { d -> LocalTime.now() },
            LocalDate::class.defaultType.javaType to { d -> LocalDate.now() },
            LocalDateTime::class.defaultType.javaType to { d -> LocalDateTime.of(d.mk(LocalDate::class), d.mk(LocalTime::class)) },
            ZonedDateTime::class.defaultType.javaType to { d -> ZonedDateTime.of(d.mk(LocalDateTime::class), d.mk(ZoneId::class)) },
            java.sql.Date::class.defaultType.javaType to { d -> java.sql.Date(d.mk(Date::class).time) },
            Timestamp::class.defaultType.javaType to { d -> Timestamp.valueOf(d.mk(LocalDateTime::class)) },
            Duration::class.defaultType.javaType to { d -> Duration.ofMillis(d.mk(Long::class)) },
            Period::class.defaultType.javaType to { d -> Period.between(d.mk(LocalDate::class), d.mk(LocalDate::class)) }
    )

    override fun mk(type: Type, databob: Databob): Any? = lookup[type]?.invoke(databob)
}