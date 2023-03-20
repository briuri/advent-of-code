package buri.aoc.y16.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.countSteps
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(14, 1)
        assertRun(428, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(680, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromInput(input, '#')
        val locations = mutableMapOf<Int, Point2D<Int>>()
        for (point in grid.filter { it !in "#." }) {
            locations[grid[point].digitToInt()] = point
        }

        // Use a pathfinder that avoids walls.
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { grid[it] != '#' }
        }

        // Compute all the interim steps upfront to save time.
        val steps = mutableMapOf<String, Int>()
        for (i in locations.keys) {
            val stepMap = pathfinder.exploreFrom(locations[i]!!)
            for (j in locations.keys.filter { it != i }) {
                if (steps["$i$j"] == null) {
                    steps["$i$j"] = stepMap.countSteps(locations[i]!!, locations[j]!!)
                    steps["$j$i"] = steps["$i$j"]!!
                }
            }
        }

        // Now add different legs together.
        val locationOrder = List(locations.keys.max() + 1) { it }
        var minSteps = Int.MAX_VALUE
        for (path in generatePermutations(locationOrder).filter { it[0] == 0 }) {
            var pathSteps = 0
            for (i in 0 until path.lastIndex) {
                pathSteps += steps[path[i].toString() + path[i + 1].toString()]!!
            }
            if (part.isTwo()) {
                pathSteps += steps[path[path.lastIndex].toString() + "0"]!!
            }
            minSteps = minSteps.coerceAtMost(pathSteps)
        }
        return minSteps
    }
}