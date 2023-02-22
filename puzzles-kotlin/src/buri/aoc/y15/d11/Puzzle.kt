package buri.aoc.y15.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("vzbxxyzz", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("vzcaabcc", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val nextCount = if (part.isOne()) 1 else 2
        var count = 0
        var password = input[0]
        while (count < nextCount) {
            password = password.increment()
            if (password.hasStraight() && !password.isConfusing() && password.hasPairs()) {
                count++
            }
        }
        return password
    }

    /**
     * Increments the password
     */
    private fun String.increment(): String {
        val builder = StringBuilder(this)
        for (i in this.lastIndex downTo 0) {
            val value = this[i]
            if (value != 'z') {
                builder[i] = value + 1
                break
            } else {
                builder[i] = 'a'
            }
        }
        return builder.toString()
    }

    /**
     * Searches for three consecutive letters, like abc
     */
    private fun String.hasStraight(): Boolean {
        for (i in 0..this.length - 3) {
            if (this[i] == this[i + 1] - 1 && this[i] == this[i + 2] - 2) {
                return true
            }
        }
        return false
    }

    /**
     * Searches for forbidden letters
     */
    private fun String.isConfusing(): Boolean {
        return this.any { it in "ilo" }
    }

    /**
     * Searches for repeated letters.
     */
    private fun String.hasPairs(): Boolean {
        var count = 0
        var i = 0
        while (i <= this.length - 2) {
            if (this[i] == this[i + 1]) {
                count++
                i++
            }
            i++
        }
        return (count >= 2)
    }
}