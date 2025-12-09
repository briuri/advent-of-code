package buri.aoc.y25.d09

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Point2D
import org.junit.Test
import kotlin.math.abs

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(50, 1)
        assertRun(4776487744, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(24, 1)
        assertRun(1560299548, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val corners = mutableListOf<Point2D<Int>>()
        for (line in input) {
            val numbers = line.extractInts()
            corners.add(Point2D(numbers[0], numbers[1]))
        }

        val rectangles = mutableListOf<Pair<Point2D<Int>, Point2D<Int>>>()
        for (a in corners) {
            for (b in corners.filter { it != a }) {
                rectangles.add(Pair(a, b))
            }
        }

        if (part.isOne()) {
            return rectangles.maxOf { it.getArea() }
        }

        // Part Two
        var maxArea = 0L
        for (oppositeCorners in rectangles) {
            val (a, b) = oppositeCorners
            val ab = Point2D(b.x, a.y)
            val ba = Point2D(a.x, b.y)
            val rectangle = listOf(a, ab, b, ba)

            var isEnclosed = true
            for (i in corners.indices) {
                val endA = corners[i]
                val endB = corners[(i + 1) % corners.size]
                val minX = rectangle.minOf { it.x }
                val maxX = rectangle.maxOf { it.x }
                val minY = rectangle.minOf { it.y }
                val maxY = rectangle.maxOf { it.y }
                val hasNoIntersections = ((endA.y >= maxY && endB.y >= maxY) ||
                        (endA.y <= minY && endB.y <= minY) ||
                        (endA.x >= maxX && endB.x >= maxX) ||
                        (endA.x <= minX && endB.x <= minX))
                isEnclosed = isEnclosed && hasNoIntersections
            }
            if (isEnclosed) {
                maxArea = maxArea.coerceAtLeast(oppositeCorners.getArea())
            }
        }
        return maxArea
    }

    fun Pair<Point2D<Int>, Point2D<Int>>.getArea(): Long {
        val x = abs(this.second.x - this.first.x) + 1L
        val y = abs(this.second.y - this.first.y) + 1L
        return x * y
    }
}
