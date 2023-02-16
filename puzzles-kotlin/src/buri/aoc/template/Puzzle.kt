package buri.aoc.template

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
        assertRun(0, 1)
        assertRun(0, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(0, 1)
        assertRun(0, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {

        for (line in input) {

        }
        return -1
    }
}