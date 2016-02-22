import io.github.databob.Databob

data class ChildContainer(val myString: String, val myNum: Int)

data class ParentContainer(val child: ChildContainer)

data class GrandparentContainer(val children: List<ParentContainer>)

fun main(args: Array<String>) {

    val databob = Databob()

    println(databob.mk<GrandparentContainer>())

    println(databob.mk<ChildContainer>())

    println(
            databob.mk<ChildContainer>()
                    .copy(myString = "overwritten")
    )
}
