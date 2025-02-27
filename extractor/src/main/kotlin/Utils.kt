package pictures.reisinger.pdfextractor.extractor

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

internal fun String.substringAfter(vararg items: String): String {
    var final = this

    items.forEach { item ->
        final = final.substringAfter(item, missingDelimiterValue = final)
    }

    return final.trimStart()
}