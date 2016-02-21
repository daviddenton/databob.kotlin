package io.github.databob

import java.lang.reflect.Type

interface Generator {
    fun mk(type: Type, databob: Databob): Any?
}