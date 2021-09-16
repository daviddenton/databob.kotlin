package io.github.databob.unit

import java.util.Vector
import java.util.stream.Stream

data class Container(val v: IntAndString)

data class IntAndString(val s: String, val num: Int)

data class InterfaceContainer(val contents: AnInterface)

data class ListContainer(val contents: List<Container>)

data class CollectionContainer(val contents: Collection<Container>)

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

class AnInterfaceImpl : AnInterface

enum class AnEnum {
    NORTH, SOUTH, WEST, EAST
}