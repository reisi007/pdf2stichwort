package pictures.reisinger.pdfextractor.extractor

/**
 * Regex to find a non-letter followed by a letter.
 *
 * This is used to uppercase the letter after the non-letter.
 */
val regex = Regex("""([^a-zA-z\u00C0-\u017FA])(.)""")

internal fun String.fixedCase(force: Boolean = false): String {
    if (force || this == uppercase()) {
        return lowercase()
            .split(" ")
            .mapIndexed { idx, word ->
                if (!force && word.length <= 3 && idx > 0)
                    return@mapIndexed word
                word.replaceFirstChar { char -> char.uppercase() }
                    .replace(regex) { match ->
                        val (_, delimiter, char) = match.groupValues
                        delimiter + char.uppercase()
                    }
            }.joinToString(" ") { it }
    } else return this
}


internal fun String.splitLines(): List<String> = split("\r\n").filter { it.isNotBlank() }

internal fun String.substringAfter(vararg items: String): String =
    items.fold(this) { acc, item -> acc.substringAfter(item, missingDelimiterValue = acc) }.trimStart()
