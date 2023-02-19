package buri.aoc.y15.d25

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
        assertRun(8997277, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input[0].extractInts()
        var index = 1
        for (x in 1 until numbers[0]) {
            index += x
        }
        for (y in 1 until numbers[1]) {
            index += y + numbers[0]
        }

        var value = 20151125L
        for (i in 2..index) {
            value = (value * 252533L) % 33554393L
        }
        return value
    }
}