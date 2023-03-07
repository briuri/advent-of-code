package buri.aoc.y15.d18

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
        assertRun(4, 1)
        assertRun(768, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(14, 1)
        assertRun(781, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val steps = if (input.size < 10) 4 else 100
        var grid = Grid(input.size, input[0].length, 0)
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                val onOff = if (value == '#') 1 else 0
                grid[x, y] = onOff
            }
        }

        val corners = mutableListOf<Point2D<Int>>()
        corners.add(Point2D(0, 0))
        corners.add(Point2D(grid.width - 1, 0))
        corners.add(Point2D(0, grid.height - 1))
        corners.add(Point2D(grid.width - 1, grid.height - 1))

        for (i in 0 until steps) {
            val nextGrid = Grid(input.size, input[0].length, 0)
            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    val point = Point2D(x, y)
                    var onCount = 0
                    for (neighbor in grid.getNeighbors(point, true)) {
                        onCount += grid[neighbor]
                    }
                    val prev = grid[point]
                    val next = if (part.isTwo() && Point2D(x, y) in corners) {
                        1
                    } else if (prev == 1 && onCount != 2 && onCount != 3) {
                        0
                    } else if (prev == 0 && onCount == 3) {
                        1
                    } else {
                        prev
                    }
                    nextGrid[x, y] = next
                }
            }
            grid = nextGrid
        }
        return grid.sum()
    }
}