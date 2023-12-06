package buri.aoc.y23.d03

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Point2D
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4361, 1)
        assertRun(553825, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(467835, 1)
        assertRun(93994191, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val visitedNumbers = mutableSetOf<Point2D<Int>>()
        val symbolPoints = grid.filter {
            if (part.isOne()) {
                !it.isDigit() && it != '.'
            } else {
                it == '*'
            }
        }

        var sum = 0L
        // Find all numbers touching this symbol that haven't already been visited.
        for (point in symbolPoints) {
            val numbers = mutableSetOf<Int>()
            for (neighbor in grid.getNeighbors(point, true)) {
                if (grid[neighbor].isDigit() && neighbor !in visitedNumbers) {
                    val number = getNumber(grid, visitedNumbers, neighbor)
                    numbers.add(number)
                }
            }

            // Part One: Any unique number touching this symbol counts.
            if (part.isOne()) {
                sum += numbers.sum()
            }
            // Part Two: Gears have exactly two numbers touching.
            else if (numbers.size == 2) {
                sum += numbers.product()
            }
        }
        return sum
    }

    /**
     * Starts from a point and explores horizontally to build a number.
     */
    private fun getNumber(grid: Grid<Char>, visited: MutableSet<Point2D<Int>>, point: Point2D<Int>): Int {
        var startX = point.x
        var endX = point.x
        while (startX > 0 && grid[startX - 1, point.y].isDigit()) {
            startX--
        }
        while (endX < grid.yRange.last && grid[endX + 1, point.y].isDigit()) {
            endX++
        }

        val points = mutableListOf<Point2D<Int>>()
        for (numX in startX..endX) {
            points.add(Point2D(numX, point.y))
        }
        visited.addAll(points)
        return points.map { grid[it] }.joinToString("").toInt()
    }
}