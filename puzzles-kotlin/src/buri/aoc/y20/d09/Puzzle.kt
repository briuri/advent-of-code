package buri.aoc.y20.d09

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
        assertRun(1038347917, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(137394018, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input.map { it.toLong() }
        var index = 26
        while (index < numbers.size) {
            if (!isValidSum(numbers, index)) {
                break
            }
            index++
        }
        if (part.isOne()) {
            return numbers[index]
        }

        for (startAt in numbers.indices) {
            val rangeSum = getContiguous(numbers, index, startAt)
            if (rangeSum != -1L) {
                return rangeSum
            }
        }
        return -1
    }

    /**
     * Returns true if a number is the sum of 2 other numbers.
     */
    private fun isValidSum(numbers: List<Long>, index: Int): Boolean {
        val target = numbers[index]
        val subset = numbers.subList(index - 25, index)
        for (a in subset) {
            for (b in subset.filter { it != a }) {
                if (a + b == target) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Tries to build a contiguous sum. Returns
     */
    private fun getContiguous(numbers: List<Long>, index: Int, startAt: Int): Long {
        val target = numbers[index]
        var sum = 0L
        var i = startAt
        while (i in numbers.indices) {
            sum += numbers[i]
            if (sum == target) {
                val subset = numbers.subList(startAt, i + 1).sorted()
                return subset.first() + subset.last()
            }
            i++
        }
        return -1
    }
}