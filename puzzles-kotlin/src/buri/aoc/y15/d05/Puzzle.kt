package buri.aoc.y15.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(255, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(55, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var count = 0
        for (word in input) {
            val niceCondition = if (part == ONE) {
                word.hasVowels() && word.hasDoubleLetter() && !word.hasBadTokens()
            } else {
                word.hasRepeatingPair() && word.hasInterruptedRepeat()
            }
            if (niceCondition) {
                count++
            }
        }
        return count
    }

    /**
     * Checks if a word has at least 3 vowels
     */
    private fun String.hasVowels(): Boolean {
        return this.filter { it in "aeiou" }.length >= 3
    }

    /**
     * Checks if there are any letters twice in a row
     */
    private fun String.hasDoubleLetter(): Boolean {
        for (i in 0 until this.lastIndex) {
            if (this[i] == this[i + 1]) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if a word has any forbidden sequences
     */
    private fun String.hasBadTokens(): Boolean {
        return this.contains("ab") || this.contains("cd")
                || this.contains("pq") || this.contains("xy")
    }

    /**
     * Checks if a pair of two letters repeats without overlapping.
     */
    private fun String.hasRepeatingPair(): Boolean {
        for (i in 0 until this.lastIndex) {
            val token = this.substring(i, i + 2)
            val index = this.indexOf(token, i + 2)
            if (index != -1) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if a pair of two letters repeats with a single letter in the middle
     */
    private fun String.hasInterruptedRepeat(): Boolean {
        for (i in 0 until this.lastIndex - 1) {
            if (this[i] == this[i + 2]) {
                return true
            }
        }
        return false
    }
}