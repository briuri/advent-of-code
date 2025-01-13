package buri.aoc.y23.d18

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point2D
import org.junit.Test
import kotlin.math.absoluteValue

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(62, 1)
        assertRun(62500, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(952408144115, 1)
        assertRun(122109860712709, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val vertices = mutableListOf<Point2D<Long>>()
        vertices.add(Point2D(0L, 0L))
        var edgePoints = 0L
        for (line in input) {
            val tokens = line.split(" ")
            val hex = tokens[2].drop(2).dropLast(1)
            val previous = vertices.last()
            val length = if (part.isOne()) tokens[1].toLong() else hex.dropLast(1).toLong(16)
            edgePoints += length
            val direction = if (part.isOne()) tokens[0].first() else hex.last()
            val next = when (direction) {
                '0', 'R' -> {
                    Point2D(previous.x + length, previous.y)
                }

                '1', 'D' -> {
                    Point2D(previous.x, previous.y + length)
                }

                '2', 'L' -> {
                    Point2D(previous.x - length, previous.y)
                }
                // 3 or U
                else -> {
                    Point2D(previous.x, previous.y - length)
                }
            }
            vertices.add(next)
        }

        // Find the area of the polygon first.
        // https://www.mathsisfun.com/geometry/area-irregular-polygons.html
        var innerArea = 0L
        for (i in 0 until vertices.lastIndex) {
            innerArea += (vertices[i].x * vertices[i + 1].y) - (vertices[i].y * vertices[i + 1].x)
        }
        innerArea = (innerArea / 2).absoluteValue

        // Then convert the area into the number of interior points.
        // https://en.wikipedia.org/wiki/Pick%27s_theorem
        // A = i + (b / 2) - 1
        // i = A - (b / 2) + 1
        val innerPoints = innerArea - (edgePoints / 2) + 1
        return innerPoints + edgePoints
    }
}