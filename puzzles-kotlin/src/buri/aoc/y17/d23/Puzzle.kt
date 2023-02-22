package buri.aoc.y17.d23

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.registers.NamedRegisters
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
        assertRun(5929, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(907, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val registers = NamedRegisters()
        if (part.isOne()) {
            for (name in 'a'..'h') {
                registers[name.toString()] = 0L
            }
            var mulCount = 0
            var pointer = 0
            while (pointer in input.indices) {
                val command = input[pointer].split(" ")
                var offset = 1
                val y = registers.resolve(command[2])
                if (command[0] == "set") {
                    registers[command[1]] = y
                } else if (command[0] == "sub") {
                    registers.subtract(command[1], y)
                } else if (command[0] == "mul") {
                    registers.multiply(command[1], y)
                    mulCount++
                } else if (registers.resolve(command[1]) != 0L) { //jnz
                    offset = y.toInt()
                }
                pointer += offset
            }
            return mulCount
        }

        // Part TWO: Find non-prime numbers in the range.
        val low = input[0].extractInts()[0] * input[4].extractInts()[0] - input[5].extractInts()[0]
        val steps = input[7].extractInts()[0] * -1
        val increment = input[30].extractInts()[0] * -1

        var h = 0
        for (b in low..(low + steps) step increment) {
            if (!b.isPrime()) {
                h++
            }
        }
        return h
    }

    /**
     * Returns true if a number is prime.
     */
    private fun Int.isPrime(): Boolean {
        if (this % 2 == 0) {
            return (this == 2)
        }
        for (i in 3..(0.1 + sqrt(this.toDouble())).toInt() step 2) {
            if (this % i == 0) {
                return false
            }
        }
        return true
    }
}