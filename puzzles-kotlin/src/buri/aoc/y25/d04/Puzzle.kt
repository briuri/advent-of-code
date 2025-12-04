package buri.aoc.y25.d04

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
        assertRun(13, 1)
        assertRun(1502, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(43, 1)
        assertRun(9083, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        var count = 0
        while (true) {
            val removals = mutableSetOf<Point2D<Int>>()
            for (y in grid.yRange) {
                for (x in grid.xRange) {
                    val point = Point2D(x, y)
                    if (grid[point] == '@') {
                        val neighbors = grid.getNeighbors(point, true)
                        if (neighbors.filter { grid[it] == '@' }.size < 4) {
                            removals.add(point)
                            count++
                        }
                    }
                }
            }
            if (part.isOne() || (part.isTwo() && removals.isEmpty())) {
                break
            }
            for (removed in removals) {
                grid[removed] = '.'
            }
        }
        return count
    }
}