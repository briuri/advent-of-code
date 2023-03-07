package buri.aoc.y18.d18

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
        assertRun(1147, 1)
        assertRun(456225, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(190164, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val gridHistory = mutableListOf<String>()
        val resourceHistory = mutableListOf<Int>()
        var grid = Grid(input[0].length, input.size, '.')
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                grid[x, y] = value
            }
        }
        val minutes = if (part.isOne()) 10 else 1_000_000_000
        for (i in 0 until minutes) {
            val newGrid = grid.copy()
            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    val value = grid[x, y]
                    val neighbors = grid.getSurrounding(x, y)
                    when (value) {
                        '.' -> if (neighbors['|']!! >= 3) {
                            newGrid[x, y] = '|'
                        }
                        '|' -> if (neighbors['#']!! >= 3) {
                            newGrid[x, y] = '#'
                        }
                        '#' -> if (neighbors['|']!! == 0 || neighbors['#']!! == 0) {
                            newGrid[x, y] = '.'
                        }
                    }
                }
            }
            grid = newGrid

            // Keep track of states to look for cycles.
            val state = grid.toString()
            val index = gridHistory.indexOf(state)
            if (index != -1) {
                val cycleSize = i - index
                val futureIndex = index + ((minutes - i) % cycleSize) - 1
                return resourceHistory[futureIndex]
            }
            gridHistory.add(state)
            resourceHistory.add(grid.count('|') * grid.count('#'))
        }
        return resourceHistory.last()
    }

    /**
     * Returns the counts of all characters in the surrounding squares.
     */
    private fun Grid<Char>.getSurrounding(x: Int, y: Int): Map<Char, Int> {
        val map = mutableMapOf('.' to 0, '|' to 0, '#' to 0)
        for (neighbor in this.getNeighbors(Point2D(x, y), true)) {
            val value = this[neighbor]
            map[value] = map[value]!! + 1
        }
        return map
    }
}