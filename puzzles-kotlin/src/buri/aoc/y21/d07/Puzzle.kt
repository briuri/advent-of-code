package buri.aoc.y21.d07

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(37, 1)
        assertRun(342534, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(168, 1)
        assertRun(94004208, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input[0].extractInts().sorted()
        val minBound = numbers[numbers.size / 4]
        val maxBound = numbers[numbers.size * 3 / 4]

        var minFuel = Int.MAX_VALUE
        for (move in minBound..maxBound) {
            var fuel = 0
            for (number in numbers) {
                val distance = (number - move).absoluteValue
                if (part.isOne()) {
                    fuel += distance
                } else {
                    for (i in 1..distance) {
                        fuel += i
                    }
                }
            }
            minFuel = minFuel.coerceAtMost(fuel)
        }
        return minFuel
    }
}