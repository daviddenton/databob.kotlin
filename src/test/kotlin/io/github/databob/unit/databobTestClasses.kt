package io.github.databob.unit

import org.funktionale.option.Option

data class Funktionale(val v: Option<String>)

data class Container(val v: IntAndString)

data class IntAndString(val s: String, val num : Int)

data class ListContainer(val others: List<Container>)

data class NullableContainer(val s: String?)
