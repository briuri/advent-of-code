package buri.aoc.y25.d03

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
        assertRun(357, 1)
        assertRun(17031, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3121910778619, 1)
        assertRun(168575096286051, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0L
        val needed = if (part.isOne()) {
            2
        } else {
            12
        }
        for (line in input) {
            val batteries = line.toCharArray().toList().map { it.digitToInt() }
            sum += getMax(batteries, needed)
        }
        return sum
    }

    fun getMax(batteries: List<Int>, needed: Int): Long {
        val digits = mutableListOf<Int>()
        var index = 0
        for (i in 0..<needed) {
            val searchRange = (index until batteries.size - needed + i + 1)
            index = searchRange.maxByOrNull { batteries[it] } ?: index
            digits.add(batteries[index])
            index++
        }
        return digits.joinToString("").toLong()
    }
}