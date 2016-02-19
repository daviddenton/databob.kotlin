import io.github.databob.Databob


data class ChildContainer(val myString: String, val myNum: Int)

data class ParentContainer(val child: ChildContainer)

data class GrandparentContainer(val children: List<ParentContainer>)

fun main(args: Array<String>) {

    val databob = Databob()

    println(databob.mk(GrandparentContainer::class))

    println(databob.mk(ChildContainer::class))

    println(
            databob.mk(ChildContainer::class)
                    .copy(myString = "overwritten")
    )
}
