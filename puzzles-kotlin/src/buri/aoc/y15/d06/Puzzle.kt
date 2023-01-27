package buri.aoc.y15.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(543903, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(14687245, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val lights = Grid()
        for (line in input) {
            val tokens = line.split(" ")
            val first = tokens[tokens.lastIndex - 2].split(",").map { it.toInt() }
            val last = tokens[tokens.lastIndex].split(",").map { it.toInt() }
            val xRange = Pair(first[0], last[0])
            val yRange = Pair(first[1], last[1])

            for (x in xRange.first..xRange.second) {
                for (y in yRange.first..yRange.second) {
                    val value = lights.get(x, y)
                    if (part == ONE) {
                        if (tokens[1] == "on") {
                            lights.set(x, y, 1)
                        } else if (tokens[1] == "off") {
                            lights.set(x, y, 0)
                        } else {  // toggle
                            val newValue = if (value == 0) 1 else 0
                            lights.set(x, y, newValue)
                        }
                    } else {
                        if (tokens[1] == "on") {
                            lights.set(x, y, value + 1)
                        } else if (tokens[1] == "off") {
                            lights.set(x, y, (value - 1).coerceAtLeast(0))
                        } else {  // toggle
                            lights.set(x, y, value + 2)
                        }
                    }
                }
            }
        }
        return lights.getSum()
    }
}

/**
 * Helper class for grid navigation
 */
class Grid {
    private val size = 1000
    private val grid = Array(size) { IntArray(size) }

    /**
     * Gets a value in the grid.
     */
    fun get(x: Int, y: Int): Int {
        return (grid[x][y])
    }

    /**
     * Sets a value in the grid.
     */
    fun set(x: Int, y: Int, value: Int) {
        grid[x][y] = value
    }

    /**
     * Sums all values in the grid.
     */
    fun getSum(): Int {
        var sum = 0
        for (x in 0 until size) {
            sum += grid[x].sum()
        }
        return sum
    }
}