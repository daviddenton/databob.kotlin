package io.github.databob

import kotlin.reflect.KType

interface Generator {
    fun get(type: KType): Any?
}