package buri.aoc.y17.d23

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
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

    private val registers = mutableMapOf<String, Int>()

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part == ONE) {
            for (name in 'a'..'h') {
                registers[name.toString()] = 0
            }
            var mulCount = 0
            var pointer = 0
            while (pointer in input.indices) {
                val command = input[pointer].split(" ")
                var offset = 1
                val y = resolve(command[2])
                if (command[0] == "set") {
                    registers[command[1]] = y
                } else if (command[0] == "sub") {
                    registers[command[1]] = registers[command[1]]!! - y
                } else if (command[0] == "mul") {
                    registers[command[1]] = registers[command[1]]!! * y
                    mulCount++
                } else if (resolve(command[1]) != 0) { //jnz
                    offset = y
                }
                pointer += offset
            }
            return mulCount
        }

        // Part TWO: Find non-prime numbers in the range.
        val low = input[0].split(" ")[2].toInt() *
                input[4].split(" ")[2].toInt() -
                input[5].split(" ")[2].toInt()
        val steps = input[7].split(" ")[2].toInt() * -1
        val increment = input[30].split(" ")[2].toInt() * -1

        var h = 0
        for (b in low..(low + steps) step increment) {
            if (!b.isPrime()) {
                h++
            }
        }
        return h
    }

    /**
     * Converts an instruction token into a value from a register or a plain numeric value.
     */
    private fun resolve(addressOrValue: String): Int {
        return if (addressOrValue.toIntOrNull() == null) {
            registers.putIfAbsent(addressOrValue, 0)
            return registers[addressOrValue]!!
        } else {
            addressOrValue.toInt()
        }
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