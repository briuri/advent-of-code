package buri.aoc.y21.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(45, 1)
        assertRun(8911, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(112, 1)
        assertRun(4748, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input[0].extractInts()
        val xBounds = numbers[0]..numbers[1]
        val yBounds = numbers[2]..numbers[3]
        val maxYs = mutableListOf<Int>()

        for (vx0 in 1..xBounds.last) {
            for (vy0 in yBounds.first..yBounds.first.absoluteValue) {
                var probe = Point2D(0, 0)
                var velocity = Point2D(vx0, vy0)
                var maxY = 0
                while (true) {
                    probe = probe.copy(x = probe.x + velocity.x, y = probe.y + velocity.y)
                    maxY = maxY.coerceAtLeast(probe.y)
                    val dvx = if (velocity.x < 0) {
                        1
                    } else if (velocity.x > 0) {
                        -1
                    } else {
                        0
                    }
                    velocity = velocity.copy(x = velocity.x + dvx, y = velocity.y - 1)

                    if (probe.x in xBounds && probe.y in yBounds) {
                        maxYs.add(maxY)
                        break
                    }
                    if (probe.x > xBounds.last || probe.y < yBounds.first) {
                        break
                    }
                }
            }
        }
        return if (part.isOne()) maxYs.max() else maxYs.size
    }
}