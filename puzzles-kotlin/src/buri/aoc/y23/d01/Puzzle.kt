package buri.aoc.y23.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import java.lang.Exception

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(142, 1)
        assertRun(56506, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(281, 2)
        assertRun(56017, 0, true)
    }

    private val textDigits = listOf(
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    )

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        return input.sumOf {
            val first = getDigit(part, it, true)
            val last = getDigit(part, it, false)
            "$first$last".toInt()
        }
    }

    /**
     * Extracts a number from the beginning or end of the string.
     *
     * Process string in reverse to handle the end. (Find "xis" in the reversed string).
     */
    private fun getDigit(part: Part, line: String, isFirst: Boolean): Int {
        val string = if (isFirst) line else line.reversed()
        val digits = if (isFirst) textDigits else textDigits.map { it.reversed() }
        for (i in string.indices) {
            val substring = string.drop(i)
            if (substring.first().isDigit()) {
                return substring.first().digitToInt()
            } else if (part.isTwo()) {
                for (text in digits) {
                    if (substring.startsWith(text)) {
                        return (digits.indexOf(text) + 1)
                    }
                }
            }
        }
        throw Exception("Couldn't find digit.")
    }
}