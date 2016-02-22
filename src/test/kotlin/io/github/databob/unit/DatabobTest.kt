package io.github.databob.unit

import io.github.databob.Databob
import io.github.databob.Generators
import io.github.databob.generators.CoinToss
import org.funktionale.either.Either
import org.funktionale.option.Option
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import java.time.*
import java.util.*
import java.util.stream.Stream
import kotlin.test.assertTrue

class DatabobTest {

    private val interfaceGenerator = Generators.ofType<AnInterface> { -> AnInterfaceImpl() }

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
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(ListContainer::class)
        assertTrue(mk is ListContainer)
        assertTrue(mk.contents is List)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents[0] is Container)
    }

    @Test
    fun support_list_of_strings() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(StringListContainer::class)
        assertTrue(mk is StringListContainer)
        assertTrue(mk.contents is List)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_stream() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(StreamContainer::class)
        assertTrue(mk is StreamContainer)
        assertTrue(mk.contents is Stream)
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_vector() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(VectorContainer::class)
        assertTrue(mk is VectorContainer)
        assertTrue(mk.contents is Vector)
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_set() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(SetContainer::class)
        assertTrue(mk is SetContainer)
        assertTrue(mk.contents is Set)
        assertTrue(mk.contents.iterator().next() is String)
    }

    @Test
    fun support_map() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(MapContainer::class)
        assertTrue(mk is MapContainer)
        assertTrue(mk.contents is Map)
        assertTrue(mk.contents.isNotEmpty())
        assertTrue(mk.contents.entries.iterator().next().key is String)
        assertTrue(mk.contents.entries.iterator().next().value is Container)
    }

    @Test
    fun support_interface() {
        val mk = Databob(interfaceGenerator).mk(AnInterface::class)
        assertTrue(mk is AnInterface)
    }

    @Test
    fun support_enum() {
        val mk = Databob().mk(AnEnum::class)
        assertTrue(mk is AnEnum)
    }

    @Test
    fun support_nested_interfaces() {
        val mk = Databob(interfaceGenerator).mk(InterfaceContainer::class)

        assertTrue(mk is InterfaceContainer)
        assertTrue(mk.contents is AnInterface)
    }

    @Test
    fun support_nested_data_classses() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(Container::class)

        assertTrue(mk.v is IntAndString)
        assertTrue(mk.v.s is String)
        assertTrue(mk.v.num is Int)
    }

    @Test
    fun support_nullable_lists() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(NullableListContainer::class)

        assertTrue(mk is NullableListContainer)
        assertTrue(mk.s is List<String>)
    }

    @Test
    fun support_nullable_containers() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(NullableContainer::class)
        assertTrue(mk is NullableContainer)
        assertTrue(mk.s is IntAndString)
    }

    @Test
    fun support_nullable_primitives() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(NullablePrimitiveContainer::class)

        assertTrue(mk is NullablePrimitiveContainer)
        assertTrue(mk.s is String)
    }

    @Test
    fun support_nullable_interfaces() {
        val mk = Databob(CoinToss.generators.alwaysHeads, interfaceGenerator).mk(NullableInterfaceContainer::class)

        assertTrue(mk is NullableInterfaceContainer)
        assertTrue(mk.s is AnInterface)
    }

    @Test
    fun support_nullable_enums() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(NullableEnumContainer::class)

        assertTrue(mk is NullableEnumContainer)
        assertTrue(mk.s is AnEnum)
    }

    @Test
    fun support_funktionale_option() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(FunktionaleOption::class)
        assertTrue(mk is FunktionaleOption)
        assertTrue(mk.v is Option<IntAndString>)
        assertTrue(mk.v.get().s is String)
    }

    @Test
    fun support_funktionale_either() {
        val mk = Databob(CoinToss.generators.alwaysHeads).mk(FunktionaleEither::class)
        assertTrue(mk is FunktionaleEither)
        assertTrue(mk.v is Either.Right<IntAndString, Container>)
        assertTrue(mk.v.right().get() is Container)
        assertTrue(mk.v.right().get().v is IntAndString)
        assertTrue(mk.v.right().get().v.num is Int)
        assertTrue(mk.v.right().get().v.s is String)
    }
}
