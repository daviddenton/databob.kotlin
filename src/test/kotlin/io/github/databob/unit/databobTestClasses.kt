package io.github.databob.unit

import org.funktionale.either.Either
import org.funktionale.option.Option
import java.util.*
import java.util.stream.Stream
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

data class FunktionaleOption(val v: Option<IntAndString>)

data class FunktionaleEither(val v: Either<IntAndString, Container>)

data class Container(val v: IntAndString)

data class IntAndString(val s: String, val num: Int)

data class InterfaceContainer(val contents: AnInterface)

data class ListContainer(val contents: List<Container>)

data class StringListContainer(val contents: List<String>)

data class SetContainer(val contents: Set<String>)

data class VectorContainer(val contents: Vector<String>)

data class StreamContainer(val contents: Stream<String>)

data class MapContainer(val contents: Map<String, Container>)

data class NullableContainer(val s: IntAndString?)

data class NullablePrimitiveContainer(val s: String?)

data class NullableListContainer(val s: List<String>?)

data class NullableInterfaceContainer(val s: AnInterface?)

data class NullableEnumContainer(val s: AnEnum?)

interface AnInterface

class AnInterfaceImpl : AnInterface {}

enum class AnEnum {
    NORTH, SOUTH, WEST, EAST
}