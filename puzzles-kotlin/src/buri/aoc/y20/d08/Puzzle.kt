package buri.aoc.y20.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(5, 1)
        assertRun(1134, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8, 1)
        assertRun(1205, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part.isOne()) {
            return run(input).first
        }

        var i = 0
        while (i in input.indices) {
            if (input[i].startsWith("nop") || input[i].startsWith("jmp")) {
                val (acc, halted) = run(swapInput(input, i))
                if (halted) {
                    return acc
                }
            }
            i++
        }
        return -1
    }

    /**
     * Runs a program. Returns a pair containing the accumulator value and whether the program halted before repeating.
     */
    private fun run(input: List<String>): Pair<Int, Boolean> {
        val visited = mutableSetOf<Int>()
        var acc = 0
        var ip = 0
        while (ip !in visited && ip in input.indices) {
            visited.add(ip)
            val command = input[ip].split(" ")
            if (command[0] == "acc") {
                acc += command[1].toInt()
                ip++
            } else if (command[0] == "jmp") {
                ip += command[1].toInt()
            } else {
                ip++
            }
        }
        return Pair(acc, ip !in input.indices)
    }

    /**
     * Swaps a nop/jmp line and returns the new instructions.
     */
    private fun swapInput(input: List<String>, ip: Int): List<String> {
        val instructions = input.toMutableList()
        if (instructions[ip].startsWith("nop")) {
            instructions[ip] = instructions[ip].replace("nop", "jmp")
        } else {
            instructions[ip] = instructions[ip].replace("jmp", "nop")
        }
        return instructions
    }
}