package io.github.databob.unit

import io.github.databob.Databob
import org.junit.Ignore
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import java.time.*
import java.util.*
import kotlin.test.assertTrue

class DatabobTest {
    val default = Databob()

    @Test
    fun support_primitives() {

        assertTrue(default.mk(Boolean::class) is Boolean)
        assertTrue(default.mk(Byte::class) is Byte)
        assertTrue(default.mk(String::class) is String)
        assertTrue(default.mk(Char::class) is Char)

        assertTrue(default.mk(Int::class) is Int)
        assertTrue(default.mk(Short::class) is Short)
        assertTrue(default.mk(Long::class) is Long)
        assertTrue(default.mk(BigInteger::class) is BigInteger)

        assertTrue(default.mk(Double::class) is Double)
        assertTrue(default.mk(Float::class) is Float)
        assertTrue(default.mk(BigDecimal::class) is BigDecimal)

        assertTrue(default.mk(Exception::class) is Exception)
        assertTrue(default.mk(RuntimeException::class) is RuntimeException)
    }

    @Test
    fun support_date_times() {

        assertTrue(default.mk(ZoneId::class) is ZoneId)
        assertTrue(default.mk(Date::class) is Date)
        assertTrue(default.mk(LocalTime::class) is LocalTime)
        assertTrue(default.mk(LocalDate::class) is LocalDate)

        assertTrue(default.mk(LocalDateTime::class) is LocalDateTime)
        assertTrue(default.mk(ZonedDateTime::class) is ZonedDateTime)
        assertTrue(default.mk(java.sql.Date::class) is java.sql.Date)
        assertTrue(default.mk(Timestamp::class) is Timestamp)

        assertTrue(default.mk(Duration::class) is Duration)
        assertTrue(default.mk(Period::class) is Period)
    }

    @Test
    @Ignore
    fun support_common_collections() {
        val a: List<String> = listOf("", "")
        assertTrue(default.mk(a.javaClass) is List)
        assertTrue(default.mk(Map::class) is Map)
        assertTrue(default.mk(Set::class) is Set)
    }

    @Test
    fun support_data_classses() {
        val mk = default.mk(Sue::class)

        assertTrue(mk is Sue)
        assertTrue(mk.others is List)
        assertTrue(mk.others[0] is Rita)
        assertTrue(mk.others[0].v is Bob)
        assertTrue(mk.others[0].v.s is String)
        assertTrue(mk.others[0].v.num is Int)
    }
}
