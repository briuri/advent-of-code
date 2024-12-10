package buri.aoc.y24.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(18, 1)
        assertRun(2406, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(9, 1)
        assertRun(1807, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)

        if (part.isOne()) {
            val word = "XMAS"
            val searches = mutableListOf<String>()
            for (xSpot in grid.filter { it == 'X' }) {
                // Clockwise from N to NW
                searches.add(grid.getSearchesFrom(xSpot, Point2D(0, -1), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(1, -1), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(1, 0), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(1, 1), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(0, 1), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(-1, 1), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(-1, 0), word.length))
                searches.add(grid.getSearchesFrom(xSpot, Point2D(-1, -1), word.length))
            }
            return searches.filter { it == word }.size
        }
        // Part TWO
        val words = listOf("MAS", "SAM")
        var count = 0
        for (aSpot in grid.filter { it == 'A' }) {
            if (aSpot.x == 0 || aSpot.x == grid.xRange.last || aSpot.y == 0 || aSpot.y == grid.yRange.last) {
                continue
            }
            val (x, y) = aSpot
            val criss = listOf(grid[x - 1, y - 1], grid[x, y], grid[x + 1, y + 1]).joinToString("")
            val cross = listOf(grid[x - 1, y + 1], grid[x, y], grid[x + 1, y - 1]).joinToString("")
            if (criss in words && cross in words) {
                count++
            }
        }
        return count
    }

    /**
     * Returns the string of letters starting from a position and going in some direction.
     */
    private fun Grid<Char>.getSearchesFrom(pos: Point2D<Int>, delta: Point2D<Int>, length: Int): String {
        val buffer = mutableListOf<Char>()
        var (x, y) = pos
        while (x in this.xRange && y in this.yRange && buffer.size < length) {
            buffer.add(this[x, y])
            x += delta.x
            y += delta.y
        }
        return buffer.joinToString("")
    }
}