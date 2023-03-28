package buri.aoc.y21.d03

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
        assertRun(198, 1)
        assertRun(1071734, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(230, 1)
        assertRun(6124992, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val gamma = StringBuilder()
        val epsilon = StringBuilder()
        if (part.isOne()) {
            for (i in input[0].indices) {
                val mostCommon = getMostCommonBit(input, i)
                val leastCommon = if (mostCommon == 1) 0 else 1
                gamma.append(mostCommon)
                epsilon.append(leastCommon)
            }
            return gamma.toString().toInt(2) * epsilon.toString().toInt(2)
        }

        val oxygen = getRating(input, true)
        val carbon = getRating(input, false)
        return oxygen.toInt(2) * carbon.toInt(2)
    }

    /**
     * Returns the least/most common bit in a position in a list of strings.
     */
    private fun getMostCommonBit(list: List<String>, index: Int): Int {
        val oneCount = list.count { it[index] == '1' }
        val zeroCount = list.size - oneCount
        return if (oneCount >= zeroCount) 1 else 0
    }

    /**
     * Calculates an Oxygen or Carbon Dioxide rating
     */
    private fun getRating(input: List<String>, isOxygen: Boolean = true): String {
        val remaining = mutableListOf<String>()
        remaining.addAll(input)
        for (i in input[0].indices) {
            val mostCommon = getMostCommonBit(remaining, i)
            val leastCommon = if (mostCommon == 1) 0 else 1
            val unwanted = if (isOxygen) leastCommon else mostCommon
            remaining.removeIf { it[i].digitToInt() == unwanted }
            if (remaining.size == 1) {
                return remaining.first()
            }
        }
        return ""
    }
}