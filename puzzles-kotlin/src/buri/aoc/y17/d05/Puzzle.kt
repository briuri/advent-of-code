package buri.aoc.y17.d05

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
        assertRun(360603, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(10, 1)
        assertRun(25347697, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var steps = 0
        var pointer = 0
        val instructions = input.map { it.toInt() }.toMutableList()
        while (pointer in instructions.indices) {
            val pointerOffset = instructions[pointer]
            instructions[pointer] += if (part.isOne() || pointerOffset < 3) 1 else -1
            pointer += pointerOffset
            steps++
        }
        return steps
    }
}