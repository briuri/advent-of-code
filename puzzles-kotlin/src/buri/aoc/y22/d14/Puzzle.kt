package buri.aoc.y22.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Bounds2D
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
        assertRun(24, 1)
        assertRun(825, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(93, 1)
        assertRun(26729, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = mutableMapOf<Point2D<Int>, Char>()
        for (line in input) {
            val points = line.split(" -> ")
            for (i in 0 until points.lastIndex) {
                val rawStart = points[i].split(",").map { it.toInt() }
                val start = Point2D(rawStart[0], rawStart[1])
                val rawEnd = points[i + 1].split(",").map { it.toInt() }
                val end = Point2D(rawEnd[0], rawEnd[1])
                addRock(grid, start, end)
            }
        }
        val source = Point2D(500, 0)

        var yMax = Bounds2D(grid.keys).y.last
        if (part.isTwo()) {
            val start = Point2D(source.x - (yMax + 2), yMax + 2)
            val end = Point2D(source.x + (yMax + 2), yMax + 2)
            addRock(grid, start, end)
            yMax += 2
        }

        var hasNewSand = true
        while (hasNewSand) {
            hasNewSand = dropFrom(grid, yMax, source)
        }
        return grid.values.count { it == 'o' }
    }

    /**
     * Returns true if the sand can drop from this point without falling off the grid.
     */
    private fun dropFrom(grid: MutableMap<Point2D<Int>, Char>, yMax: Int, top: Point2D<Int>): Boolean {
        val next = findBottom(grid, yMax, top) ?: return false
        val lowerLeft = Point2D(next.x - 1, next.y + 1)
        val lowerRight = Point2D(next.x + 1, next.y + 1)
        if (lowerLeft !in grid.keys) {
            return dropFrom(grid, yMax, lowerLeft)
        }
        if (lowerRight !in grid.keys) {
            return dropFrom(grid, yMax, lowerRight)
        }
        grid[next] = 'o'
        return true
    }

    /**
     * Finds the last open square under a square.
     */
    private fun findBottom(grid: MutableMap<Point2D<Int>, Char>, yMax: Int, top: Point2D<Int>): Point2D<Int>? {
        var y = top.y
        while (Point2D(top.x, y) !in grid.keys) {
            y++
            if (y > yMax) {
                return null
            }
        }
        return Point2D(top.x, y - 1)
    }

    /**
     * Draws a rock formation.
     */
    private fun addRock(grid: MutableMap<Point2D<Int>, Char>, start: Point2D<Int>, end: Point2D<Int>) {
        if (start.x == end.x) {
            if (start.y <= end.y) {
                for (y in start.y..end.y) {
                    grid[Point2D(start.x, y)] = '#'
                }
            } else {
                for (y in end.y..start.y) {
                    grid[Point2D(start.x, y)] = '#'
                }
            }
        } else {
            if (start.x <= end.x) {
                for (x in start.x..end.x) {
                    grid[Point2D(x, start.y)] = '#'
                }
            } else {
                for (x in end.x..start.x) {
                    grid[Point2D(x, start.y)] = '#'
                }
            }
        }
    }
}