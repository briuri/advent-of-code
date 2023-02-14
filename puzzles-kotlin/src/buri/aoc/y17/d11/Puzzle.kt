package buri.aoc.y17.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test
import kotlin.math.absoluteValue

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(759, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(1501, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var point = Triple(0, 0, 0)
        var maxDistance = 0
        for (direction in input[0].split(",")) {
            // https://www.redblobgames.com/grids/hexagons/#coordinates-cube
            point = when (direction) {
                "n" -> point.copy(second = point.second - 1, third = point.third + 1)
                "ne" -> point.copy(first = point.first + 1, second = point.second - 1)
                "se" -> point.copy(first = point.first + 1, third = point.third - 1)
                "s" -> point.copy(second = point.second + 1, third = point.third - 1)
                "sw" -> point.copy(first = point.first - 1, second = point.second + 1)
                else -> point.copy(first = point.first - 1, third = point.third + 1)
            }
            maxDistance = maxDistance.coerceAtLeast(getManhattanDistance(point) / 2)
        }
        return if (part == ONE) (getManhattanDistance(point) / 2) else maxDistance
    }

    /**
     * Returns the Manhattan distance of a 3D coordinate.
     */
    private fun getManhattanDistance(point: Triple<Int, Int, Int>): Int {
        return point.first.absoluteValue + point.second.absoluteValue + point.third.absoluteValue
    }
}