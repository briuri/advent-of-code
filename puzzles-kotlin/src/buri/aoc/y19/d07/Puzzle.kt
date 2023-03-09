package buri.aoc.y19.d07

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
        assertRun(46248, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(54163586, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val instructions = input[0].extractInts()
        val phaseSettings = if (part.isOne()) listOf(0, 1, 2, 3, 4) else listOf(5, 6, 7, 8, 9)
        val numAmps = phaseSettings.size

        var bestOutput = 0
        for (phases in generatePermutations(phaseSettings)) {
            val computers = mutableListOf<Computer>()
            for (i in 0 until numAmps) {
                computers.add(Computer(instructions))
                computers[i].inputs.add(phases[i])
            }

            var interimOutput = 0
            do {
                for (computer in computers) {
                    computer.inputs.add(interimOutput)
                    computer.run()
                    interimOutput = computer.outputs.removeLast()
                }
            } while (!computers.last().halted)
            bestOutput = bestOutput.coerceAtLeast(interimOutput)
        }
        return bestOutput
    }
}