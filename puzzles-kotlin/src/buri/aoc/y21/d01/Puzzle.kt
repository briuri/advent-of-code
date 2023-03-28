package buri.aoc.y21.d01

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
        assertRun(1139, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1103, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val depths = input.map { it.toInt() }
        val step = if (part.isOne()) 1 else 3

        var count = 0
        var i = 0
        while (i < depths.size - step) {
            if (depths[i] < depths[i + step]) {
                count++
            }
            i++
        }
        return count
    }
}