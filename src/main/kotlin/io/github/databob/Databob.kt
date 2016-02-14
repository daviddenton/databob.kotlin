package io.github.databob

import kotlin.reflect.KClass

object Databob {
    fun <R : Any> mk(c: KClass<R>): R {
       return DataBobInstance().mk(c)
    }
}