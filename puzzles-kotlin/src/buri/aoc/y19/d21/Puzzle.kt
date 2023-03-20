package buri.aoc.y19.d21

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
        assertRun(19354392, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1139528802, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computer = Computer(input)
        // Jump if A, B, or C is not ground, and D is ground.
        // J = (!A || !B || !C) && D
        var commands = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\n"
        // In Part 2, either walk or jump safely after landing at D.
        if (part.isTwo()) {
            commands += "NOT E T\nNOT T T\nOR H T\nAND T J\n"
        }
        commands += if (part.isOne()) "WALK\n" else "RUN\n"
        computer.input(commands)
        computer.run()
        return computer.output(true)
    }
}