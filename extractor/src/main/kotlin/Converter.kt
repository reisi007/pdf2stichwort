package pictures.reisinger.pdfextractor.extractor

fun SoccerInfo.toPhotomachanics(appendable: Appendable) {
    write(TsvWriter(appendable))
}


private fun SoccerInfo.write(writer: Writer): Unit = with(writer) {
    val homeCode = Codes.getCodeByName(home.name)
    val awayCode = Codes.getCodeByName(away.name)
    appendComment("Basis Informationen")
    val teams = home.name + " vs. " + away.name
    appendLine("teams", teams)
    appendLine(
        "info",
        "${general.type} $teams am ${general.date}"
    )
    general.write(writer)

    home.write(writer, "h")
    homeCode?.let { home.write(writer, it) }

    away.write(writer, "a")
    awayCode?.let { away.write(writer, it) }

    officials.write(writer)
}


private fun GeneralInfo.write(writer: Writer) = with(writer) {
    appendLine("stadion", arena)
    appendLine("typ", type)
    appendLine("datum", date)
}


private fun TeamInfo.write(writer: Writer, prefix: String) = with(writer) {
    appendComment("Team")
    appendLine(prefix, name)
    appendLine("${prefix}t", trainer)
    players.forEach { (id, name) ->
        appendLine(prefix + id, name)
    }
}


private fun OfficialInfo.write(writer: Writer) = with(writer) {
    appendComment("Officials")
    appendLine("r1", referee)

    assistants.forEachIndexed { idx, name ->
        appendLine("r${idx + 2}", name)
    }

    appendLine("r4", fourth)
    appendLine("var1", `var`)
    appendLine("var2", avar)
}