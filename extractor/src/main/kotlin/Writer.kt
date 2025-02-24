package pictures.reisinger.pdfextractor.extractor

interface Writer {
    fun appendLine(vararg columns: String): TsvWriter
    fun appendComment(comment: String): TsvWriter
}