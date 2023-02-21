package buri.aoc.y16.d12

import buri.aoc.common.registers.Assembunny
import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
            val offset = getIntValue(input[16]) * getIntValue(input[17])
            var a = 1L
            var b = 1L
            for (i in 0..times) {
                val sum = a + b
                a = b
                b = sum
            }
            return a + offset
        }
        val registers = Assembunny(input)
        registers.run()
        return registers["a"]
    }
}