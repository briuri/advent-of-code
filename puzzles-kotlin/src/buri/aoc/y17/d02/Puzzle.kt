package buri.aoc.y17.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(18, 1)
        assertRun(47623, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(9, 2)
        assertRun(312, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0
        var divisionSum = 0
        for (line in input) {
            val tokens = line.replace("\\s+".toRegex(), " ").split(" ")
            val row = tokens.map { it.toInt() }.sorted()
            sum += row.max() - row.min()

            for (num1 in row.reversed()) {
                for (num2 in row.filter { it != num1 }) {
                    if (num1 % num2 == 0) {
                        divisionSum += num1 / num2
                    }
                }
            }
        }
        return if (part == ONE) sum else divisionSum
    }
}