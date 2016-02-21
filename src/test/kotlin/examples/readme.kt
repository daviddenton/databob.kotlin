package examples

import io.github.databob.Databob
import io.github.databob.Generators
import org.funktionale.option.Option
import java.time.ZonedDateTime

data class ReadReceipt(val read: Boolean)

data class EmailAddress(val value: String)

data class Email(val from: EmailAddress, val to: List<EmailAddress>, val date: ZonedDateTime, val read: Boolean, val subject: String, val readReceipt: Option<ReadReceipt>)

data class Inbox(val address: EmailAddress, val emails: List<Email>)

class InboxAddressBuilder {
    private var address = EmailAddress("some@email.address.com")
    private var emails = listOf<Email>()

    fun withAddress(newAddress: EmailAddress): InboxAddressBuilder {
        address = newAddress
        return this
    }

    fun withEmails(newEmails: List<Email>): InboxAddressBuilder {
        emails = newEmails
        return this
    }

    fun build() = Inbox(address, emails)
}

val a = Databob().mk(Email::class)

val b = Databob().mk(Email::class).copy(from = EmailAddress("my@real.email.com"))

val override = Generators.ofType { databob -> EmailAddress(databob.mk(String::class) + "@" + databob.mk(String::class) + ".com") }

val c = Databob(override).mk(Email::class)


