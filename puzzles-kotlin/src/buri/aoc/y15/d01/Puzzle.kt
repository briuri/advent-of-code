package buri.aoc.y15.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(-1, 1, false)
        assertRun(232, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(5, 1, false)
        assertRun(1783, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var floor = 0
        for ((index, direction) in input[0].withIndex()) {
            val increment = if (direction == '(') 1 else -1
            floor += increment
            if (part == TWO && floor == -1) {
                return (index + 1)
            }
        }
        return floor
    }
}