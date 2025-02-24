package pictures.reisinger.pdfextractor.extractor

class TsvWriter(private val output: Appendable) : Writer {

    override fun appendLine(vararg columns: String) = apply {
        output.appendLine(columns.joinToString("\t") { it })
    }

    override fun appendComment(comment: String) = apply {
        comment.splitLines()
            .forEach {
                output.append("// ")
                output.appendLine(it)
            }
    }
}