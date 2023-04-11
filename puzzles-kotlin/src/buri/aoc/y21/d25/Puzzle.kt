package buri.aoc.y21.d25

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
        assertRun(58, 1)
        assertRun(400, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var grid = Grid.fromCharInput(input)
        var steps = 0
        do {
            steps++
            var moves = 0

            var nextGrid = grid.copy()
            for (east in grid.filter { it == '>' }) {
                val x = if (east.x == grid.width - 1) 0 else (east.x + 1)
                val next = Point2D(x, east.y)
                if (grid[next] == '.') {
                    moves++
                    nextGrid[east] = '.'
                    nextGrid[next] = '>'
                }
            }
            grid = nextGrid

            nextGrid = grid.copy()
            for (south in grid.filter { it == 'v' }) {
                val y = if (south.y == grid.height - 1) 0 else (south.y + 1)
                val next = Point2D(south.x, y)
                if (grid[next] == '.') {
                    moves++
                    nextGrid[south] = '.'
                    nextGrid[next] = 'v'
                }
            }
            grid = nextGrid
        } while (moves > 0)
        return steps
    }
}