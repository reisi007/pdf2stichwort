package pictures.reisinger.pdfextractor.extractor

import pictures.reisinger.pdfextractor.logging.LOGGER
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createParentDirectories

fun createTestOutput(id: String, ext: String = "txt", action: (Appendable) -> Unit) {
    return Paths.get("testout", "$id.$ext").apply {
        createParentDirectories()
        LOGGER.trace("Wrote file to " + absolutePathString())
    }.bufferedWriter(StandardCharsets.UTF_8).use(action)
}