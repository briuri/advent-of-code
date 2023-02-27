package buri.aoc.y18.d19

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.registers.IndexedRegisters
import org.junit.Test
import kotlin.math.sqrt

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(6, 1)
        assertRun(2047, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(24033240, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val registers = IndexedRegisters()
        if (part.isTwo()) {
            registers[0] = 1L
        }

        val instructions = input.drop(1)
        val ipRegister = input[0].extractInts()[0]
        if (part.isOne()) {
            var pointer = 0
            while (pointer in instructions.indices) {
                val command = instructions[pointer].split(" ")
                registers[ipRegister] = pointer.toLong()
                registers.run(command[0], command)
                pointer = registers[ipRegister].toInt() + 1
            }
            return registers[0]
        }

        // For part two, convert the looping code into an algorithm for factor sums.
        var sum = 0
        val target = 10551424
        for (i in 1..sqrt(target.toDouble()).toInt()) {
            if (target % i == 0) {
                sum += i
                if (i != target / i) {
                    sum += (target / i)
                }
            }
        }
        return sum
    }
}