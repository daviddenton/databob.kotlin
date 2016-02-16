package io.github.databob

import kotlin.reflect.KClass

class DataBobInstance {
    private val lookup = CompositeGenerator(PrimitiveGenerator()).with(CollectionGenerator())

    fun <R : Any> mk(c: KClass<R>): R {
        return mk(c.java)
    }

    fun <R : Any> mk(c: Class<R>): R {
        val constructor = c.kotlin.constructors.iterator().next()
        val map = constructor.parameters
                .map { k -> lookup.get(k.type) ?: mk(Class.forName(k.type.toString())) }
//                    val message: ParameterizedType = k.type.javaType as ParameterizedType
//                    println(    message.actualTypeArguments.map { t -> t })
//                    val message1: Type? = message.actualTypeArguments.iterator().next()
//                    println(message1?.javaClass)
        return constructor.call(*map.toTypedArray())
    }
}
