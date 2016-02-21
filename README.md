# databob.kotlin

[![coverage](https://coveralls.io/repos/daviddenton/databob.scala/badge.svg?branch=master)](https://coveralls.io/github/daviddenton/databob.kotlin?branch=master)
[![kotlin](https://img.shields.io/badge/kotlin-1.0.0-blue.svg)](http://kotlinlang.org)
[![build status](https://travis-ci.org/daviddenton/databob.kotlin.svg?branch=master)](https://travis-ci.org/daviddenton/databob.kotlin)
[![bintray version](https://api.bintray.com/packages/daviddenton/maven/databob.kotlin/images/download.svg)](https://bintray.com/daviddenton/maven/databob.kotlin/_latestVersion)

##### (Also available in [Scala](https://github.com/daviddenton/databob.scala) and [JavaScript](https://github.com/daviddenton/databob) flavours)

Databob provides a way to generate completely randomised objects with zero-boilerplate builder code.

###Why?
The problem of generating dummy test instances for our classes has been around for a long time. Given the following data classes...
```
data class ReadReceipt(val read: Boolean)

data class EmailAddress(val value: String)

data class Email(val from: EmailAddress, val to: List<EmailAddress>, val date: ZonedDateTime, val read: Boolean, val subject: String, val readReceipt: Option<ReadReceipt>)

data class Inbox(val address: EmailAddress, val emails: List<Email>)
```

We could start to write objects using the [TestBuilder](http://www.javacodegeeks.com/2013/06/builder-pattern-good-for-code-great-for-tests.html) pattern using the traditional method:
```
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
```

Kotlin makes this easier for us somewhat by leveraging data class ```copy()```. This also allows us to be compiler safe, as removing 
a field will break the equivalent ```with``` method:
```
class InboxBuilder {
  private var inbox = Inbox(EmailAddress("some@email.address.com"), List[Email]())
  
  def withAddress(newAddress: EmailAddress) = {
    inbox = inbox.copy(address = newAddress)
    this
  }

  def withEmails(newEmails: List[Email]) = {
    inbox = inbox.copy(emails = newEmails)
    this
  }

  def build = inbox
}
```

Taking this even further with data class copy(), we can reduce this to:
```
class BetterInboxBuilder private constructor(private val inbox: Inbox) {

    constructor() : this(Inbox(EmailAddress("some@email.address.com"), listOf<Email>()))

    fun withAddress(newAddress: EmailAddress) = BetterInboxBuilder(inbox.copy(address = newAddress))

    fun withEmails(newEmails: List<Email>) = BetterInboxBuilder(inbox.copy(emails = newEmails))

    fun build() = inbox
}
```

So, better - but it still seems pretty tedious to maintain. Additionally, we don't really want tests to rely unknowingly on 
bits of default test data for multiple tests which will lead to an explosion of [ObjectMother](http://martinfowler.com/bliki/ObjectMother.html)-type methods with small variations 
to suit particular tests.

What we really want are completely randomised instances, with important overrides set-up only for tests that rely on them. No sharing of test data across tests. Ever.

Enter Databob. For a completely randomised instance, including non-primitive sub-tree objects:
```
Databob().mk(Email::class)
```

That's it. Want to override particular value(s)?
```
Databob().mk(Email::class).copy(from = EmailAddress("my@real.email.com"))
```

Or add your own rule for generating values?
```
val override = Generators.ofType { databob -> EmailAddress(databob.mk(String::class) + "@" + databob.mk(String::class) + ".com") }

Databob(override).mk(Email::class)
```

### Out-of-the-box features:
- Nested object-trees (ie. non-primitive fields)
- Kotlin/Java primitives/Enums
- Kotlin and Java Collection classes
- Java8 date-time values
- Bindings for Funktionale monadic types (Option/Either)
- Simple overriding mechanism for your own-types and custom generation rules

### See it in action
See the [example code](https://github.com/daviddenton/databob.kotlin/tree/master/src/test/kotlin/examples).

### Get it
```XML
<dependency>
  <groupId>io.github.daviddenton</groupId>
  <artifactId>databob.kotlin</artifactId>
  <version>X.X.X</version>
</dependency>
```

For extension binding support (such as Funktionale, you'll need to also add relevant dependencies)

###Contribute
PRs gratefully accepted for other common types that might be useful.
