package buri.aoc.y24.d18

import buri.aoc.common.*
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
        assertRun("22", 1)
        assertRun("288", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("6,1", 1)
        assertRun("52,5", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val isExample = (input.size < 30)
        val size = if (isExample) Point2D(7, 7) else Point2D(71, 71)
        val grid = Grid(size.x, size.y, '.')
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { grid[it] != '#' }
        }

        val start = Point2D(0, 0)
        val end = Point2D(size.x - 1, size.y - 1)

        if (part.isOne()) {
            val numBytes = if (isExample) 12 else 1024
            for (line in input.subList(0, numBytes)) {
                val tokens = line.extractInts()
                grid[tokens[0], tokens[1]] = '#'
            }
            return pathfinder.exploreFrom(start).countSteps(start, end).toString()
        }

        // Part TWO
        for (line in input) {
            val tokens = line.extractInts()
            grid[tokens[0], tokens[1]] = '#'
            if (pathfinder.exploreFrom(start).countSteps(start, end) == -1) {
                return tokens.joinToString(",")
            }
        }
        throw Exception("No corrupted bytes blocked the path.")
    }
}