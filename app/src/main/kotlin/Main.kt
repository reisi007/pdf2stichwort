package pictures.reisinger.pdfextractor.app

import pictures.reisinger.pdfextractor.app.serialisation.Address
import pictures.reisinger.pdfextractor.email.*
import pictures.reisinger.pdfextractor.extractor.extractBundesligaPdf
import pictures.reisinger.pdfextractor.extractor.toPhotomachanics

fun main() {
    println("Watching for emails")
    checkForMails(keepOpen = true) { (mail, inputStream) ->
        val address = Address.value
        val emailInfo = when (mail) {
            "presse@lask.at" -> {
                val info = extractBundesligaPdf(inputStream)
                val title = "${info.home.name} vs. ${info.away.name} am ${info.general.date}"
                title to buildString {
                    info.toPhotomachanics(this)
                }

            }

            else -> {
                println("Unknown mail, ignoring $mail")
                null
            }

        }

        val recipients = address[mail]
        if (recipients.isNullOrEmpty() || emailInfo == null) {
            return@checkForMails false
        }

        val (title, attachmentContent) = emailInfo
        sendMail {
            subject = "Keywords für $title"
            setFrom("florian@reisinger.pictures")
            setTo("florian@reisinger.pictures")
            setBcc(recipients - "florian@reisinger.pictures\"")

            setMultipartContent {
                addTextPart("Anbei die Stichwörter für die aktuelle Partie")
                addAttachment("keywords.tsv", attachmentContent)
            }
        }

        false
    }

    println("Completed - shutting down")
}