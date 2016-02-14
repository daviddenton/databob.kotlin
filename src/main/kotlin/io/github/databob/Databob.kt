package io.github.databob

import kotlin.reflect.KClass

object Databob {
    private val default = DataBobInstance()

    fun <R : Any> mk(c: KClass<R>): R {
       return default.mk(c)
    }

    fun <R : Any> mk(c: Class<R>): R {
       return default.mk(c)
    }
}