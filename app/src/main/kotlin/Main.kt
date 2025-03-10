package pictures.reisinger.pdfextractor.app

import pictures.reisinger.pdfextractor.app.serialisation.Address
import pictures.reisinger.pdfextractor.email.*
import pictures.reisinger.pdfextractor.email.EmailUtils.checkForMails
import pictures.reisinger.pdfextractor.email.EmailUtils.sendMail
import pictures.reisinger.pdfextractor.extractor.extractBundesligaPdf
import pictures.reisinger.pdfextractor.extractor.toPhotomachanics
import pictures.reisinger.pdfextractor.logging.LOGGER

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            performAction()
        } catch (e: Exception) {
            LOGGER.error(e, "An unhandled exception occured")
        }

    }

    private fun performAction() {
        LOGGER.info("Watching for emails")
        val address = Address.value
        checkForMails(address.keys, keepOpen = true) { (mail, inputStream) ->
            val emailInfo = when (mail) {
                "presse@lask.at" -> {
                    val info = extractBundesligaPdf(inputStream)
                    val title = "${info.home.name} vs. ${info.away.name} am ${info.general.date}"
                    title to buildString {
                        info.toPhotomachanics(this)
                    }

                }

                else -> {
                    LOGGER.debug("Unknown mail, ignoring $mail")
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

        LOGGER.info("Completed - shutting down")
    }
}