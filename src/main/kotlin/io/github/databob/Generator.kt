package io.github.databob

import kotlin.reflect.KType

interface Generator {
    fun mk(type: KType, databob: Databob): Any?
}

