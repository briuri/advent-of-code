package buri.aoc.y24.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
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
        assertRun(14, 1)
        assertRun(261, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(34, 1)
        assertRun(898, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        // Get unique antenna IDs.
        val antennas = grid.filter { it != '.' }.map { grid[it] }.toSet()

        val antinodes = mutableSetOf<Point2D<Int>>()
        val repetitions = if (part.isOne()) 1 else 50
        for (antenna in antennas) {
            val positions = grid.filter { it == antenna }
            for (first in positions) {
                for (second in positions.filter { it != first }) {
                    val dx = second.x - first.x
                    val dy = second.y - first.y
                    for (i in 1..repetitions) {
                        antinodes.add(Point2D(first.x - (dx * i), first.y - (dy * i)))
                        antinodes.add(Point2D(second.x + (dx * i), second.y + (dy * i)))
                    }
                    if (part.isTwo()) {
                        antinodes.add(first)
                        antinodes.add(second)
                    }
                }
            }
        }
        return antinodes.filter { grid.isInBounds(it) }.size
    }
}