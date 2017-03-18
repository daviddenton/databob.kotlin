package io.github.databob

import org.funktionale.either.Either
import org.funktionale.either.toLeft
import org.funktionale.either.toRight
import org.funktionale.option.Option
import org.funktionale.option.toOption
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.full.defaultType
import kotlin.reflect.jvm.javaType

class FunktionaleGenerator : io.github.databob.Generator {

    private val lookup by lazy {
        mapOf<Type, (Array<Type>, Databob) -> Any>(
                Option::class.defaultType.javaType to { t, d -> d.mk(Class.forName(t[0].typeName)).toOption().filter { t -> d.mk<CoinToss>().toss() } },
                Either::class.defaultType.javaType to { t, d ->
                    val pair = Pair(d.mk(Class.forName(t[0].typeName)), d.mk(Class.forName(t[1].typeName)))
                    if (d.mk<CoinToss>().toss()) pair.toRight() else pair.toLeft()
                }
        )
    }

    override fun mk(type: Type, databob: Databob): Any? = when (type) {
        is ParameterizedType -> {
            lookup[type.rawType]?.invoke(type.actualTypeArguments, databob)
        }
        else -> null
    }
}