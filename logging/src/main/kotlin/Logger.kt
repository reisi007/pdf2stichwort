package pictures.reisinger.pdfextractor.logging

import org.slf4j.Logger

class Logger internal constructor(private val LOGGER: Logger) {

    fun info(text: String) {
        LOGGER.info(text)
    }

    fun debug(text: String) {
        LOGGER.debug(text)
    }

    fun trace(text: String) {
        LOGGER.trace(text)
    }

    fun error(e: Throwable, text: String) {
        LOGGER.error(text, e)
    }
}