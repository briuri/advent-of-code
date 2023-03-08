package buri.aoc.y19.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(1650, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1129, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val range = input[0].extractInts(false)
        var count = 0
        for (password in range[0]..range[1]) {
            val chars = password.toString().toCharArray().toList()
            if (chars == chars.sorted() && hasPairs(part, chars)) {
                count++
            }
        }
        return count
    }

    /**
     * Searches for repeated letters.
     */
    private fun hasPairs(part: Part, chars: List<Char>): Boolean {
        var i = 0
        while (i <= chars.size - 2) {
            if (part.isOne()) {
                if (chars[i] == chars[i + 1]) {
                    return true
                }
                i++
            } else {
                val value = chars[i]
                // Pair not followed by a third identical number.
                if (value == chars[i + 1] && (i + 2 == chars.size || value != chars[i + 2])) {
                    return true
                } else {
                    // Jump ahead to first different number.
                    while (i < chars.size && chars[i] == value) {
                        i++
                    }
                }
            }
        }
        return false
    }
}