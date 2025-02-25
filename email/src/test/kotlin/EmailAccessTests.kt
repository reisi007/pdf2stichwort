package pictures.reisinger.pdfextractor.email

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class EmailAccessTests {

    @Test
    fun `find E-Mails and print length if found`() {
        checkForMails(listOf("reisi007@gmail.com"), keepOpen = false) { (_, inputStream) ->
            println("Length ${inputStream.readAllBytes().size / 1024.0} kb")
            false
        }
    }
}