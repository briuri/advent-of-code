package buri.aoc.y19.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.registers.Computer
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(6069343, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3188550, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computer = Computer(input)
        computer.input(if (part.isOne()) 1L else 5L)
        computer.run()
        return computer.output(true)
    }
}