package io.github.databob.unit

import io.github.databob.CoinToss
import io.github.databob.Databob
import io.github.databob.Generators
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File
import java.io.PrintStream
import java.io.Reader
import java.io.Writer
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URL
import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.Period
import java.time.Year
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import java.util.UUID
import kotlin.test.assertTrue

class DatabobTest {

    private inline fun <reified T : Any> assertSupports() = assertTrue(Databob().mk<T>() is T)

    private val interfaceGenerator = Generators.ofType<AnInterface> { -> AnInterfaceImpl() }

    data class Bob(val date: LocalDateTime)
    @Test
    fun `bob`() {
        Databob().mk<Bob>()
    }

    @Test
    fun support_primitives() {
        assertSupports<Boolean>()
        assertSupports<Byte>()
        assertSupports<String>()
        assertSupports<Char>()

        assertSupports<Int>()
        assertSupports<Short>()
        assertSupports<Long>()
        assertSupports<BigInteger>()

        assertSupports<Double>()
        assertSupports<Float>()
        assertSupports<BigDecimal>()

        assertSupports<Exception>()
        assertSupports<RuntimeException>()
    }

    @Test
    fun support_jdkCommons() {
        assertSupports<URL>()
        assertSupports<Exception>()
        assertSupports<RuntimeException>()
        assertSupports<UUID>()
        assertSupports<File>()
        assertSupports<Reader>()
        assertSupports<Writer>()
        assertSupports<PrintStream>()
    }

    @Test
    fun support_date_times() {
        assertSupports<Year>()
        assertSupports<Month>()
        assertSupports<DayOfWeek>()

        assertSupports<ZoneId>()
        assertSupports<Date>()
        assertSupports<LocalTime>()
        assertSupports<LocalDate>()

        assertSupports<LocalDateTime>()
        assertSupports<ZonedDateTime>()
        assertSupports<java.sql.Date>()
        assertSupports<Timestamp>()

        assertSupports<Duration>()
        assertSupports<Period>()
    }

    @Test
    fun support_list() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<ListContainer>()
        assertTrue(mk.contents.isNotEmpty())
    }

    @Test
    fun support_list_of_strings() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<StringListContainer>()
        assertTrue(mk.contents.isNotEmpty())
    }

    @Test
    fun support_stream() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<StreamContainer>()
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_vector() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<VectorContainer>()
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_set() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<SetContainer>()
        assertNotNull(mk.contents.iterator().next())
    }

    @Test
    fun support_map() {
        val mk: MapContainer = Databob(CoinToss.generators.alwaysHeads).mk()
        assertTrue(mk.contents.isNotEmpty())
    }

    @Test
    fun support_interface() {
        val mk: AnInterface = Databob(interfaceGenerator).mk()
        assertNotNull(mk)
    }

    @Test
    fun support_enum() {
        val mk: AnEnum = Databob().mk()
        assertNotNull(mk)
    }

    @Test
    fun support_nested_interfaces() {
        val mk: InterfaceContainer = Databob(interfaceGenerator).mk()
        assertNotNull(mk)
    }

    @Test
    fun support_nested_data_classses() {
        val mk: Container = Databob(CoinToss.generators.alwaysHeads).mk()
        assertNotNull(mk)
    }

    @Test
    fun support_nullable_lists() {
        val mk: NullableListContainer = Databob(CoinToss.generators.alwaysHeads).mk()
        assertTrue(mk.s is List<String>)
    }

    @Test
    fun support_nullable_containers() {
        val mk: NullableContainer = Databob(CoinToss.generators.alwaysHeads).mk()
        assertTrue(mk.s is IntAndString)
    }

    @Test
    fun support_nullable_primitives() {
        val mk: NullablePrimitiveContainer = Databob(CoinToss.generators.alwaysHeads).mk()

        assertTrue(mk.s is String)
    }

    @Test
    fun support_nullable_interfaces() {
        val mk: NullableInterfaceContainer = Databob(CoinToss.generators.alwaysHeads, interfaceGenerator).mk()
        assertTrue(mk.s is AnInterface)
    }

    @Test
    fun support_nullable_enums() {
        val mk: NullableEnumContainer = Databob(CoinToss.generators.alwaysHeads).mk()

        assertTrue(mk.s is AnEnum)
    }
}
