package buri.aoc.y19.d01

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
        assertRun(3345909, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(5015983, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0
        for (mass in input.map { it.toInt() }) {
            var fuel = mass / 3 - 2
            if (part.isOne()) {
                sum += fuel
            } else {
                while (fuel > 0) {
                    sum += fuel
                    fuel = fuel / 3 - 2
                }
            }
        }
        return sum
    }
}