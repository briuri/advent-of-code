package buri.aoc.y19.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Grid
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.position.Point2D
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
        assertRun("2276", 0, true)
    }

    @Test
    fun runPart2() {
        // CBLPJZCU
        assertRun("  ■■  ■■■  ■    ■■■    ■■ ■■■■  ■■  ■  ■", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val position = MutablePosition(Point2D(0, 0), Direction.NORTH)
        val painted = mutableMapOf<Point2D<Int>, Int>()
        if (part.isTwo()) {
            painted[position.coords] = 1
        }

        val computer = Computer(input)
        while (!computer.halted) {
            val color = painted.getOrDefault(position.coords, 0)
            computer.input(color.toLong())
            computer.run()
            painted[position.coords] = computer.output().toInt()
            if (computer.output().toInt() == 0) {
                position.turnLeft()
            } else {
                position.turnRight()
            }
            position.move()
        }

        if (part.isOne()) {
            return painted.size.toString()
        }

        val grid = Grid(50, 6, ' ')
        for ((point, _) in painted.filter { it.value == 1 }) {
            grid[point] = '■'
        }
        return grid.toString()
    }
}