package buri.aoc.y19.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import buri.aoc.common.position.Point2D
import buri.aoc.common.position.getNeighbors
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
        assertRun(7720, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1681189, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computer = Computer(input[0].extractLongs())

        val scaffolds = mutableSetOf<Point2D<Int>>()
        if (part.isOne()) {
            computer.run()
            var x = 0
            var y = 0
            while (computer.hasOutput()) {
                val value = computer.output().toInt()
                if (value == 10) {
                    x = 0
                    y++
                } else {
                    if (value == 35) {
                        scaffolds.add(Point2D(x, y))
                    }
                    x++
                }
            }

            // Find scaffolds with all 4 neighbors also scaffolds and sum their point products.
            return scaffolds.filter { it.getNeighbors().all { near -> near in scaffolds } }.sumOf { it.x * it.y }
        }

        // Based on visual inspection of the grid (after which I refactored Part I to just use a set).
        val commands = "A,B,A,B,C,C,B,A,B,C\nL,10,R,10,L,10,L,10\nR,10,R,12,L,12\nR,12,L,12,R,6\nn\n"
        computer.input(commands)
        computer.set(0, 2L)
        computer.run()
        return computer.output(true)
    }
}