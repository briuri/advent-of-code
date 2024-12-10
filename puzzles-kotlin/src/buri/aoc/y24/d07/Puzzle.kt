package buri.aoc.y24.d07

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(3749, 1)
        assertRun(1611660863222, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(11387, 1)
        assertRun(945341732469724, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0L
        for (line in input) {
            val numbers = line.extractLongs()
            val out = numbers[0]
            if (out in getPossibleAnswers(part, numbers.drop(1))) {
                sum += out
            }
        }
        return sum
    }

    /**
     * Recursively tries all possibilities using available operands. Works backwards from end so left-to-right
     * precedence is enforced.
     */
    private fun getPossibleAnswers(part: Part, values: List<Long>): List<Long> {
        if (values.size == 1) {
            return values
        }
        val answers = mutableListOf<Long>()
        val last = values.last()
        for (value in getPossibleAnswers(part, values.dropLast(1))) {
            answers.add(value + last)
            answers.add(value * last)
            if (part.isTwo()) {
                answers.add("$value$last".toLong())
            }
        }
        return answers
    }
}