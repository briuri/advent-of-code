package buri.aoc.y19.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        assertRun(4023471, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8051, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computer = Computer(input[0].extractInts())
        if (part.isOne()) {
            computer.setNounVerb(12, 2)
            return computer.run()
        }
        for (noun in 0..99) {
            for (verb in 0..99) {
                computer.reset()
                computer.setNounVerb(noun, verb)
                if (computer.run() == 19690720) {
                    return (100 * noun + verb)
                }
            }
        }
        return -1
    }
}