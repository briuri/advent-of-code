package buri.aoc.y21.d09

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.extractInts
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
        assertRun(15, 1)
        assertRun(448, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1134, 1)
        assertRun(1417248, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromInput(input, '0')
        val lowPoints = mutableListOf<Point2D<Int>>()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val point = Point2D(x, y)
                if (grid.getNeighbors(point).all { grid[it].digitToInt() > grid[point].digitToInt() }) {
                    lowPoints.add(point)
                }
            }
        }

        if (part.isOne()) {
            return lowPoints.sumOf { grid[it].digitToInt() + 1 }
        }

        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            grid.getNeighbors(current).filter { grid[it] != '9' }
        }
        val sizes = mutableListOf<Int>()
        for (point in lowPoints) {
            val cameFrom = pathfinder.exploreFrom(point)
            sizes.add(cameFrom.keys.size)
        }
        val biggest = sizes.sorted().reversed().take(3)
        return biggest[0] * biggest[1] * biggest[2]
    }
}