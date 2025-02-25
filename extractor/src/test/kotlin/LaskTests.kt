package pictures.reisinger.pdfextractor.extractor

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class LaskTests {
    @Test
    fun `parse LASK vs RAPID and output it to TSV`() {
        LaskTests::class.java.classLoader.getResourceAsStream("2025-02-23 - LASK - SK Rapid.pdf")?.use { pdf ->
            val info = extractBundesligaPdf(pdf)
            createTestOutput("lask-rapid", "tsv") {
                info.toPhotomachanics(it)
            }
            info.assert()
        } ?: throw IllegalStateException("File not found")
    }

    private fun SoccerInfo.assert() = assertThat(this).all {
        prop(SoccerInfo::general).all {
            prop(GeneralInfo::type).isEqualTo("Admiral Bundesliga Grunddurchgang 19. Runde")
            prop(GeneralInfo::date).isEqualTo("23.02.2025")
            prop(GeneralInfo::arena).isEqualTo("Raiffeisen Arena Linz")
        }

        prop(SoccerInfo::home).all {
            prop(TeamInfo::name).isEqualTo("LASK")
            prop(TeamInfo::trainer).isEqualTo("Markus Schopp")
            prop(TeamInfo::players).hasSize(17)
        }

        prop(SoccerInfo::away).all {
            prop(TeamInfo::name).isEqualTo("SK RAPID")
            prop(TeamInfo::trainer).isEqualTo("Robert Klauss")
            prop(TeamInfo::players).hasSize(17)
        }

        prop(SoccerInfo::officials).all {
            prop(OfficialInfo::referee).isEqualTo("Christian-Petru Ciochirca")
            prop(OfficialInfo::assistants).containsExactly("Michael Obritzberger", "Mattias Hartl")
            prop(OfficialInfo::fourth).isEqualTo("Achim Untergasser")
            prop(OfficialInfo::`var`).isEqualTo("Alan Kijas")
            prop(OfficialInfo::avar).isEqualTo("Maximilian Weiss")
        }
    }
}

