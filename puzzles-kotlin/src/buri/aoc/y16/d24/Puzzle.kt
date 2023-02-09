package buri.aoc.y16.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Grid
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
        val grid = Grid(input[0].length, input.size)
        val locations = mutableMapOf<Int, Pair<Int, Int>>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                grid[x, y] = value.toString()
                if (value !in "#.") {
                    locations[value.digitToInt()] = Pair(x, y)
                }
            }
        }

        // Compute all the interim steps upfront to save time.
        val steps = mutableMapOf<String, Int>()
        for (i in locations.keys) {
            for (j in locations.keys.filter { it != i }) {
                if (steps["$i$j"] == null) {
                    steps["$i$j"] = countSteps(grid, locations[i]!!, locations[j]!!)
                    steps["$j$i"] = steps["$i$j"]!!
                }
            }
        }

        // Now add different legs together.
        val locationOrder = List(locations.keys.max() + 1) { it }
        var minSteps = Int.MAX_VALUE
        for (path in generatePermutations(locationOrder, 0).filter { it[0] == 0 }) {
            var pathSteps = 0
            for (i in 0 until path.lastIndex) {
                pathSteps += steps[path[i].toString() + path[i + 1].toString()]!!
            }
            if (part == TWO) {
                pathSteps += steps[path[path.lastIndex].toString() + "0"]!!
            }
            minSteps = minSteps.coerceAtMost(pathSteps)
        }
        return minSteps
    }

    /**
     * Counts the minimum number of steps to get from one point to another.
     */
    private fun countSteps(grid: Grid, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val frontier = mutableListOf<Pair<Int, Int>>()
        val stepsTo = mutableMapOf<Pair<Int, Int>, Int>()
        frontier.add(start)
        stepsTo[start] = 0
        var current: Pair<Int, Int>?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            if (current == end) {
                break
            }
            for (next in getNeighbors(grid, current).filter { !stepsTo.containsKey(it) }) {
                frontier.add(next)
                stepsTo[next] = stepsTo[current]!! + 1
            }
        }
        return stepsTo[end]!!
    }

    /**
     * Finds valid neighbors for a point
     */
    private fun getNeighbors(grid: Grid, current: Pair<Int, Int>): List<Pair<Int, Int>> {
        return grid.getNeighbors(current.first, current.second).filter { grid[it] != "#" }
    }
}