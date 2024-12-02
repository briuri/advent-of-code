package buri.aoc.y24.d01

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(11, 1)
        assertRun(2815556, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(31, 1)
        assertRun(23927637, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        for (line in input) {
            val numbers = line.extractInts()
            left.add(numbers[0])
            right.add(numbers[1])
        }
        if (part.isOne()) {
            left.sort()
            right.sort()
            return left.zip(right).sumOf { abs(it.first - it.second) }
        }

        // Part TWO
        var score = 0
        for (i in left.indices) {
            val count = right.filter { it == left[i] }.size
            score += (left[i] * count)
        }
        return score
    }
}