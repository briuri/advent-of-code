package buri.aoc.y18.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(202209, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(11777564, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part.isOne()) {
            val registers = IndexedRegisters()
            val instructions = input.drop(1)
            val ipRegister = input[0].extractInts()[0]

            var pointer = 0
            while (pointer in instructions.indices) {
                val command = instructions[pointer].split(" ")
                registers[ipRegister] = pointer.toLong()
                registers.run(command[0], command)
                pointer = registers[ipRegister].toInt() + 1
                // Return value right before we jump to the "eqrr" line for register 5.
                if (pointer == 28) {
                    return registers[5]
                }
            }
        }

        // Convert to pseudocode and find cycle.
        var c = 0
        val repeats = mutableListOf<Int>()
        while (true) {
            var a = c or 65536
            c = 10828530
            while (true) {
                c = (c + (a and 255) and 16777215) * 65899 and 16777215
                if (256 > a) {
                    if (c !in repeats) {
                        repeats.add(c)
                        break
                    } else {
                        return repeats[repeats.size - 1]
                    }
                } else {
                    a /= 256
                }
            }
        }
    }
}