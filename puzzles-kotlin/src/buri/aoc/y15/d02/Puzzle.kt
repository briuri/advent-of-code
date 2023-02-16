package buri.aoc.y15.d02

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
        assertRun(101, 1)
        assertRun(1586300, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(48, 1)
        assertRun(3737498, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var total = 0
        for (line in input) {
            val sides = line.split("x").map { it.toInt() }.sorted()
            val paper = if (part == ONE) {
                (3 * sides[0] * sides[1]) + (2 * sides[1] * sides[2]) + (2 * sides[2] * sides[0])
            } else {
                (2 * (sides[0] + sides[1])) + (sides[0] * sides[1] * sides[2])
            }
            total += paper
        }
        return total
    }
}