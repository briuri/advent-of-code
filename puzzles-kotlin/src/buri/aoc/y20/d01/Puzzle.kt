package buri.aoc.y20.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(138379, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(85491920, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input.map { it.toInt() }
        if (part.isOne()) {
            for (a in numbers) {
                for (b in numbers.filter { it != a }) {
                    if (a + b == 2020) {
                        return a * b
                    }
                }
            }
        }
        for (a in numbers) {
            for (b in numbers.filter { it != a }) {
                for (c in numbers.filter { it != a && it != b}) {
                    if (a + b + c == 2020) {
                        return a * b * c
                    }
                }
            }
        }
        return -1
    }
}