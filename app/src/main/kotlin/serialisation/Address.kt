package pictures.reisinger.pdfextractor.app.serialisation

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

object Address {
    @OptIn(ExperimentalSerializationApi::class)
    val value by lazy {
        val input = Address::class.java.classLoader
            .getResourceAsStream("addressMapping.json") ?: throw IllegalStateException("No file")

        Json.decodeFromStream<AddressMapping>(input)

    }
}

typealias AddressMapping = Map<String, List<String>>