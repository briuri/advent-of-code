package buri.aoc.y22.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Direction.*
import buri.aoc.common.position.Point2D
import buri.aoc.common.position.Point3D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(18, 1)
        assertRun(290, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(54, 1)
        assertRun(842, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val xBounds = 1..input[0].length - 2
        val yBounds = 1..input.size - 2
        val blizzards = Blizzards(850, xBounds, yBounds)
        for (y in yBounds) {
            for (x in xBounds) {
                if (input[y][x] != '.') {
                    val facing = Direction.from(input[y][x])
                    blizzards.add(x, y, facing)
                }
            }
        }

        var start = Point3D(1, 0, 0)
        var end = Point2D(xBounds.last, yBounds.last + 1)
        var fewest = getFewestMinutes(blizzards, start, end)
        if (part.isTwo()) {
            start = Point3D(xBounds.last, yBounds.last + 1, fewest)
            end = Point2D(1, 0)
            fewest += getFewestMinutes(blizzards, start, end)
            start = Point3D(1, 0, fewest)
            end = Point2D(xBounds.last, yBounds.last + 1)
            fewest += getFewestMinutes(blizzards, start, end)
        }
        return fewest
    }

    /**
     * Calculates the fewest number of minutes needed to get from one spot to another.
     */
    private fun getFewestMinutes(blizzards: Blizzards, start: Point3D<Int>, end: Point2D<Int>): Int {
        var frontier = mutableListOf<Point3D<Int>>()
        frontier.add(start)
        while (frontier.isNotEmpty()) {
            val nextFrontier = mutableSetOf<Point3D<Int>>()
            for (current in frontier) {
                if (current.z > blizzards.maxMinutes) {
                    break
                }
                for (next in getNextSteps(blizzards, start, end, current)) {
                    nextFrontier.add(next)
                    if (next.x == end.x && next.y == end.y) {
                        return next.z - start.z
                    }
                }
            }
            frontier = nextFrontier.toMutableList()
        }
        return -1
    }

    private fun getNextSteps(
        blizzards: Blizzards, start: Point3D<Int>, end: Point2D<Int>, current: Point3D<Int>
    ): List<Point3D<Int>> {
        val next = mutableListOf<Point3D<Int>>()
        next.add(Point3D(current.x, current.y, current.z + 1))
        next.add(Point3D(current.x - 1, current.y, current.z + 1))
        next.add(Point3D(current.x + 1, current.y, current.z + 1))
        next.add(Point3D(current.x, current.y - 1, current.z + 1))
        next.add(Point3D(current.x, current.y + 1, current.z + 1))
        val atStart = (current.x == start.x && current.y == start.y)

        val invalid = mutableListOf<Point3D<Int>>()
        for (point in next) {
            val nextIsStart = (point.x == start.x && point.y == start.y)
            // Allowed to wait at start or reach exit.
            if ((atStart && nextIsStart) || (point.x == end.x && point.y == end.y)) {
                continue
            }
            // Remove out of bounds or blizzards.
            if (point.x !in blizzards.xBounds || point.y !in blizzards.yBounds || point in blizzards.blizzards) {
                invalid.add(point)
            }
        }
        next.removeAll(invalid)
        return next
    }
}

class Blizzards(val maxMinutes: Int, val xBounds: IntRange, val yBounds: IntRange) {
    val blizzards = mutableSetOf<Point3D<Int>>()

    /**
     * Adds a blizzard and simulates its movement over the minutes. (z = time)
     */
    fun add(x: Int, y: Int, facing: Direction) {
        var blizzard = Point3D(x, y, 0)
        blizzards.add(blizzard)
        for (minute in 1 until maxMinutes) {
            var nextX = blizzard.x
            var nextY = blizzard.y
            when (facing) {
                NORTH -> {
                    nextY -= 1
                    if (nextY !in yBounds) {
                        nextY = yBounds.last
                    }
                }

                SOUTH -> {
                    nextY += 1
                    if (nextY !in yBounds) {
                        nextY = yBounds.first
                    }
                }

                WEST -> {
                    nextX -= 1
                    if (nextX !in xBounds) {
                        nextX = xBounds.last
                    }
                }

                EAST -> {
                    nextX += 1
                    if (nextX !in xBounds) {
                        nextX = xBounds.first
                    }
                }
            }
            blizzard = blizzard.copy(nextX, nextY, blizzard.z + 1)
            blizzards.add(blizzard)
        }
    }
}