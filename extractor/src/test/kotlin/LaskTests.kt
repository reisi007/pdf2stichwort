package pictures.reisinger.pdfextractor.extractor

import org.junit.jupiter.api.Test

class LaskTests {
    @Test
    fun `parse LASK vs RAPID and output it to TSV`() {
        LaskTests::class.java.classLoader.getResourceAsStream("2025-02-23 - LASK - SK Rapid.pdf")?.use { pdf ->
            val info = extractBundesligaPdf(pdf)
            createTestOutput("lask-rapid", "tsv") {
                info.toPhotomachanics(it)
            }
        } ?: throw IllegalStateException("File not found")
    }
}