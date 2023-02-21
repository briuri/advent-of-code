package buri.aoc.y17.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.registers.NamedRegisters
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(6061, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(6696, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val registers = NamedRegisters()
        var maxAnytime = 0L
        for (line in input) {
            val tokens = line.split(" ")
            if (isConditionTrue(registers, tokens)) {
                val value = registers[tokens[0]]
                var amount = tokens[2].toLong()
                if (tokens[1] == "dec") {
                    amount *= -1
                }
                registers[tokens[0]] = value + amount
                maxAnytime = maxAnytime.coerceAtLeast(value + amount)
            }
        }
        return if (part == ONE) registers.max() else maxAnytime
    }

    /**
     * Checks if the condition is true.
     */
    private fun isConditionTrue(registers: NamedRegisters, tokens: List<String>): Boolean {
        val value1 = registers[tokens[4]]
        val value2 = tokens[6].toLong()
        return when (tokens[5]) {
            "<" -> value1 < value2
            ">" -> value1 > value2
            "==" -> value1 == value2
            "!=" -> value1 != value2
            "<=" -> value1 <= value2
            else -> value1 >= value2
        }
    }
}