package pictures.reisinger.pdfextractor.extractor

internal val String.fixedCase
    get(): String {
        return split(" ")
            .map { word ->
                word.lowercase()
                    .split("-")
                    .asSequence()
                    .map { it.replaceFirstChar { char -> char.uppercase() } }
                    .joinToString("-") { it }
            }
            .joinToString(" ") { it }
    }


internal fun String.splitLines(): List<String> = split("\r\n").filter { it.isNotBlank() }