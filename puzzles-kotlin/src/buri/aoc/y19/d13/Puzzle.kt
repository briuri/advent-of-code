package buri.aoc.y19.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
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
        assertRun(180, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8777, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val computer = Computer(input)
        if (part.isTwo()) {
            computer.set(0, 2)
        }

        var score = 0L
        val grid = Grid(36, 21, ' ')
        var paddleX = -1L
        var ballX = -1L
        while (!computer.halted) {
            computer.run()
            while (computer.hasOutput()) {
                val x = computer.output()
                val y = computer.output()
                val id = computer.output()
                if (x != -1L) {
                    val tile = when (id.toInt()) {
                        0 -> ' '
                        1 -> '■'
                        2 -> '#'
                        3 -> {
                            paddleX = x
                            '='
                        }
                        else -> {
                            ballX = x
                            'o'
                        }
                    }
                    grid[x.toInt(), y.toInt()] = tile
                } else {
                    score = id
                }
            }

            // Follow the ball with the paddle.
            val command = if (paddleX == ballX) {
                0L
            } else if (paddleX > ballX) {
                -1L
            } else {
                1L
            }
            computer.input(command)
        }

        return if (part.isOne()) grid.count('#') else score
    }
}