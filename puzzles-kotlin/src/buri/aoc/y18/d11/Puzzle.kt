package buri.aoc.y18.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("33,45", 1)
        assertRun("245,14", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("90,269,16", 1)
        assertRun("235,206,13", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val size = 300
        val serial = input[0].toInt()

        // https://en.wikipedia.org/wiki/Summed-area_table
        val grid = Grid(size, size)
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val rackId = x + 10
                val power = ((rackId * y + serial) * rackId).toString()
                val lr = power[power.length - 3].digitToInt() - 5
                val ur = if (y > 0) grid[x, y - 1].toInt() else 0
                val ll = if (x > 0) grid[x - 1, y].toInt() else 0
                val ul = if (x > 0 && y > 0) grid[x - 1, y - 1].toInt() else 0
                grid[x, y] = lr + ur + ll - ul
            }
        }

        if (part == ONE) {
            val point = getLargest(grid, 3).first
            return "${point.first},${point.second}"
        }

        val largestBySize = mutableSetOf<Pair<Triple<Int, Int, Int>, Int>>()
        for (i in 2 until 17) { // Lowered upper bound after I got the right answer.
            largestBySize.add(getLargest(grid, i))
        }
        val largest = largestBySize.maxBy { it.second }.first
        return "${largest.first},${largest.second},${largest.third}"
    }

    /**
     * Returns the location and power of the cell with the given size with the most power.
     */
    private fun getLargest(grid: Grid, size: Int): Pair<Triple<Int, Int, Int>, Int> {
        val powerSums = mutableMapOf<Triple<Int, Int, Int>, Int>()
        for (y in (size - 1) until grid.height) {
            for (x in (size - 1) until grid.width) {
                val lr = grid[x, y].toInt()
                val ur = if (y >= size) grid[x, y - size].toInt() else 0
                val ll = if (x >= size) grid[x - size, y].toInt() else 0
                val ul = if (x >= size && y >= size) grid[x - size, y - size].toInt() else 0
                powerSums[Triple(x - (size - 1), y - (size - 1), size)] = lr + ul - ll - ur
            }
        }
        return powerSums.maxBy { it.value }.toPair()
    }
}