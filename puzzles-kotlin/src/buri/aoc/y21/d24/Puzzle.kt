package buri.aoc.y21.d24

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import kotlin.math.pow

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(91897399498995, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(51121176121391, 0, true)
    }

    private val step5Values = listOf(1, 1, 1, 26, 1, 1, 26, 1, 26, 1, 26, 26, 26, 26)
    private val step6Values = listOf(15, 14, 11, -13, 14, 15, -7, 10, -12, 15, -16, -9, -8, -8)
    private val step16Values = listOf(4, 16, 14, 3, 11, 13, 11, 7, 12, 15, 13, 1, 15, 4)
    private val powerValues = mutableListOf<Int>()
    private val maxZValues = mutableListOf<Long>()

    init {
        var power = 7
        for (value in step5Values) {
            powerValues.add(power)
            if (value == 26) {
                power--
            }
        }
        for (value in powerValues) {
            maxZValues.add(26.toDouble().pow(value).toLong())
        }
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = mutableListOf<Long>()
        for (number in getValidNumbers(1, 0)) {
            numbers.add(number.toLong())
        }
        return if (part.isOne()) numbers.max() else numbers.min()
    }

    /**
     * Recursively builds the 14-digit numbers.
     */
    private fun getValidNumbers(digit: Int, zPrevious: Long): List<String> {
        val numbers = mutableListOf<String>()
        if (digit > 14) {
            if (zPrevious == 0L) {
                numbers.add("")
            }
        } else if (zPrevious <= maxZValues[digit - 1]) {
            for (w in 1L..9L) {
                val z = runAluProgram(w, digit, zPrevious)
                for (number in getValidNumbers(digit + 1, z)) {
                    numbers.add("$w$number")
                }
            }
        }
        return numbers
    }

    /**
     * Runs the ALU algorithm.
     */
    private fun runAluProgram(w: Long, digit: Int, zPrevious: Long): Long {
        val x = (zPrevious % 26) + step6Values[digit - 1]
        var z = (zPrevious / step5Values[digit - 1])
        if (w != x) {
            z = z * 26 + (w + step16Values[digit - 1])
        }
        return z
    }
}