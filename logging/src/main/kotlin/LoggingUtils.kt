package  pictures.reisinger.pdfextractor.logging

import org.slf4j.LoggerFactory


val Any.LOGGER: Logger
    get() = Logger(LoggerFactory.getLogger(this::class.java))