package buri.aoc.y20.d11

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
        assertRun(37, 1)
        assertRun(2270, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(26, 1)
        assertRun(2042, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val limit = if (part.isOne()) 4 else 5
        var grid = Grid.fromInput(input, ' ')
        val visited = mutableSetOf<String>()
        while (grid.toString() !in visited) {
            visited.add(grid.toString())
            val newGrid = grid.copy()
            for (y in 0 until grid.height) {
                for (x in 0 until grid.width) {
                    val point = Point2D(x, y)
                    val occupiedNeighbors = countOccupiedNeighbors(part, grid, point)
                    when (grid[point]) {
                        'L' -> if (occupiedNeighbors == 0) {
                            newGrid[point] = '#'
                        }
                        '#' -> if (occupiedNeighbors >= limit) {
                            newGrid[point] = 'L'
                        }
                    }
                }
            }
            grid = newGrid
        }
        return grid.count { it == '#' }
    }

    /**
     * Counts the number of occupied seats adjacent (or in view of) the center.
     */
    private fun countOccupiedNeighbors(part: Part, grid: Grid<Char>, point: Point2D<Int>): Int {
        var neighbors = grid.getNeighbors(point, true).toMutableList()
        if (part.isTwo()) {
            val newNeighbors = mutableListOf<Point2D<Int>>()
            for (neighbor in neighbors) {
                val dx = neighbor.x - point.x
                val dy = neighbor.y - point.y

                var next = neighbor
                // Extend line of sight until we find a seat.
                while (grid.isInBounds(next) && grid[next] !in "#L") {
                    next = next.copy(x = next.x + dx, y = next.y + dy)
                }
                if (grid.isInBounds(next)) {
                    newNeighbors.add(next)
                }
            }
            neighbors = newNeighbors
        }
        return neighbors.count { grid[it] == '#'}
    }
}