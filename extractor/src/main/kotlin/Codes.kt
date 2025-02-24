package pictures.reisinger.pdfextractor.extractor

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object Codes {
    private val codes by lazy {
        val codes = Codes::class.java.classLoader.getResourceAsStream("codes.csv")
            ?.let { BufferedReader(InputStreamReader(it, StandardCharsets.UTF_8)) }
            ?: throw IllegalStateException("Codes not found")

        codes.useLines { lines ->
            lines.filter { it.isNotBlank() }
                .associate {
                    val (first, second) = it.split(",")
                    first to second
                }
        }
    }

    fun getCodeByName(name: String): String? {
        return (codes[name] ?: run {
            codes.asSequence()
                .find { (k) -> k.contains(name, ignoreCase = true) }
                ?.value
        })?.lowercase()
    }
}