package pictures.reisinger.pdfextractor.extractor

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class FixedCaseTests {

    @ParameterizedTest
    @MethodSource
    fun testCases(input: String, force: Boolean, expected: String) {
        assertThat(input.fixedCase(force)).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun testCases(): Stream<Arguments> {
            return Stream.of(
                argsOf("GRUNDDURCHGANG", false, "Grunddurchgang"),
                argsOf("RIED IM INNKREIS", false, "Ried im Innkreis"),
                argsOf("UEFA Nations League", false, "UEFA Nations League"),
                argsOf("ORT IM INNKREIS", false, "Ort im Innkreis"),
                argsOf("Vorname NACH-NAME", true, "Vorname Nach-Name"),
                argsOf("Some D'ANGELO", true, "Some D'Angelo"),
                argsOf("Thomas MÜLLER", true, "Thomas Müller"),
                argsOf("Kouadio Guy Ange AHOUSSOU", true, "Kouadio Guy Ange Ahoussou"),
            )
        }

        private fun argsOf(input: String, force: Boolean, expected: String) =
            Arguments.of(input, force, expected)
    }
}