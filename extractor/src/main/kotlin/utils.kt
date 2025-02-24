package pictures.reisinger.pdfextractor.extractor

internal val String.fixedCase
    get(): String {
        return split(" ")
            .map { it.lowercase().replaceFirstChar { it.uppercase() } }
            .joinToString(" ") { it }
    }


internal fun String.splitLines(): List<String> = split("\r\n").filter { it.isNotBlank() }