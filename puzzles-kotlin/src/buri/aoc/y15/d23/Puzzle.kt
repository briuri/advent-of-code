package buri.aoc.y15.d23

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
        assertRun(170, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(247, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val registers = mutableMapOf<String, Long>()
        registers["a"] = if (part == Part.ONE) 0 else 1
        registers["b"] = 0

        var pointer = 0
        while (pointer in 0..input.lastIndex) {
            val tokens = input[pointer].split(" ")
            when (tokens[0]) {
                "hlf" -> {
                    registers[tokens[1]] = registers[tokens[1]]!! / 2
                    pointer++
                }
                "tpl" -> {
                    registers[tokens[1]] = registers[tokens[1]]!! * 3
                    pointer++
                }
                "inc" -> {
                    registers[tokens[1]] = registers[tokens[1]]!! + 1
                    pointer++
                }
                "jmp" -> {
                    pointer += tokens[1].toInt()
                }
                "jie" -> {
                    if (registers[tokens[1].dropLast(1)]!! % 2 == 0L) {
                        pointer += tokens[2].toInt()
                    }
                    else {
                        pointer++
                    }
                }
                "jio" -> {
                    if (registers[tokens[1].dropLast(1)]!! == 1L) {
                        pointer += tokens[2].toInt()
                    }
                    else {
                        pointer++
                    }
                }
            }
        }
        return registers["b"]!!
    }
}