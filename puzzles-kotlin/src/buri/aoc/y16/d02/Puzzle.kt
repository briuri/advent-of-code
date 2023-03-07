package buri.aoc.y16.d02

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
        assertRun("1985", 1)
        assertRun("74921", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("5DB3", 1)
        assertRun("A6B35", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val grid1 = Grid(3, 3, '0')
        grid1[0, 0] = '1'
        grid1[1, 0] = '2'
        grid1[2, 0] = '3'
        grid1[0, 1] = '4'
        grid1[1, 1] = '5'
        grid1[2, 1] = '6'
        grid1[0, 2] = '7'
        grid1[1, 2] = '8'
        grid1[2, 2] = '9'

        val grid2 = Grid(5, 5, '0')
        grid2[2, 0] = '1'
        grid2[1, 1] = '2'
        grid2[2, 1] = '3'
        grid2[3, 1] = '4'
        grid2[0, 2] = '5'
        grid2[1, 2] = '6'
        grid2[2, 2] = '7'
        grid2[3, 2] = '8'
        grid2[4, 2] = '9'
        grid2[1, 3] = 'A'
        grid2[2, 3] = 'B'
        grid2[3, 3] = 'C'
        grid2[2, 4] = 'D'

        val grid = if (part.isOne()) grid1 else grid2
        val code = StringBuilder()
        var key = if (part.isOne()) Point2D(1, 1) else Point2D(0, 2)
        for (line in input) {
            for (value in line) {
                val dx = when (value) {
                    'L' -> -1
                    'R' -> 1
                    else -> 0
                }
                val dy = when (value) {
                    'U' -> -1
                    'D' -> 1
                    else -> 0
                }
                val nextKey = key.copy(x = key.x + dx, y = key.y + dy)
                if (isInBounds(part, grid, nextKey)) {
                    key = nextKey
                }
            }
            code.append(grid[key])
        }
        return code.toString()
    }

    /**
     * Checks if a point is in bounds, using different rules for Part 2.
     */
    private fun isInBounds(part: Part, grid: Grid<Char>, point: Point2D<Int>): Boolean {
        return grid.isInBounds(point) && (part.isOne() || grid[point] != '0')
    }
}