package pictures.reisinger.pdfextractor.email

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import pictures.reisinger.pdfextractor.email.EmailUtils.checkForMails
import pictures.reisinger.pdfextractor.logging.LOGGER

@Disabled
class EmailAccessTests {

    @Test
    fun `find E-Mails and print length if found`() {
        checkForMails(listOf("reisi007@gmail.com"), keepOpen = false) { (_, inputStream) ->
            LOGGER.trace("Length ${inputStream.readAllBytes().size / 1024.0} kb")
            false
        }
    }
}