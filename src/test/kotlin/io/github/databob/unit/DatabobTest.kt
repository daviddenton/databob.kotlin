package io.github.databob.unit

import io.github.databob.Databob
import org.funktionale.option.Option
import org.junit.Ignore
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import java.time.*
import java.util.*
import kotlin.test.assertTrue

class DatabobTest {

    @Test
    fun support_primitives() {
        assertTrue(Databob().mk(Boolean::class) is Boolean)
        assertTrue(Databob().mk(Byte::class) is Byte)
        assertTrue(Databob().mk(String::class) is String)
        assertTrue(Databob().mk(Char::class) is Char)

        assertTrue(Databob().mk(Int::class) is Int)
        assertTrue(Databob().mk(Short::class) is Short)
        assertTrue(Databob().mk(Long::class) is Long)
        assertTrue(Databob().mk(BigInteger::class) is BigInteger)

        assertTrue(Databob().mk(Double::class) is Double)
        assertTrue(Databob().mk(Float::class) is Float)
        assertTrue(Databob().mk(BigDecimal::class) is BigDecimal)

        assertTrue(Databob().mk(Exception::class) is Exception)
        assertTrue(Databob().mk(RuntimeException::class) is RuntimeException)
    }

    @Test
    fun support_date_times() {

        assertTrue(Databob().mk(ZoneId::class) is ZoneId)
        assertTrue(Databob().mk(Date::class) is Date)
        assertTrue(Databob().mk(LocalTime::class) is LocalTime)
        assertTrue(Databob().mk(LocalDate::class) is LocalDate)

        assertTrue(Databob().mk(LocalDateTime::class) is LocalDateTime)
        assertTrue(Databob().mk(ZonedDateTime::class) is ZonedDateTime)
        assertTrue(Databob().mk(java.sql.Date::class) is java.sql.Date)
        assertTrue(Databob().mk(Timestamp::class) is Timestamp)

        assertTrue(Databob().mk(Duration::class) is Duration)
        assertTrue(Databob().mk(Period::class) is Period)
    }

    @Test
    @Ignore
    fun support_common_collections() {
        val a: List<String> = listOf("", "")
        assertTrue(Databob().mk(a.javaClass) is List)
        assertTrue(Databob().mk(Map::class) is Map)
        assertTrue(Databob().mk(Set::class) is Set)
    }

    @Test
    fun support_data_classses() {
        val mk = Databob().mk(ListContainer::class)

        assertTrue(mk is ListContainer)
        assertTrue(mk.others is List)
        assertTrue(mk.others[0] is Container)
        assertTrue(mk.others[0].v is IntAndString)
        assertTrue(mk.others[0].v.s is String)
        assertTrue(mk.others[0].v.num is Int)
    }

    @Test
    fun support_nullable() {
        val mk = Databob().mk(NullableContainer::class)

        assertTrue(mk is NullableContainer)
        assertTrue(mk.s == null)
    }

    @Test
    fun support_funktionale() {
        val mk = Databob().mk(Funktionale::class)
        assertTrue(mk is Funktionale)
        assertTrue(mk.v is Option<String>)
    }
}
