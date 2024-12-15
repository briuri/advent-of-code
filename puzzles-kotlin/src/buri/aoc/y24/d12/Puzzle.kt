package buri.aoc.y24.d12

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.position.Direction
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
        assertRun(140, 1)
        assertRun(772, 2)
        assertRun(1930, 3)
        assertRun(1450422, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(80, 1)
        assertRun(436, 2)
        assertRun(1206, 3)
        assertRun(236, 4)
        assertRun(368, 5)
        assertRun(906606, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { grid[it] == grid[current] }
        }

        // Regions identified by upper-leftmost position.
        val regions = mutableMapOf<Point2D<Int>, Set<Point2D<Int>>>()
        // Directions where a fence exists for each position on the grid.
        val fences = mutableMapOf<Point2D<Int>, MutableSet<Direction>>()

        for (y in grid.yRange) {
            for (x in grid.xRange) {
                val pos = Point2D(x, y)
                if (regions.values.none { pos in it }) {
                    regions[pos] = pathfinder.exploreFrom(pos).keys
                }
                fences.putIfAbsent(pos, mutableSetOf())
                if (x == 0 || grid[x - 1, y] != grid[pos]) {
                    fences[pos]!!.add(Direction.WEST)
                }
                if (x == grid.xRange.last || grid[x + 1, y] != grid[pos]) {
                    fences[pos]!!.add(Direction.EAST)
                }
                if (y == 0 || grid[x, y - 1] != grid[pos]) {
                    fences[pos]!!.add(Direction.NORTH)
                }
                if (y == grid.yRange.last || grid[x, y + 1] != grid[pos]) {
                    fences[pos]!!.add(Direction.SOUTH)
                }
            }
        }
        // In part two, reduce fence count to represent polygon sides. Explore from upper-leftmost point.
        if (part.isTwo()) {
            val needsSouth = listOf(Direction.WEST, Direction.EAST)
            for (pos in fences.keys) {
                for (direction in fences[pos]!!) {
                    var diff = if (direction in needsSouth) {
                        Point2D(0, 1)
                    }
                    // Otherwise move east to explore.
                    else {
                        Point2D(1, 0)
                    }
                    var next = Point2D(pos.x + diff.x, pos.y + diff.y)
                    // While next position shares the same value and fence direction, keep exploring
                    while (grid.isInBounds(next) && grid[next] == grid[pos] && direction in fences[next]!!) {
                        if (direction in fences[next]!!) {
                            fences[next]!!.remove(direction)
                        }
                        next = Point2D(next.x + diff.x, next.y + diff.y)
                    }
                }
            }
        }

        var prices = 0
        for (region in regions) {
            val totalFences = region.value.sumOf { fences[it]!!.size }
            prices += (totalFences * region.value.size)
        }
        return prices
    }
}