package buri.aoc.y24.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
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
        assertRun(1, 1)
        assertRun(1307, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(19 + 12 + 14 + 12 + 22 + 4 + 3, 1)
        assertRun(986545, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val isExample = (input.size < 20)
        val threshold = if (isExample) 64L else 100L
        val cheatMax = if (part.isOne()) 2L else 20L

        val grid = Grid.fromCharInput(input)
        val start = grid.filter { it == 'S' }.first()
        val end = grid.filter { it == 'E' }.first()
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { grid[it] != '#' }
        }
        val path = pathfinder.exploreFrom(start).getPathAsMap(start, end)
        val steps = path.size - 1

        var count = 0
        for (cheat in grid.findCheats(path, cheatMax)) {
            val stepsTo = path[cheat.first]!!
            val stepsFrom = steps - path[cheat.second]!!
            val savings = steps - (stepsTo + cheat.first.getManhattanDistance(cheat.second) + stepsFrom)
            if (savings >= threshold) {
                count++
            }
        }
        return count
    }

    /**
     * Finds start/end squares with the right distance between them, without checking if there are walls in between.
     */
    private fun Grid<Char>.findCheats(
        path: Map<Point2D<Int>, Int>,
        cheatMax: Long
    ): List<Pair<Point2D<Int>, Point2D<Int>>> {
        val cheats = mutableListOf<Pair<Point2D<Int>, Point2D<Int>>>()
        for (start in this.filter { it in listOf('.', 'S') }) {
            // Find points within the right distance that are closer to end than start.
            val ends = this.filter { it in listOf('.', 'E') }
                .filter { start.getManhattanDistance(it) in 2L..cheatMax && path[start]!! < path[it]!! }
            for (end in ends) {
                cheats.add(Pair(start, end))
            }
        }
        return cheats
    }

    /**
     * Builds a path from start to end with the cameFrom map. Switched from a list of points to a map to reduce runtime
     * (previously called list.indexOf four times for each pair of points).
     */
    private fun Map<Point2D<Int>, Point2D<Int>?>.getPathAsMap(
        start: Point2D<Int>,
        end: Point2D<Int>
    ): Map<Point2D<Int>, Int> {
        val path = mutableListOf<Point2D<Int>>()
        var current = end
        while (true) {
            path.add(current)
            if (current == start) {
                break
            }
            current = this[current]!!
        }
        path.reverse()
        return path.mapIndexed { index, point2D -> point2D to index }.toMap()
    }
}