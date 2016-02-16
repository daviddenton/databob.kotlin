import io.github.databob.Databob

data class Bob(val v: String, val b: Rita)
data class Rita(val v: Mark)
data class Mark(val v: Double)
data class Bill(val v: List<Float>)
data class Sue(val v: Int)

fun main(args: Array<String>) {
    println(Databob.mk(Mark::class))
    println(Databob.mk(Rita::class))
    println(Databob.mk(Bob::class))
    println(Databob.mk(Bill::class))
}