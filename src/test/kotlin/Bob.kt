import io.github.databob.Databob

data class Bob(val v: String, val b: Rita)
data class Rita(val v: List<String>)
data class Mark(val v: Set<Double>)
data class Bill(val v: Map<String, Float>)

fun main(args: Array<String>) {
    println(Databob().mk(Bob::class))
    println(Databob().mk(Mark::class))
    println(Databob().mk(Rita::class))
    println(Databob().mk(Bill::class))
}