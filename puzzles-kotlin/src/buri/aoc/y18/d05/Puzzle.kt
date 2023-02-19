package buri.aoc.y18.d05

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
        assertRun(10, 1)
        assertRun(10132, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4, 1)
        assertRun(4572, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val reduced = reduce(input[0])
        if (part == ONE) {
            return reduced.length
        }
        var minLength = Int.MAX_VALUE
        for (value in reduced.lowercase().toSet()) {
            val regexp = String.format("[%c%c]", value, value.uppercaseChar()).toRegex()
            minLength = minLength.coerceAtMost(reduce(reduced.replace(regexp, "")).length)
        }
        return minLength
    }

    /**
     * Runs all reactions and returns the final string.
     */
    private fun reduce(rawPolymer: String): String {
        val polymer = StringBuilder(rawPolymer)
        do {
            val prevLength = polymer.length
            var i = 0
            while (i in 0 until polymer.lastIndex) {
                if (polymer[i] != polymer[i + 1] && polymer[i].lowercase() == polymer[i + 1].lowercase()) {
                    polymer.delete(i, i + 2)
                } else {
                    i++
                }
            }
        } while (polymer.length != prevLength)
        return polymer.toString()
    }
}