package buri.aoc.y17.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction.NORTH
import buri.aoc.common.position.Grid
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(5587, 1)
        assertRun(5399, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2511944, 1)
        assertRun(2511776, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        // Using a grid and offsetting so we stay in the grid is faster than using a map of points to states.
        val gridOffset = 225
        val grid = Grid(400, 440, State.CLEAN.icon)
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                if (value == '#') {
                    grid[x + gridOffset, y + gridOffset] = State.INFECTED.icon
                }
            }
        }

        val virus = MutablePosition(Point2D(input[0].length / 2 + gridOffset, input.size / 2 + gridOffset), NORTH)
        var count = 0
        repeat(if (part.isOne()) 10_000 else 10_000_000) {
            when (grid[virus.coords]) {
                State.CLEAN.icon -> virus.turnLeft()
                State.WEAK.icon -> {}
                State.INFECTED.icon -> virus.turnRight()
                State.FLAGGED.icon -> virus.turnAround()
            }
            when (grid[virus.coords]) {
                State.CLEAN.icon -> {
                    if (part.isOne()) {
                        grid[virus.coords] = State.INFECTED.icon
                        count++
                    } else {
                        grid[virus.coords] = State.WEAK.icon
                    }
                }

                State.WEAK.icon -> {
                    grid[virus.coords] = State.INFECTED.icon
                    count++
                }

                State.INFECTED.icon -> {
                    grid[virus.coords] = if (part.isOne()) State.CLEAN.icon else State.FLAGGED.icon
                }

                State.FLAGGED.icon -> grid[virus.coords] = State.CLEAN.icon
            }
            virus.move()
        }
        return count
    }
}

enum class State(val icon: Char) { CLEAN('.'), WEAK('W'), INFECTED('#'), FLAGGED('F') }