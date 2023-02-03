package buri.aoc.y15.d18

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Grid
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
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
        var grid = Grid(input.size, input[0].length)
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                val onOff = if (value == '#') 1 else 0
                grid.set(x, y, onOff)
            }
        }

        val corners = mutableListOf<Pair<Int, Int>>()
        corners.add(Pair(0, 0))
        corners.add(Pair(grid.width - 1, 0))
        corners.add(Pair(0, grid.height - 1))
        corners.add(Pair(grid.width - 1, grid.height - 1))

        for (i in 0 until steps) {
            val nextGrid = Grid(input.size, input[0].length)
            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    var onCount = 0
                    for (neighbor in grid.getNeighbors(x, y, true)) {
                        onCount += grid.get(neighbor).toInt()
                    }
                    val prev = grid.get(x, y).toInt()
                    val next = if (part == TWO && Pair(x, y) in corners) {
                        1
                    }
                    else if (prev == 1 && onCount != 2 && onCount != 3) {
                        0
                    }
                    else if (prev == 0 && onCount == 3) {
                        1
                    }
                    else {
                        prev
                    }
                    nextGrid.set(x, y, next)
                }
            }
            grid = nextGrid
        }
        return grid.getSum()
    }
}