package buri.aoc.y19.d23

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
        assertRun(18982, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(11088, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computers = mutableListOf<Computer>()
        repeat(50) {
            val computer = Computer(input)
            computer.input(it.toLong())
            computers.add(computer)
        }

        val yValues = mutableSetOf<Long>()
        var nat: Pair<Long, Long>? = null
        while (true) {
            for (computer in computers) {
                if (!computer.hasInput()) {
                    computer.input(-1L)
                }
            }
            for (computer in computers) {
                computer.run()
                if (computer.hasOutput()) {
                    val address = computer.output().toInt()
                    val x = computer.output()
                    val y = computer.output()
                    if (address == 255) {
                        nat = Pair(x, y)
                        if (part.isOne()) {
                            return nat.second
                        }
                    } else {
                        computers[address].input(x)
                        computers[address].input(y)
                    }
                }
            }

            if (part.isTwo() && nat != null && computers.none { it.hasInput() }) {
                if (nat.second in yValues) {
                    return nat.second
                }
                yValues.add(nat.second)
                computers[0].input(nat.first)
                computers[0].input(nat.second)
            }
        }
    }
}