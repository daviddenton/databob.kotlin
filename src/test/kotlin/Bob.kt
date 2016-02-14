import io.github.databob.Databob

data class Bob(val v: String, val b: Rita)
data class Rita(val v: Mark)
data class Mark(val v: Double)
data class Bill(val v: Float)
data class Charlie(val v: Float)
data class Sue(val v: Int)


fun main(args: Array<String>) {
    val a = Databob.mk(Bob::class)
    println(a.copy(b = Databob.mk(Rita::class.java)))
}