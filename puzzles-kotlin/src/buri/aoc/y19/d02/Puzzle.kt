package buri.aoc.y19.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4023471, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8051, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val intCode = IntCode(input[0].extractInts())
        if (part.isOne()) {
            return intCode.run(12, 2)
        }
        for (noun in 0..99) {
            for (verb in 0 .. 99) {
                if (intCode.run(noun, verb) == 19690720) {
                    return (100 * noun + verb)
                }
            }
        }
        return -1
    }
}

class IntCode(private val instructions: List<Int>) {

    /**
     * Runs the IntCode
     */
    fun run(noun: Int, verb: Int): Int {
        val code = instructions.toMutableList()
        code[1] = noun
        code[2] = verb

        var pointer = 0
        while (code[pointer] != 99) {
            val in1 = code[pointer + 1]
            val in2 = code[pointer + 2]
            val out = code[pointer + 3]
            code[out] = when (code[pointer]) {
                1 -> code[in1] + code[in2]
                2 -> code[in1] * code[in2]
                else -> continue
            }
            pointer += 4
        }
        return code[0]
    }
}