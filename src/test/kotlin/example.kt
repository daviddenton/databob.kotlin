import io.github.databob.Databob

data class ChildContainer(val myString: String, val myNum: List<Int>)

data class ParentContainer(val child: ChildContainer)

data class GrandparentContainer(val children: List<ParentContainer>)

data class MyPair<A, B>(val a: A, val b: B)

fun main(args: Array<String>) {

    val databob = Databob()

    println(databob.mk<MyPair<String, Int>>())

    println(databob.mk<ChildContainer>())

    println(
            databob.mk<ChildContainer>()
                    .copy(myString = "overwritten")
    )
}
