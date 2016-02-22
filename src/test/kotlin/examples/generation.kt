package examples

import io.github.databob.Databob
import io.github.databob.Generators
import io.github.databob.CollectionSizeRange
import org.funktionale.option.Option
import java.time.ZonedDateTime


data class ReadReceipt(val readDate: ZonedDateTime)

data class EmailAddress(val value: String)

data class Email(val from: EmailAddress, val to: List<EmailAddress>, val date: ZonedDateTime, val read: Boolean, val subject: String, val readReceipt: Option<ReadReceipt>)

data class Inbox(val address: EmailAddress, val emails: List<Email>)

/**
 * This set of examples shows how you can use Databob to generate objects
 */
fun main(args: Array<String>) {
    fun completelyRandomObject() = Databob().mk<Email>()

    println(completelyRandomObject())

    fun mkObjectWithOverriddenField() = Databob().mk<Email>().copy(subject = "my stupid subject")

    println(mkObjectWithOverriddenField())

    fun objectWithCustomCollectionSizes() = Databob(CollectionSizeRange.generators.between(3, 5)).mk<Email>()

    println(objectWithCustomCollectionSizes())

    fun usingACustomGenerator(): Email {
        return Databob(
                Generators.ofType { databob -> EmailAddress(Databob().mk<String>() + "@" + Databob().mk<String>() + ".com") }
        ).mk<Email>()
    }

    println(usingACustomGenerator())
}

