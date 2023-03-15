package buri.aoc.y19.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.absoluteValue

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("63794407", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("77247538", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val offset = input[0].substring(0, 7).toInt()
        val originalNumber = input[0].toCharArray().map { it.digitToInt() }.toIntArray()

        if (part.isOne()) {
            var number = originalNumber
            val pattern = getPattern(input[0].length)
            for (phase in 0 until 100) {
                val nextNumber = IntArray(number.size) { 0 }
                for (position in number.indices) {
                    var sum = 0
                    for ((index, value) in number.withIndex()) {
                        sum += value * pattern[position][index]
                    }
                    nextNumber[position] = (sum % 10).absoluteValue
                }
                number = nextNumber
            }
            return number.slice(0..7).joinToString("")
        }

        var number = IntArray(originalNumber.size * 10000) { 0 }
        for (i in 1 until 10000) {
            originalNumber.copyInto(number, originalNumber.size * i)
        }
        for (phase in 0 until 100) {
            val nextNumber = IntArray(number.size) { 0 }
            var partialSum = 0
            for (j in number.size - 1 downTo offset) {
                partialSum += number[j]
                nextNumber[j] = (partialSum % 10).absoluteValue
            }
            number = nextNumber
        }
        return number.slice(offset..(offset + 7)).joinToString("")
    }

    /**
     * Builds the pattern to apply to the number.
     */
    private fun getPattern(length: Int): List<List<Int>> {
        val list = mutableListOf<MutableList<Int>>()
        for (i in 1..length) {
            val positionList = mutableListOf<Int>()
            val basePattern = ArrayDeque<Int>()
            basePattern.addFirst(-1)
            basePattern.addFirst(0)
            basePattern.addFirst(1)
            basePattern.addFirst(0)
            while (positionList.size < length + 1) {
                val current = basePattern.removeFirst()
                repeat(i) {
                    positionList.add(current)
                }
                basePattern.addLast(current)
            }
            list.add(positionList.subList(1, length + 1))
        }
        return list
    }
}