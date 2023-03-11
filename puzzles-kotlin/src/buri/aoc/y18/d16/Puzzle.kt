package buri.aoc.y18.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.extractLongs
import buri.aoc.common.registers.IndexedRegisters
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(607, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(577, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var registers = IndexedRegisters()
        val opcodes = setOf(
            "addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori", "setr", "seti",
            "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr"
        )
        val assignedCodes = mutableMapOf<Int, String>()

        var threeCount = 0
        var index = 0
        // Go through samples 1x in Part ONE. Repeat in Part TWO until all opcodes have been deciphered.
        while (assignedCodes.size != opcodes.size) {
            index = 0
            while (input[index].startsWith("Before")) {
                val remainingCodes = opcodes.filter { it !in assignedCodes.values }
                val matchingCodes = getCodesFor(registers, remainingCodes, input.subList(index, index + 3))
                if (matchingCodes.size >= 3) {
                    threeCount++
                }
                if (part.isTwo() && matchingCodes.size == 1) {
                    val number = input[index + 1].extractInts()[0]
                    assignedCodes[number] = matchingCodes.first()
                }
                index += 4
            }
            if (part.isOne()) {
                return threeCount
            }
        }

        // With all opcodes deciphered, run the program.
        index += 2
        registers = IndexedRegisters()
        while (index in input.indices) {
            val inputs = input[index].split(" ")
            registers.run(assignedCodes[inputs[0].toInt()]!!, inputs)
            index++
        }
        return registers[0]
    }

    /**
     * Returns the opcodes this sample works for.
     */
    private fun getCodesFor(registers: IndexedRegisters, opcodes: List<String>, sample: List<String>): List<String> {
        val initial = sample[0].extractInts()
        val inputs = sample[1].split(" ")
        val expected = sample[2].extractLongs()

        val matchingCodes = mutableListOf<String>()
        for (code in opcodes) {
            for (i in initial.indices) {
                registers[i] = initial[i].toLong()
            }
            registers.run(code, inputs)
            val actual = mutableListOf<Long>()
            for (i in expected.indices) {
                actual.add(registers[i])
            }
            if (actual == expected) {
                matchingCodes.add(code)
            }
        }
        return matchingCodes
    }
}