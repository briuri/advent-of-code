package buri.aoc.y22.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(21, 1)
        assertRun(1792, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8, 1)
        assertRun(334880, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromIntInput(input)
        if (part.isOne()) {
            var count = 0
            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    if (isVisible(grid, x, y)) {
                        count++
                    }
                }
            }
            return count
        }

        val scores = mutableListOf<Int>()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                scores.add(getScore(grid, x, y))
            }
        }
        return scores.max()
    }

    /**
     * Returns true if this tree can be seen from the edge.
     */
    private fun isVisible(grid: Grid<Int>, startX: Int, startY: Int): Boolean {
        val value = grid[startX, startY]
        // Up
        var visibleU = true
        for (down in startY - 1 downTo 0) {
            visibleU = visibleU && (grid[startX, down] < value)
        }
        // Left
        var visibleL = true
        for (x in startX - 1 downTo 0) {
            visibleL = visibleL && (grid[x, startY] < value)
        }
        // Down
        var visibleD = true
        for (y in startY + 1 until grid.height) {
            visibleD = visibleD && (grid[startX, y] < value)
        }
        // Right
        var visibleR = true
        for (x in startX + 1 until grid.width) {
            visibleR = visibleR && (grid[x, startY] < value)
        }
        return (visibleU || visibleD || visibleL || visibleR)
    }

    /**
     * Calculates the scenic score.
     */
    private fun getScore(grid: Grid<Int>, startX: Int, startY: Int): Int {
        val value = grid[startX, startY]
        // Up
        var countU = 0
        for (y in startY - 1 downTo 0) {
            countU++
            if (grid[startX, y] >= value) {
                break
            }
        }
        // Left
        var countL = 0
        for (x in startX - 1 downTo 0) {
            countL++
            if (grid[x, startY] >= value) {
                break
            }
        }
        // Down
        var countD = 0
        for (y in startY + 1 until grid.height) {
            countD++
            if (grid[startX, y] >= value) {
                break
            }
        }
        // Right
        var countR = 0
        for (x in startX + 1 until grid.width) {
            countR++
            if (grid[x, startY] >= value) {
                break
            }
        }
        return (countU * countD * countL * countR)
    }
}