package pictures.reisinger.pdfextractor.email

import jakarta.activation.DataHandler
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import jakarta.mail.util.ByteArrayDataSource
import java.net.URLConnection

@MimeMessageExtensions
fun MimeMessage.setTo(to: String) {
    addRecipients(Message.RecipientType.TO, to)
}

@MimeMessageExtensions
fun MimeMessage.setCc(cc: String) {
    addRecipients(Message.RecipientType.CC, cc)
}

@MimeMessageExtensions
fun MimeMessage.setBcc(bcc: List<String>) {
    if (bcc.isNotEmpty())
        addRecipients(Message.RecipientType.BCC, bcc.map { InternetAddress(it) }.toTypedArray())
}

@MimeMessageExtensions
fun MimeMessage.setMultipartContent(action: MimeMultipart.() -> Unit) {
    setContent(MimeMultipart().apply(action))
}

@MimeMessageExtensions
fun MimeMultipart.addTextPart(text: String) = addBodyPart(MimeBodyPart().apply { setText(text) })

@MimeMessageExtensions
fun MimeMultipart.addAttachment(filename: String, content: String) = addBodyPart(MimeBodyPart().apply {
    fileName = filename
    dataHandler = DataHandler(
        ByteArrayDataSource(content, URLConnection.guessContentTypeFromName(filename))
    )
})

@DslMarker
annotation class MimeMessageExtensions