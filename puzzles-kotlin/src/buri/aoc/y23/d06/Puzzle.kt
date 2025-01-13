package buri.aoc.y23.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(288, 1)
        assertRun(74698, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(71503, 1)
        assertRun(27563421, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var times = input[0].extractLongs()
        var distances = input[1].extractLongs()
        if (part.isTwo()) {
            times = listOf(times.joinToString("").toLong())
            distances = listOf(distances.joinToString("").toLong())
        }

        val ways = mutableMapOf<Int, Int>()
        for (race in times.indices) {
            ways[race] = 0
            for (holdTime in 0..times[race]) {
                val distance = holdTime * (times[race] - holdTime)
                if (distance > distances[race]) {
                    ways[race] = ways[race]!! + 1
                }
            }
        }
        return ways.values.product()
    }
}