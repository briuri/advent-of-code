package buri.aoc.y24.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import org.junit.Test
import kotlin.math.pow

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("4,6,3,5,6,3,5,2,1,0", 1)
        assertRun("5,7,3,0", 2)
        assertRun("7,1,3,4,1,2,6,7,1", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("117440", 2)
        assertRun("109019476330651", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val computer = Computer()
        val program = input[4].extractLongs()
        val startA = input[0].extractLongs().first()
        val startB = input[1].extractLongs().first()
        val startC = input[2].extractLongs().first()
        if (part.isOne()) {
            return computer.run(program, startA, startB, startC).joinToString(",")
        }

        // Part TWO
        // Range of a values that result in 16 outputs.
        // (Irrelevant, researched during brute-force guessing)
        // val range = 35_184_372_088_832L..281_474_976_710_655L

        val expected = program.joinToString(",")
        var attempts = mutableListOf<Long>()
        attempts.add(0)
        for (i in 1..program.size) {
            val nextAttempts = mutableListOf<Long>()
            for (a in attempts) {
                for (offset in 0..7) {
                    val newA = a * 8 + offset
                    val output = computer.run(program, newA, startB, startC)
                    if (expected.endsWith(output.joinToString(",")) && output.size == i) {
                        nextAttempts.add(newA)
                    }
                }
            }
            attempts = nextAttempts
        }
        return attempts.first().toString()
    }
}

class Computer {
    private var a: Long = 0L
    private var b: Long = 0L
    private var c: Long = 0L
    private val debug = false

    /**
     * Executes a program, given the starting values of each register.
     */
    fun run(program: List<Long>, startA: Long, startB: Long, startC: Long): List<Long> {
        a = startA
        b = startB
        c = startC

        val output = mutableListOf<Long>()
        var ip = 0
        while (true) {
            if (ip > program.lastIndex) {
                debugPrint("[halt] ip=$ip\n")
                break
            }
            val instruction = program[ip]
            var operand = program[ip + 1]
            when (instruction) {
                0L -> {
                    operand = operand.toCombo()
                    val denominator = 2.toDouble().pow(operand.toDouble()).toLong()
                    val result = (a / denominator)
                    debugPrint("[adv] a = a / 2^opC = $a / $denominator = $result")
                    a = result
                }

                1L -> {
                    val result = b xor operand
                    debugPrint("[bxl] b = b xor opL = $b xor $operand = $result")
                    b = result
                }

                2L -> {
                    operand = operand.toCombo()
                    val result = operand % 8
                    debugPrint("[bst] b = opC % 8 = $operand % 8 = $result")
                    b = result
                }

                3L -> {
                    val result = if (a == 0L) "(ignored, a = 0)" else "(jumped, a = $a)"
                    debugPrint("[jnz] ip = opL = $operand $result")
                    if (a != 0L) {
                        ip = operand.toInt()
                        continue
                    }
                }

                4L -> {
                    val result = b xor c
                    debugPrint("[bxc] b = b xor c = $b xor $c = $result")
                    b = result
                }

                5L -> {
                    operand = operand.toCombo()
                    val result = operand % 8
                    debugPrint("[out] out = opC % 8 = $operand % 8 = $result")
                    output.add(result)
                }

                6L -> {
                    operand = operand.toCombo()
                    val denominator = 2.toDouble().pow(operand.toDouble()).toLong()
                    val result = (a / denominator)
                    debugPrint("[bdv] b = a / 2^opC = $a / $denominator = $result")
                    b = result
                }

                // 7L
                else -> {
                    operand = operand.toCombo()
                    val denominator = 2.toDouble().pow(operand.toDouble()).toLong()
                    val result = (a / denominator)
                    debugPrint("[cdv] c = a / 2^opC = $a / $denominator = $result")
                    c = result
                }
            }
            ip += 2
        }
        return output
    }

    /**
     * Displays individual commands for inspection.
     */
    private fun debugPrint(output: String) {
        if (debug) {
            println(output)
        }
    }

    /**
     * Converts an operand into a combo operand.
     */
    private fun Long.toCombo(): Long {
        return when (this) {
            0L -> 0
            1L -> 1
            2L -> 2
            3L -> 3
            4L -> a
            5L -> b
            6L -> c
            else -> throw Exception("7 is not a valid operand.")
        }
    }
}