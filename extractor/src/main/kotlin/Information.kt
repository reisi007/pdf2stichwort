package pictures.reisinger.pdfextractor.extractor

data class GeneralInfo(val type: String, val date: String, val arena: String)

data class SoccerInfo(val general: GeneralInfo, val home: TeamInfo, val away: TeamInfo, val officials: OfficialInfo)

data class TeamInfo(val players: Map<Int, String>, val trainer: String, val name: String)

data class OfficialInfo(
    val referee: String,
    val assistants: List<String>,
    val fourth: String,
    val `var`: String,
    val avar: String
)
