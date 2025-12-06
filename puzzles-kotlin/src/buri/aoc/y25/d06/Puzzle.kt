package buri.aoc.y25.d06

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
        assertRun(4277556, 1)
        assertRun(5346286649122, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3263827, 1)
        assertRun(10389131401929, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val ops = input[input.lastIndex]
        val equations = mutableListOf<RawEquation>()
        val maxLength = input.maxOf { it.length } + 1

        var opIndex = ops.indexOfFirst { !it.isWhitespace() }
        var i = opIndex + 1
        while (i <= maxLength) {
            if (i == maxLength || (i in ops.indices && !ops[i].isWhitespace())) {
                equations.add(RawEquation(i - opIndex, ops[opIndex]))
                opIndex = i
            }
            i++
        }

        val widths = equations.map { it.width }
        for (line in input.dropLast(1)) {
            var j = 0
            for ((index, width) in widths.withIndex()) {
                val end = (j + width).coerceAtMost(line.length)
                val number = line.substring(j, end)
                equations[index].addNumber(number)
                j += width
            }
        }
        return equations.sumOf { it.getResult(part) }
    }
}

data class RawEquation(val width: Int, val operation: Char) {
    val rawNumbers = mutableListOf<String>()

    fun addNumber(rawNumber: String) {
        rawNumbers.add(rawNumber)
    }

    fun getResult(part: Part): Long {
        var result = when (operation) {
            '+' -> 0L
            else -> 1L
        }

        val numbers = if (part.isOne()) {
            rawNumbers
        } else {
            val vertRawNumbers = mutableListOf<String>()
            for (i in width - 2 downTo 0) {
                val builder = StringBuilder()
                for (rawNumber in rawNumbers) {
                    builder.append(rawNumber[i])
                }
                vertRawNumbers.add(builder.toString())
            }
            vertRawNumbers
        }

        for (number in numbers.map { it.trim().toLong() }) {
            when (operation) {
                '+' -> result += number
                else -> result *= number
            }
        }
        return result
    }
}