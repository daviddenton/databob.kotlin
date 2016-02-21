package examples

import io.github.databob.Databob
import io.github.databob.Generators
import org.funktionale.option.Option
import java.time.ZonedDateTime

data class ReadReceipt(val read: Boolean)

data class EmailAddress(val value: String)

data class Email(val from: EmailAddress, val to: List<EmailAddress>, val date: ZonedDateTime, val read: Boolean, val subject: String, val readReceipt: Option<ReadReceipt>)

data class Inbox(val address: EmailAddress, val emails: List<Email>)

class InboxBuilder {
    private var address = EmailAddress("some@email.address.com")
    private var emails = listOf<Email>()

    fun withAddress(newAddress: EmailAddress): InboxBuilder {
        address = newAddress
        return this
    }

    fun withEmails(newEmails: List<Email>): InboxBuilder {
        emails = newEmails
        return this
    }

    fun build() = Inbox(address, emails)
}

class BetterInboxBuilder {
    private var inbox = Inbox(EmailAddress("some@email.address.com"), listOf<Email>())

    fun withAddress(newAddress: EmailAddress) : BetterInboxBuilder {
        inbox = inbox.copy(address = newAddress)
        return this
    }

    fun withEmails(newEmails: List<Email>): BetterInboxBuilder {
        inbox = inbox.copy(emails = newEmails)
        return this
    }

    fun build() = inbox
}


class EvenBetterInboxBuilder private constructor(private val inbox: Inbox) {

    constructor() : this(Inbox(EmailAddress("some@email.address.com"), listOf<Email>()))

    fun withAddress(newAddress: EmailAddress) = EvenBetterInboxBuilder(inbox.copy(address = newAddress))

    fun withEmails(newEmails: List<Email>) = EvenBetterInboxBuilder(inbox.copy(emails = newEmails))

    fun build() = inbox
}

val a = Databob().mk(Email::class)

val b = Databob().mk(Email::class).copy(from = EmailAddress("my@real.email.com"))

val override = Generators.ofType { databob -> EmailAddress(databob.mk(String::class) + "@" + databob.mk(String::class) + ".com") }

val c = Databob(override).mk(Email::class)


