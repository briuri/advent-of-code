package buri.aoc.y19.d15

import buri.aoc.common.*
import buri.aoc.common.position.*
import buri.aoc.common.registers.Computer
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(280, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(400, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid(42, 60, ' ')
        val start = Point2D(grid.width / 2, grid.height / 2)
        grid[start] = 'o'

        val computer = Computer(input[0].extractLongs())
        explore(computer, grid, start)
        var end: Point2D<Int> = start
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                if (grid[x, y] == 'X') {
                    end = Point2D(x, y)
                }
            }
        }

        // Use a pathfinder that avoids walls.
        val pathfinder = Pathfinder { current ->
            grid.getNeighbors(current).filter { grid[it] != '#' }
        }

        return if (part.isOne()) {
            pathfinder.exploreFrom(start).countSteps(start, end)
        } else {
            val cameFrom = pathfinder.exploreFrom(end)
            cameFrom.maxOf { cameFrom.countSteps(end, it.key) }
        }
    }

    /**
     * Depth-first search of the maze.
     */
    private fun explore(computer: Computer, grid: Grid<Char>, start: Point2D<Int>) {
        val neighbors = grid.getNeighbors(start).filter { grid[it] == ' ' }
        for (next in neighbors) {
            computer.input(toMove(start, next).toLong())
            computer.run()
            when (computer.output()) {
                0L -> {
                    grid[next] = '#'
                    continue
                }
                1L -> {
                    grid[next] = '.'
                    explore(computer, grid, next)
                }
                2L -> {
                    grid[next] = 'X'
                }
            }
            computer.input(toMove(next, start).toLong())
            computer.run()
            computer.output()   // Discard output from the reversal.
        }
    }

    /**
     * Translates the direction to get between two (non-diagonal adjacent) points.
     * N=1, S=2, W=3, E=4
     */
    private fun toMove(from: Point2D<Int>, to: Point2D<Int>): Int {
        return if (to.y < from.y) {
            1
        } else if (to.y > from.y) {
            2
        } else if (to.x < from.x) {
            3
        } else {
            4
        }
    }
}