package buri.aoc.y18.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.registers.NamedRegisters
import org.junit.Test
import org.junit.jupiter.api.Named

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
        var registers = NamedRegisters()
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
        registers = NamedRegisters()
        while (index in input.indices) {
            val inputs = input[index].split(" ")
            run(registers, assignedCodes[inputs[0].toInt()]!!, inputs)
            index++
        }
        return registers["0"]
    }

    /**
     * Returns the opcodes this sample works for.
     */
    private fun getCodesFor(registers: NamedRegisters, opcodes: List<String>, sample: List<String>): List<String> {
        val initial = sample[0].extractInts()
        val inputs = sample[1].split(" ")
        val expected = sample[2].extractInts().map { it.toLong() }

        val matchingCodes = mutableListOf<String>()
        for (code in opcodes) {
            for (i in initial.indices) {
                registers[i.toString()] = initial[i].toLong()
            }
            run(registers, code, inputs)
            val actual = mutableListOf<Long>()
            for (i in expected.indices) {
                actual.add(registers[i.toString()])
            }
            if (actual == expected) {
                matchingCodes.add(code)
            }
        }
        return matchingCodes
    }

    /**
     * Runs a command using the provided input numbers.
     */
    private fun run(registers: NamedRegisters, code: String, inputs: List<String>) {
        val a = inputs[1]
        val b = inputs[2]
        val c = inputs[3]
        when (code) {
            "addr" -> registers[c] = registers[a] + registers[b]
            "addi" -> registers[c] = registers[a] + b.toLong()
            "mulr" -> registers[c] = registers[a] * registers[b]
            "muli" -> registers[c] = registers[a] * b.toLong()
            "banr" -> registers[c] = registers[a] and registers[b]
            "bani" -> registers[c] = registers[a] and b.toLong()
            "borr" -> registers[c] = registers[a] or registers[b]
            "bori" -> registers[c] = registers[a] or b.toLong()
            "setr" -> registers[c] = registers[a]
            "seti" -> registers[c] = a.toLong()
            "gtir" -> registers[c] = if (a.toLong() > registers[b]) 1 else 0
            "gtri" -> registers[c] = if (registers[a] > b.toLong()) 1 else 0
            "gtrr" -> registers[c] = if (registers[a] > registers[b]) 1 else 0
            "eqir" -> registers[c] = if (a.toLong() == registers[b]) 1 else 0
            "eqri" -> registers[c] = if (registers[a] == b.toLong()) 1 else 0
            "eqrr" -> registers[c] = if (registers[a] == registers[b]) 1 else 0
        }
    }
}