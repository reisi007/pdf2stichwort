package pictures.reisinger.pdfextractor.email

import org.junit.jupiter.api.Test

class EmailAccessTests {

    @Test
    fun `find E-Mails from presse@lask at`() {
        checkForMails(keepOpen = false) { (_, inputStream) ->
            println("Length ${inputStream.readAllBytes().size / 1024.0} kb")
            false
        }
    }
}