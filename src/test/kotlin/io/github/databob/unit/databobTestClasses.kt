package io.github.databob.unit

import org.funktionale.option.Option

data class Funktionale(val v: Option<String>)

data class Container(val v: IntAndString)

data class IntAndString(val s: String, val num: Int)

data class ListContainer(val contents: List<Container>)

data class SetContainer(val contents: Set<String>)

data class MapContainer(val contents: Map<String, Container>)

data class NullableContainer(val s: IntAndString?)

data class NullablePrimitiveContainer(val s: String?)

data class NullableListContainer(val s: List<String>?)
