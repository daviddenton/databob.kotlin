package io.github.databob.unit

import org.funktionale.option.Option
import kotlin.reflect.defaultType
import kotlin.reflect.jvm.javaType

data class Funktionale(val v: Option<String>)

data class Container(val v: IntAndString)

data class IntAndString(val s: String, val num: Int)

data class ListContainer(val contents: List<Container>)

data class SetContainer(val contents: Set<String>)

data class MapContainer(val contents: Map<String, Container>)

data class NullableContainer(val s: IntAndString?)

data class NullablePrimitiveContainer(val s: String?)

data class NullableListContainer(val s: List<String>?)

interface AnInterface

class AnInterfaceImpl : AnInterface {}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun main(args: Array<String>) {
    Direction::class.java.isEnum
    println(Direction::class.defaultType.javaType)
}