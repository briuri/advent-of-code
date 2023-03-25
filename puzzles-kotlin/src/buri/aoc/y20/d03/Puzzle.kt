package buri.aoc.y20.d03

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
        assertRun(242, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2265549792, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromInput(input, '.')
        val deltas = mutableListOf<Point2D<Int>>()
        deltas.add(Point2D(3, 1))
        if (part.isTwo()) {
            deltas.add(Point2D(1, 1))
            deltas.add(Point2D(5, 1))
            deltas.add(Point2D(7, 1))
            deltas.add(Point2D(1, 2))
        }

        var treeProduct = 1L
        for (delta in deltas) {
            var count = 0
            var x = 0
            var y = 0
            while (y < grid.height) {
                x = (x + delta.x) % grid.width
                y += delta.y
                if (y < grid.height && grid[x, y] == '#') {
                    count++
                }
            }
            treeProduct *= count
        }
        return treeProduct
    }
}