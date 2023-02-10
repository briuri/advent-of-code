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
                hasVowels(word) && hasDoubleLetter(word) && !hasBadTokens(word)
            } else {
                hasRepeatingPair(word) && hasInterruptedRepeat(word)
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
    private fun hasVowels(word: String): Boolean {
        return word.filter { it in "aeiou" }.length >= 3
    }

    /**
     * Checks if there are any letters twice in a row
     */
    private fun hasDoubleLetter(word: String): Boolean {
        for (i in 0 until word.lastIndex) {
            if (word[i] == word[i + 1]) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if a word has any forbidden sequences
     */
    private fun hasBadTokens(word: String): Boolean {
        return word.contains("ab") || word.contains("cd")
                || word.contains("pq") || word.contains("xy")
    }

    /**
     * Checks if a pair of two letters repeats without overlapping.
     */
    private fun hasRepeatingPair(word: String): Boolean {
        for (i in 0 until word.lastIndex) {
            val token = word.substring(i, i + 2)
            val index = word.indexOf(token, i + 2)
            if (index != -1) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if a pair of two letters repeats with a single letter in the middle
     */
    private fun hasInterruptedRepeat(word: String): Boolean {
        for (i in 0 until word.lastIndex - 1) {
            if (word[i] == word[i + 2]) {
                return true
            }
        }
        return false
    }
}