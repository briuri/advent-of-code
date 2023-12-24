package buri.aoc.y17.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point3D
import org.junit.Test

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
        val origin = Point3D(0, 0, 0)
        var point = origin
        var maxDistance = 0L
        for (direction in input[0].split(",")) {
            // https://www.redblobgames.com/grids/hexagons/#coordinates-cube
            point = when (direction) {
                "n" -> point.copy(y = point.y - 1, z = point.z + 1)
                "ne" -> point.copy(x = point.x + 1, y = point.y - 1)
                "se" -> point.copy(x = point.x + 1, z = point.z - 1)
                "s" -> point.copy(y = point.y + 1, z = point.z - 1)
                "sw" -> point.copy(x = point.x - 1, y = point.y + 1)
                else -> point.copy(x = point.x - 1, z = point.z + 1)
            }
            maxDistance = maxDistance.coerceAtLeast(point.getManhattanDistance(origin) / 2)
        }
        return if (part.isOne()) (point.getManhattanDistance(origin) / 2) else maxDistance
    }
}