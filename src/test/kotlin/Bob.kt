import io.github.databob.Databob
import java.math.BigDecimal
import java.math.BigInteger

data class Bob(val v: String, val b: Rita)
data class Rita(val v: List<String>)
data class Mark(val v: Set<Double>)
data class Bill(val v: Map<String, Float>)

fun main(args: Array<String>) {
    println(Databob().mk(BigDecimal::class))
    println(Databob().mk(BigInteger::class))
    println(Databob().mk(Bob::class))
    println(Databob().mk(Mark::class))
    println(Databob().mk(Rita::class))
    println(Databob().mk(Bill::class))
    println(Databob().mk(RuntimeException::class))
}