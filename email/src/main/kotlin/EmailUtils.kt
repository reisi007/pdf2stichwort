package pictures.reisinger.pdfextractor.email

import jakarta.mail.*
import jakarta.mail.event.MessageCountEvent
import jakarta.mail.event.MessageCountListener
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.eclipse.angus.mail.imap.IMAPMessage
import java.io.InputStream
import java.util.*

fun sendMail(@MimeMessageExtensions action: MimeMessage.() -> Unit) {
    val host = System.getenv("EMAIL_HOST")
    val port = System.getenv("EMAIL_SMTP_PORT")
    val user = System.getenv("EMAIL_USER")
    val pwd = System.getenv("EMAIL_PASSWORD")

    val session = Session.getInstance(
        Properties().apply {
            put(MailConstants.SMTP_HOST, host)
            put(MailConstants.SMTP_PORT, port)
            put(MailConstants.SMTP_AUTH, "true")
            put(MailConstants.SMTP_SSL_ENABLE, "true")
        }
    )

    val message = MimeMessage(session).apply(action)
    println("Prepared message")
    Transport.send(message, user, pwd)
    println("Sent ${message.subject}")
}

fun checkForMails(keepOpen: Boolean = true, foundAction: (Pair<String, InputStream>) -> Boolean) {
    val host = System.getenv("EMAIL_HOST")
    val port = System.getenv("EMAIL_IMAP_PORT")
    val user = System.getenv("EMAIL_USER")
    val pwd = System.getenv("EMAIL_PASSWORD")

    val session = Session.getDefaultInstance(
        Properties().apply {
            put(MailConstants.IMAP_PROTOCOL, "imap");
            put(MailConstants.IMAP_HOST, host);
            put(MailConstants.IMAP_PORT, port);
            put(MailConstants.IMAP_SSL_ENABLE, "true");
        }
    )

    val store = session.getStore("imap")

    store.connect(user, pwd)

    store.getFolder("INBOX").use { inbox ->
        store.getFolder("INBOX.Trash").use { trash ->
            inbox.open(Folder.READ_WRITE)
            trash.open(Folder.READ_WRITE)
            val messages = inbox.messages
            handleMessage(messages, foundAction, inbox, trash)

            inbox.addMessageCountListener(object : MessageCountListener {
                override fun messagesAdded(e: MessageCountEvent) {
                    handleMessage(e.messages, foundAction, inbox, trash)
                }

                override fun messagesRemoved(e: MessageCountEvent) {
                    // no action
                }

            })

            while (keepOpen) {
                println("Checking for messages every 30s")
                inbox.messageCount
                Thread.sleep(30_000)
            }
        }
    }

}

private fun handleMessage(
    messages: Array<Message>,
    foundAction: (Pair<String, InputStream>) -> Boolean,
    sourceFolder: Folder,
    trashFolder: Folder
) {
    filterMessages(messages)
        .forEach { (message, from, inputStream) ->
            println("Found mail $from")
            val shouldDelete = foundAction(from to inputStream)
            if (shouldDelete) {
                sourceFolder.copyMessages(arrayOf(message), trashFolder)
                message.setFlag(Flags.Flag.DELETED, true)
            }
        }
}

fun filterMessages(messages: Array<Message>): Sequence<Triple<Message, String, InputStream>> {
    return messages.asSequence()
        .mapNotNull { message -> message as? IMAPMessage }
        .mapNotNull { imapMessage ->
            val internetAddress = imapMessage.from.first() as? InternetAddress
            val multipart = imapMessage.content as? Multipart
            if (internetAddress == null) return@mapNotNull null
            if (multipart == null) return@mapNotNull null
            Triple(imapMessage, internetAddress.address, multipart)

        }
        .filter { (_, mail) -> AllowedEmails.emails.contains(mail) }
        .mapNotNull { (message, mail, multipart) ->
            val pdfAttachments = (0 until multipart.count).map {
                multipart.getBodyPart(it)
            }
                .filter { Part.ATTACHMENT.equals(it.disposition) }
                .filter { it.fileName != null && it.fileName.endsWith(".pdf") }
                .toList()
            if (pdfAttachments.size == 1)
                return@mapNotNull Triple(message, mail, pdfAttachments.first().inputStream)
            null
        }

}
