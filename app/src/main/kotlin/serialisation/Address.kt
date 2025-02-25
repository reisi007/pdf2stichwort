package pictures.reisinger.pdfextractor.app.serialisation

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.nio.file.Paths
import kotlin.io.path.inputStream

object Address {
    @OptIn(ExperimentalSerializationApi::class)
    val value by lazy {
        val input = Paths.get("./config/addressMapping.json").inputStream()
        Json.decodeFromStream<AddressMapping>(input)
    }
}

typealias AddressMapping = Map<String, List<String>>