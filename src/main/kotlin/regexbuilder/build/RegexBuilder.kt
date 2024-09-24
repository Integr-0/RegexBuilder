package net.integr.regexbuilder.build

class RegexBuilder {
    private var matchingGroup = ""

    fun match(char: Char): RegexBuilder {
        matchingGroup += convertSpecialCases(char)
        return this
    }

    fun match(chars: String): RegexBuilder {
        matchingGroup += "($chars)"
        return this
    }

    fun matchAny(): RegexBuilder {
        matchingGroup += '.'
        return this
    }

    fun matchAnyOf(chars: String): RegexBuilder {
        matchingGroup += "[$chars]"
        return this
    }

    fun matchAnyWithout(chars: String): RegexBuilder {
        matchingGroup += "[^$chars]"
        return this
    }

    fun matchAnyBetween(vararg charPairs: Pair<Char, Char>): RegexBuilder {
        var str = ""

        for (pair in charPairs) {
            str += pair.first + "-" + pair.second
        }

        matchingGroup += "[$str]"
        return this
    }

    fun matchAnyBetween(char1: Char, char2: Char): RegexBuilder {
        matchingGroup += "[$char1-$char2]"
        return this
    }

    fun matchGroup(builder: RegexBuilder): RegexBuilder {
        matchingGroup += '(' + builder.getString() + ')'
        return this
    }

    fun matchNonGroup(builder: RegexBuilder): RegexBuilder {
        matchingGroup += "(?:" + builder.getString() + ')'
        return this
    }

    fun exact(amount: Int): RegexBuilder {
        matchingGroup += "{$amount}"
        return this
    }

    fun exactToExact(start: Int, end: Int): RegexBuilder {
        matchingGroup += "{$start,$end}"
        return this
    }

    fun exactOrMore(amount: Int): RegexBuilder {
        matchingGroup += "{$amount,}"
        return this
    }

    fun noneOrMore(): RegexBuilder {
        matchingGroup += '*'
        return this
    }

    fun oneOrMore(): RegexBuilder {
        matchingGroup += '+'
        return this
    }

    fun wordCharacter(): RegexBuilder {
        matchingGroup += "\\w"
        return this
    }

    fun digitCharacter(): RegexBuilder {
        matchingGroup += "\\d"
        return this
    }

    fun whitespaceCharacter(): RegexBuilder {
        matchingGroup += "\\s"
        return this
    }

    fun notWordCharacter(): RegexBuilder {
        matchingGroup += "\\W"
        return this
    }

    fun notDigitCharacter(): RegexBuilder {
        matchingGroup += "\\D"
        return this
    }

    fun notWhitespaceCharacter(): RegexBuilder {
        matchingGroup += "\\S"
        return this
    }

    fun matchOneOf(chars1: String, chars2: String): RegexBuilder {
        matchingGroup += "($chars1|$chars2)"
        return this
    }

    fun asFewAsPossible(): RegexBuilder {
        matchingGroup += '?'
        return this
    }

    private fun convertSpecialCases(char: Char): String {
        return when (char) {
            '(' -> "\\("
            ')' -> "\\)"
            '.' -> "\\."
            '*' -> "\\*"
            '\t' -> "\\t"
            '\n' -> "\\n"
            '\r' -> "\\r"
            '\\' -> "\\\\"
            else -> char.toString()
        }
    }

    fun build(): Regex {
        return Regex(matchingGroup)
    }

    private fun getString() = matchingGroup
}