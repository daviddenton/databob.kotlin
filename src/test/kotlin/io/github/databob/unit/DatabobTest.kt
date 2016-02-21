package io.github.databob.unit

import io.github.databob.Databob
import io.github.databob.generators.CoinToss
import org.funktionale.option.Option
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
    fun support_list() {
        val mk = Databob().mk(ListContainer::class)
        assertTrue(mk is ListContainer)
        assertTrue(mk.contents is List)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents[0] is Container)
    }

    @Test
    fun support_set() {
        val mk = Databob().mk(SetContainer::class)
        assertTrue(mk is SetContainer)
        assertTrue(mk.contents is Set)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_map() {
        val mk = Databob().mk(MapContainer::class)
        assertTrue(mk is MapContainer)
        assertTrue(mk.contents is Map)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents.entries.iterator().next().key is String)
        assertTrue(mk.contents.entries.iterator().next().value is Container)
    }

    @Test
    fun support_nested_data_classses() {
        val mk = Databob().mk(Container::class)

        assertTrue(mk.v is IntAndString)
        assertTrue(mk.v.s is String)
        assertTrue(mk.v.num is Int)
    }

    @Test
    fun support_nullable_lists() {
        val mk = Databob(CoinToss.instances.alwaysHeads).mk(NullableListContainer::class)

        assertTrue(mk is NullableListContainer)
        assertTrue(mk.s is List<String>)
    }

    @Test
    fun support_nullable_containers() {
        val mk = Databob(CoinToss.instances.alwaysHeads).mk(NullableContainer::class)
        assertTrue(mk is NullableContainer)
        assertTrue(mk.s is IntAndString)
    }

    @Test
    fun support_nullable_primitives() {
        val mk = Databob(CoinToss.instances.alwaysHeads).mk(NullablePrimitiveContainer::class)

        assertTrue(mk is NullablePrimitiveContainer)
        assertTrue(mk.s is String)
    }

    @Test
    fun support_funktionale() {
        val mk = Databob().mk(Funktionale::class)
        assertTrue(mk is Funktionale)
        assertTrue(mk.v is Option<String>)
    }
}
