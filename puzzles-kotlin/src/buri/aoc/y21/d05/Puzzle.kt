package buri.aoc.y21.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(5, 1)
        assertRun(5197, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(12, 1)
        assertRun(18605, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val vents = mutableMapOf<Point2D<Int>, Int>()
        for (line in input) {
            val numbers = line.extractInts(false)
            val start = Point2D(numbers[0], numbers[1])
            val end = Point2D(numbers[2], numbers[3])
            val dx = getDelta(numbers[2], numbers[0])
            val dy = getDelta(numbers[3], numbers[1])
            // Ignore diagonal lines.
            if (part.isOne() && dx != 0 && dy != 0) {
                continue
            }

            var y = start.y
            var x = start.x
            do {
                val point = Point2D(x, y)
                vents.putIfAbsent(point, 0)
                vents[point] = vents[point]!! + 1
                x += dx
                y += dy
            } while (point != end)
        }
        return vents.count { it.value > 1 }
    }

    /**
     * Calculates a slope for two numbers.
     */
    private fun getDelta(num1: Int, num2: Int): Int {
        return if (num1 > num2) {
            1
        } else if (num1 == num2) {
            0
        } else {
            -1
        }
    }
}