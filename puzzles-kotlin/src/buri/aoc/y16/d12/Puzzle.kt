package buri.aoc.y16.d12

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
        assertRun(42, 1)
        assertRun(318020, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(9227674, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        // Reduce assembunny to Fibonacci algorithm
        if (part == TWO) {
            val getIntValue = { line: String -> line.split(" ")[1].toInt() }
            val times = getIntValue(input[2]) + getIntValue(input[5])
            val offset = getIntValue(input[16]) + getIntValue(input[17])
            var a = 1L
            var b = 1L
            for (i in 0 .. times) {
                val sum = a + b
                a = b
                b = sum
            }
            return a + offset
        }
        val registers = mutableMapOf<String, Long>()
        registers["a"] = 0L
        registers["b"] = 0L
        registers["c"] = if (part == ONE) 0L else 1L
        registers["d"] = 0L

        var pointer = 0
        while (pointer in input.indices) {
            val command = input[pointer].split(" ")
            if (command[0] == "cpy") {
                registers[command[2]] = getValue(registers, command[1])
                pointer++
            }
            else if (command[0] == "inc") {
                registers[command[1]] = registers[command[1]]!! + 1
                pointer++
            }
            else if (command[0] == "dec") {
                registers[command[1]] = registers[command[1]]!! - 1
                pointer++
            }
            else if (command[0] == "jnz") {
                val offset = if (getValue(registers, command[1]) != 0L) {
                    command[2].toInt()
                }
                else {
                    1
                }
                pointer += offset
            }
        }
        return registers["a"]!!
    }

    /**
     * Converts an instruction token into a value from a register or a plain numeric value.
     */
    private fun getValue(registers: MutableMap<String, Long>, addressOrValue: String): Long {
        return if (addressOrValue.toIntOrNull() == null) {
            registers[addressOrValue]!!
        }
        else {
            addressOrValue.toLong()
        }
    }
}