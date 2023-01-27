package buri.aoc.y15.d11

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
        val nextCount = if (part == ONE) { 1 } else { 2 }
        var count = 0
        var password = input[0]
        while (count < nextCount) {
            password = increment(password)
            if (hasStraight(password) && !isConfusing(password) && hasPairs(password)) {
                count++
            }
        }
        return password
    }

    /**
     * Increments the password
     */
    private fun increment(password: String): String {
        val builder = StringBuilder(password)
        for (index in password.lastIndex downTo 0) {
            val value = password[index]
            if (value != 'z') {
                builder[index] = value + 1
                break
            }
            else {
                builder[index] = 'a'
            }
        }
        return builder.toString()
    }

    /**
     * Searches for three consecutive letters, like abc
     */
    private fun hasStraight(password: String): Boolean {
        for (index in 0..password.length - 3) {
            if (password[index] == password[index + 1] - 1 && password[index] == password[index + 2] - 2) {
                return true
            }
        }
        return false
    }

    /**
     * Searches for forbidden letters
     */
    private fun isConfusing(password: String): Boolean {
        return (password.contains('i') || password.contains('l') || password.contains('l'))
    }

    /**
     * Searches for repeated letters.
     */
    private fun hasPairs(password: String): Boolean {
        var count = 0
        var index = 0
        while (index <= password.length - 2) {
            if (password[index] == password[index + 1]) {
                count++
                index++
            }
            index++
        }
        return (count >= 2)
    }
}