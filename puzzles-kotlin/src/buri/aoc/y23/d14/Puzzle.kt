package buri.aoc.y23.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.CycleSkipper
import buri.aoc.common.Part
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Orientation
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(136, 1)
        assertRun(106997, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(64, 1)
        assertRun(99641, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var grid = Grid.fromCharInput(input)
        if (part.isOne()) {
            grid.tilt()
        } else {
            val keyStrategy = {
                grid.toString()
            }
            val stepStrategy = {
                repeat(4) {
                    grid.tilt()
                    grid = grid.copy(Orientation.CLOCKWISE_90)
                }
            }
            CycleSkipper(keyStrategy, stepStrategy).run(1_000_000_000)
        }
        return grid.filter { it == 'O' }.sumOf { grid.height - it.y }
    }

    /**
     * Tilts the grid so rocks roll north.
     */
    private fun Grid<Char>.tilt() {
        // Filter comes back in reading order.
        for (point in filter { it == 'O' }) {
            // Seek ahead to the next tile containing a . value.
            var nextUp = point.y - 1
            while (nextUp >= 0 && get(point.x, nextUp) == '.') {
                nextUp--
            }
            set(point.x, point.y, '.')
            set(point.x, nextUp + 1, 'O')
        }
    }
}