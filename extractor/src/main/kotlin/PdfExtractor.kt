package pictures.reisinger.pdfextractor.extractor

import org.apache.pdfbox.Loader
import org.apache.pdfbox.io.RandomAccessReadBuffer
import org.apache.pdfbox.text.PDFTextStripperByArea
import java.awt.Rectangle
import java.io.InputStream

fun extractBundesligaPdf(input: InputStream) = extractFromPdf(input, "Admiral Bundesliga")

internal fun extractFromPdf(input: InputStream, league: String): SoccerInfo {
    val pdf = Loader.loadPDF(RandomAccessReadBuffer(input))
    val page = pdf.getPage(0)
    val mediaBox = page.mediaBox
    val topHeight = (0.3 * mediaBox.height).toInt()
    val halfWidth = (0.5 * mediaBox.width).toInt()
    val centerHeight = (0.45 * mediaBox.height).toInt()
    val bottomHeight = (0.1 * mediaBox.height).toInt()

    val stripper = PDFTextStripperByArea().apply {
        addRegion("top", Rectangle(0, 0, mediaBox.width.toInt(), topHeight))
        addRegion("home", Rectangle(0, topHeight, halfWidth, centerHeight))
        addRegion("away", Rectangle(halfWidth, topHeight, halfWidth, centerHeight))
        addRegion("bottom", Rectangle(0, topHeight + centerHeight, mediaBox.width.toInt(), bottomHeight))
        extractRegions(page)
    }

    return SoccerInfo(
        extractInfo(stripper.getTextForRegion("top").splitLines(), league),
        extractTeam(stripper.getTextForRegion("home").splitLines()),
        extractTeam(stripper.getTextForRegion("away").splitLines()),
        extractOfficial(stripper.getTextForRegion("bottom").splitLines())
    )
}


internal fun extractInfo(raw: List<String>, league: String): GeneralInfo {
    val date = raw[4]
    val arena = raw[3]
    return GeneralInfo(
        "$league ${raw[0].fixedCase()} ${raw[1].fixedCase()}",
        date,
        arena
    )
}

private val EXTRACT_PLAYER = Regex("""^(\d+)\s(.+?)(\s[TC]{1,2})?${'$'}""")

internal fun extractTeam(raw: List<String>): TeamInfo {
    val name = raw.first()
    val trainer = raw.last().substringAfter("TR").trim().fixedCase(force = true)
    val players = raw.subList(1, raw.size - 2).associate {
        val (_, number, player) = EXTRACT_PLAYER.matchEntire(it)?.groupValues
            ?: throw IllegalStateException("Cannot extract player from $it")
        number.toInt() to player.fixedCase(force = true)
    }

    return TeamInfo(players, trainer, name)
}

internal fun extractOfficial(raw: List<String>): OfficialInfo {
    val referee = raw[0].substringAfter("TR", ":").trim().fixedCase(force = true)
    val assistants = raw[1].substringAfter("TR", ":")
        .split(",")
        .map { it.trim().fixedCase(force = true) }
    val fourth = raw[2].substringAfter("TR", ":").trim().fixedCase(force = true)
    val (`var`, avar) = raw[3].substringAfter("TR", ":")
        .split("/")
        .map { it.trim().fixedCase(force = true) }
    return OfficialInfo(
        referee,
        assistants,
        fourth,
        `var`,
        avar
    )
}
