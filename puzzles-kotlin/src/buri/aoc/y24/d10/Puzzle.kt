package buri.aoc.y24.d10

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
        assertRun(36, 1)
        assertRun(694, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(81, 1)
        assertRun(1497, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromIntInput(input)
        var scores = 0
        for (start in grid.filter { it == 0 }) {
            val frontier = ArrayDeque<Point2D<Int>>()
            frontier.add(start)
            while (frontier.isNotEmpty()) {
                val current = frontier.removeFirst()

                // Reached the end
                if (grid[current] == 9) {
                    scores++
                    continue
                }

                // Continue exploring
                var neighbors = grid.getNeighbors(current).filter { grid[it] - grid[current] == 1 }
                if (part.isOne()) {
                    neighbors = neighbors.filter { it !in frontier }
                }
                frontier.addAll(neighbors)
            }
        }
        return scores
    }
}