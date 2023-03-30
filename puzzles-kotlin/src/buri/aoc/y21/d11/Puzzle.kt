package buri.aoc.y21.d11

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
        assertRun(1656, 1)
        assertRun(1617, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(195, 1)
        assertRun(258, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromIntInput(input)
        val times = if (part.isOne()) 100 else 1000
        var flashes = 0
        repeat(times) {
            val newFlashes = tick(grid)
            flashes += newFlashes
            if (part.isTwo() && newFlashes == grid.width * grid.height) {
                return it + 1
            }
        }
        return flashes
    }

    /**
     * Does the next turn and returns the number of flashes.
     */
    private fun tick(grid: Grid<Int>): Int {
        // First, increase each energy level.
        for (y in grid.yRange) {
            for (x in grid.xRange) {
                grid[x, y] = grid[x, y] + 1
            }
        }

        // Then, flash any unflashed octopi > 9
        val flashed = Grid(grid.width, grid.height, 0)
        var lastFlashCount: Int
        do {
            lastFlashCount = flashed.sum()
            for (y in grid.yRange) {
                for (x in grid.xRange.filter { grid[it, y] > 9 && flashed[it, y] == 0 }) {
                    flashed[x, y] = 1
                    for (neighbor in grid.getNeighbors(Point2D(x, y), true)) {
                        grid[neighbor] = grid[neighbor] + 1
                    }
                }
            }
        } while (flashed.sum() != lastFlashCount)

        // Finally, set flashed levels to 0
        for (y in grid.yRange) {
            for (x in grid.xRange.filter { flashed[it, y] == 1 }) {
                grid[x, y] = 0
            }
        }
        return flashed.sum()
    }
}