package pictures.reisinger.pdfextractor.email

import java.nio.charset.StandardCharsets

object AllowedEmails {

    val emails by lazy {
        AllowedEmails::class.java.classLoader.getResourceAsStream("emails.txt")
            ?.reader(StandardCharsets.UTF_8)
            ?.useLines {
                it.toSet()
            } ?: throw IllegalStateException("Cannot find list of emails")
    }
}