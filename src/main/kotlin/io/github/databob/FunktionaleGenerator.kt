package io.github.databob

import org.funktionale.either.Either
import org.funktionale.either.toLeft
import org.funktionale.either.toRight
import org.funktionale.option.Option
import org.funktionale.option.toOption
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType
import kotlin.reflect.full.createType

class FunktionaleGenerator : io.github.databob.Generator {

    private val lookup by lazy {
        mapOf<KType, (Array<Type>, Databob) -> Any>(
            Option::class.createType() to { t, d -> d.mk(Class.forName(t[0].typeName).kotlin).toOption().filter { d.mk<CoinToss>().toss() } },
            Either::class.createType() to { t, d ->
                val pair = Pair(d.mk(Class.forName(t[0].typeName).kotlin), d.mk(Class.forName(t[1].typeName).kotlin))
                if (d.mk<CoinToss>().toss()) pair.toRight() else pair.toLeft()
            }
        )
    }

    override fun mk(type: KType, databob: Databob): Any? = when (type) {
        is ParameterizedType -> {
            lookup[type]?.invoke(type.actualTypeArguments, databob)
        }
        else -> null
    }
}