package buri.aoc.y19.d25

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
        assertRun("4722720", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val computer = Computer(input)
        // Originally, I set up commands to bring all items to last room.
        // Then I automated picking up different subsets of items until I found the correct 4.
        // Finally, I pared the commands back to just the ones needed as a walkthrough.
        val commands = "south. west. south. take shell. north. north. take weather machine. west. south. east. " +
                "take candy cane. west. north. east. south. east. east. south. take hypercube. south. south. east"
        for (command in commands.split(". ")) {
            computer.input(command + '\n')
        }
        while (!computer.halted) {
            computer.run()
        }

        val buffer = StringBuilder()
        while (computer.hasOutput()) {
            buffer.append(computer.output().toInt().toChar())
        }
        return buffer.toString().split("typing ")[1].split(" ")[0]
    }
}