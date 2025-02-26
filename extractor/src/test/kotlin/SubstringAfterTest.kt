package pictures.reisinger.pdfextractor.extractor

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SubstringAfterTest {

    @ParameterizedTest
    @MethodSource
    fun testCases(input: String, expected: String, vararg replacements: String) {
        assertThat(input.substringAfter(*replacements)).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun testCases(): Stream<Arguments> {
            return Stream.of(
                argsOf("TR: Trainer", "Trainer", "TR", ":"),
                argsOf("TR: Trainer", "Trainer", ":"),
                argsOf("Trainer", "Trainer", "TR", ":"),
                argsOf(" Test", "Test", "TR")
            )
        }

        private fun argsOf(input: String, expected: String, vararg replacements: String) =
            Arguments.of(input, expected, replacements)
    }

}