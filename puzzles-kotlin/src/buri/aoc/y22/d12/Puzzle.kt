package buri.aoc.y22.d12

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
        assertRun(31, 1)
        assertRun(504, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(29, 1)
        assertRun(500, 0, true)
    }

    private val elevation = "abcdefghijklmnopqrstuvwxyz"

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val originalStart = grid.filter { it == 'S' }.first()
        val end = grid.filter { it == 'E' }.first()
        grid[originalStart] = 'a'
        grid[end] = 'z'

        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { isTraversable(grid, current, it) }
        }

        if (part.isOne()) {
            return pathfinder.exploreFrom(originalStart).countSteps(originalStart, end)
        }

        val startsToSteps = mutableMapOf<Point2D<Int>, Int>()
        for (start in grid.filter { it == 'a' }) {
            val steps = pathfinder.exploreFrom(start).countSteps(start, end)
            if (steps != -1) {
                startsToSteps[start] = steps
            }
        }
        return startsToSteps.values.min()
    }

    /**
     * Returns true if movement is possible.
     */
    private fun isTraversable(grid: Grid<Char>, current: Point2D<Int>, next: Point2D<Int>): Boolean {
        val elevation1 = elevation.indexOf(grid[current])
        val elevation2 = elevation.indexOf(grid[next])
        return elevation2 - elevation1 < 2

    }
}