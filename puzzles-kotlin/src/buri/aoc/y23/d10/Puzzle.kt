package buri.aoc.y23.d10

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.countSteps
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Point2D
import org.junit.Test
import java.util.*
import kotlin.time.measureTime

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(8, 1)
        assertRun(6897, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(10, 2)
        assertRun(367, 0, true)
    }

    private val validPipesBelow = listOf('|', 'L', 'J')
    private val validPipesAbove = listOf('|', '7', 'F')
    private val validPipesLeft = listOf('-', 'L', 'F')
    private val validPipesRight = listOf('-', 'J', '7')
    private val notTraversable = listOf('S', '.')
    private val unknown = '?'

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var grid = Grid.fromCharInput(input)
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { isTraversable(grid, current, it) }
        }

        val start = grid.filter { it == 'S' }.first()
        val cameFrom = pathfinder.exploreFrom(start)
        var pipes = cameFrom.keys
        if (part.isOne()) {
            var maxSteps = Int.MIN_VALUE
            for (pos in pipes) {
                maxSteps = maxSteps.coerceAtLeast(cameFrom.countSteps(start, pos))
            }
            return maxSteps
        }

        // Stretch the grid so that gaps between pipes become empty squares.
        grid = grid.stretch(cameFrom.keys)
        pipes = pathfinder.exploreFrom(Point2D(start.x * 2, start.y * 2)).keys

        // Anything that is not a connected pipe is initially marked as unknown '?'.
        // Explore from unknown spots and try to get to the outer edge of the grid.
        val visited = pipes.toMutableSet()
        val outsideFinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { grid[it] == unknown }
        }
        for (point in grid.filter { it == unknown }) {
            if (point !in visited) {
                val neighbors = outsideFinder.exploreFrom(point).keys
                if (neighbors.any { it.x == 0 || it.x == grid.xRange.last || it.y == 0 || it.y == grid.yRange.last }) {
                    // If we got outside, mark all connected neighbors as no longer unknown.
                    for (pos in neighbors) {
                        grid[pos] = '.'
                    }
                }
                visited.addAll(neighbors)
            }
        }

        // Shrinking the grid discards any excess generated unknown squares.
        // Remaining unknown squares must be on the inside.
        return grid.shrink().filter { it == unknown }.size
    }

    /**
     * Returns true if movement is possible.
     */
    private fun isTraversable(grid: Grid<Char>, current: Point2D<Int>, next: Point2D<Int>): Boolean {
        // Can't leave the pipe or return to start.
        if (grid[next] in notTraversable) {
            return false
        }
        // Otherwise, depends on which way the ends of the current pipe face.
        return when (grid[current]) {
            '|' -> current.y + 1 == next.y || current.y - 1 == next.y
            '-' -> current.x + 1 == next.x || current.x - 1 == next.x
            'L' -> current.x + 1 == next.x || current.y - 1 == next.y
            'J' -> current.x - 1 == next.x || current.y - 1 == next.y
            '7' -> current.x - 1 == next.x || current.y + 1 == next.y
            'F' -> current.x + 1 == next.x || current.y + 1 == next.y
            // Pipe in next position must point back at the start.
            'S' -> ((current.y + 1 == next.y && grid[next] in validPipesBelow)
                    || (current.y - 1 == next.y && grid[next] in validPipesAbove)
                    || (current.x - 1 == next.x && grid[next] in validPipesLeft)
                    || (current.x + 1 == next.x && grid[next] in validPipesRight))

            else -> false
        }
    }

    /**
     * Doubles the size of the grid and fills in the gaps with the correct type of pipe or an empty square.
     */
    private fun Grid<Char>.stretch(pipes: Set<Point2D<Int>>): Grid<Char> {
        val stretched = Grid(width * 2, height * 2, unknown)
        for (y in yRange) {
            for (x in xRange) {
                // Eliminate any stray pipes that weren't traversed in part 1.
                stretched[x * 2, y * 2] = if (Point2D(x, y) in pipes) get(x, y) else unknown
                // Examine the old pipe on the right and down to see if we need to insert a connector.
                if (isRightPipe(pipes, x, y)) {
                    stretched[x * 2 + 1, y * 2] = '-'
                }
                if (isDownPipe(pipes, x, y)) {
                    stretched[x * 2, y * 2 + 1] = '|'
                }
                // The diagonal square (x * 2 + 1, y * 2 + 1) is never a pipe since pipes don't travel diagonally.
            }
        }
        return stretched
    }

    /**
     * Halves the size of the grid, discarding the stretched parts and keeping only the original squares.
     */
    private fun Grid<Char>.shrink(): Grid<Char> {
        val shrunk = Grid(width / 2, height / 2, '.')
        for (y in yRange step 2) {
            for (x in xRange step 2) {
                shrunk[x / 2, y / 2] = get(x, y)
            }
        }
        return shrunk
    }

    /**
     * Determines if the point to the right of a known value should be a connector pipe.
     */
    private fun Grid<Char>.isRightPipe(pipes: Set<Point2D<Int>>, x: Int, y: Int): Boolean {
        if (x + 1 < width) {
            val currentValue = get(x, y)
            val nextValue = get(x + 1, y)
            // Base case for pipes that need a connection in the middle of them.
            if (pipes.containsAll(listOf(Point2D(x, y), Point2D(x + 1, y)))
                && currentValue in validPipesLeft && nextValue in validPipesRight) {
                return true
            }
            // Special case for Start-adjacent squares
            if (currentValue == 'S' && nextValue in validPipesRight
                || currentValue in validPipesLeft && nextValue == 'S') {
                return true
            }
        }
        return false
    }

    /**
     * Determines if the point below a known value should be a connector pipe.
     */
    private fun Grid<Char>.isDownPipe(pipes: Set<Point2D<Int>>, x: Int, y: Int): Boolean {
        if (y + 1 < height) {
            val currentValue = get(x, y)
            val nextValue = get(x, y + 1)
            // Base case for pipes that need a connection in the middle of them.
            if (pipes.containsAll(listOf(Point2D(x, y), Point2D(x, y + 1)))
                && currentValue in validPipesAbove && nextValue in validPipesBelow) {
                return true
            }
            // Special case for Start-adjacent squares
            if (currentValue == 'S' && nextValue in validPipesBelow
                || currentValue in validPipesAbove && nextValue == 'S') {
                return true
            }
        }
        return false
    }
}