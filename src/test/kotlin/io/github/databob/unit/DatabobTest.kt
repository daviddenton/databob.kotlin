package io.github.databob.unit

import io.github.databob.CoinToss
import io.github.databob.Databob
import io.github.databob.Generators
import org.funktionale.either.Either
import org.funktionale.option.Option
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import java.time.*
import java.util.*
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DatabobTest {

    private val interfaceGenerator = Generators.ofType<AnInterface> { -> AnInterfaceImpl() }

    @Test
    fun support_primitives() {
        assertTrue(Databob().mk<Boolean>() is Boolean)
        assertTrue(Databob().mk<Byte>() is Byte)
        assertTrue(Databob().mk<String>() is String)
        assertTrue(Databob().mk<Char>() is Char)

        assertTrue(Databob().mk<Int>() is Int)
        assertTrue(Databob().mk<Short>() is Short)
        assertTrue(Databob().mk<Long>() is Long)
        assertTrue(Databob().mk<BigInteger>() is BigInteger)

        assertTrue(Databob().mk<Double>() is Double)
        assertTrue(Databob().mk<Float>() is Float)
        assertTrue(Databob().mk<BigDecimal>() is BigDecimal)

        assertTrue(Databob().mk<Exception>() is Exception)
        assertTrue(Databob().mk<RuntimeException>() is RuntimeException)
    }

    @Test
    fun support_date_times() {

        assertTrue(Databob().mk<ZoneId>() is ZoneId)
        assertTrue(Databob().mk<Date>() is Date)
        assertTrue(Databob().mk<LocalTime>() is LocalTime)
        assertTrue(Databob().mk<LocalDate>() is LocalDate)

        assertTrue(Databob().mk<LocalDateTime>() is LocalDateTime)
        assertTrue(Databob().mk<ZonedDateTime>() is ZonedDateTime)
        assertTrue(Databob().mk<java.sql.Date>() is java.sql.Date)
        assertTrue(Databob().mk<Timestamp>() is Timestamp)

        assertTrue(Databob().mk<Duration>() is Duration)
        assertTrue(Databob().mk<Period>() is Period)
    }

    @Test
    fun support_list() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<ListContainer>()
        assertTrue(mk is ListContainer)
        assertTrue(mk.contents is List)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents[0] is Container)
    }

    @Test
    fun support_list_of_strings() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<StringListContainer>()
        assertTrue(mk is StringListContainer)
        assertTrue(mk.contents is List)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_stream() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<StreamContainer>()
        assertTrue(mk is StreamContainer)
        assertTrue(mk.contents is Stream)
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_vector() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<VectorContainer>()
        assertTrue(mk is VectorContainer)
        assertTrue(mk.contents is Vector)
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_set() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<SetContainer>()
        assertTrue(mk is SetContainer)
        assertTrue(mk.contents is Set)
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_map() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<MapContainer>()
        assertTrue(mk is MapContainer)
        assertTrue(mk.contents is Map)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents.entries.iterator().next().key is String)
        assertTrue(mk.contents.entries.iterator().next().value is Container)
    }

    @Test
    fun support_interface() {
        val mk = Databob(interfaceGenerator).mk<AnInterface>()
        assertTrue(mk is AnInterface)
    }

    @Test
    fun support_enum() {
        val mk = Databob().mk<AnEnum>()
        assertTrue(mk is AnEnum)
    }

    @Test
    fun support_nested_interfaces() {
        val mk = Databob(interfaceGenerator).mk<InterfaceContainer>()

        assertTrue(mk is InterfaceContainer)
        assertTrue(mk.contents is AnInterface)
    }

    @Test
    fun support_nested_data_classses() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<Container>()

        assertTrue(mk.v is IntAndString)
        assertTrue(mk.v.s is String)
        assertTrue(mk.v.num is Int)
    }

    @Test
    fun support_nullable_lists() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<NullableListContainer>()

        assertTrue(mk is NullableListContainer)
        assertTrue(mk.s is List<String>)
    }

    @Test
    fun support_nullable_containers() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<NullableContainer>()
        assertTrue(mk is NullableContainer)
        assertTrue(mk.s is IntAndString)
    }

    @Test
    fun support_nullable_primitives() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<NullablePrimitiveContainer>()

        assertTrue(mk is NullablePrimitiveContainer)
        assertTrue(mk.s is String)
    }

    @Test
    fun support_nullable_interfaces() {
        val mk = Databob(CoinToss.generators.alwaysHeads, interfaceGenerator).mk<NullableInterfaceContainer>()

        assertTrue(mk is NullableInterfaceContainer)
        assertTrue(mk.s is AnInterface)
    }

    @Test
    fun support_nullable_enums() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<NullableEnumContainer>()

        assertTrue(mk is NullableEnumContainer)
        assertTrue(mk.s is AnEnum)
    }

    @Test
    fun support_funktionale_option_happy() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<FunktionaleOption>()
        assertTrue(mk is FunktionaleOption)
        assertTrue(mk.v is Option<IntAndString>)
        assertTrue(mk.v.get().s is String)
    }

    @Test
    fun support_funktionale_option_sad() {
        val mk = Databob(CoinToss.generators.alwaysTails).mk<FunktionaleOption>()
        assertTrue(mk is FunktionaleOption)
        assertTrue(mk.v is Option<IntAndString>)
        assertEquals(mk.v.isEmpty(), true)
    }

    @Test
    fun support_funktionale_either_happy() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk<FunktionaleEither>()
        assertTrue(mk is FunktionaleEither)
        assertTrue(mk.v is Either.Right<IntAndString, Container>)
        assertTrue(mk.v.right().get() is Container)
        assertTrue(mk.v.right().get().v is IntAndString)
        assertTrue(mk.v.right().get().v.num is Int)
        assertTrue(mk.v.right().get().v.s is String)
    }

    @Test
    fun support_funktionale_either_sad() {
        val mk = Databob(CoinToss.generators.alwaysTails).mk<FunktionaleEither>()
        assertTrue(mk is FunktionaleEither)
        assertTrue(mk.v is Either.Left<IntAndString, Container>)
        assertTrue(mk.v.left().get() is IntAndString)
        assertTrue(mk.v.left().get().num is Int)
        assertTrue(mk.v.left().get().s is String)
    }
}
