package buri.aoc.y15.d25

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
        assertRun(8997277, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val row = input[0].split("row ")[1].split(",")[0].toInt()
        val column = input[0].split("column ")[1].dropLast(1).toInt()
        var index = 1
        for (x in 1 until row) {
            index += x
        }
        for (y in 1 until column) {
            index += y + row
        }

        var value = 20151125L
        for (i in 2 .. index) {
            value = (value * 252533L) % 33554393L
        }
        return value
    }
}