package buri.aoc.y24.d06

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
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
        assertRun(41, 1)
        assertRun(5564, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(6, 1)
        assertRun(1976, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val pos = MutablePosition(grid.filter { it == '^' }.first(), Direction.NORTH)
        val visited = mutableSetOf<Point2D<Int>>()
        while (grid.isInBounds(pos.coords)) {
            visited.add(pos.coords)
            pos.moveOrTurn(grid)
        }
        if (part.isOne()) {
            return visited.size
        }

        // Part TWO
        var blocks = 0
        // Constrain search area to just the blocks we know the guard will hit to speed up.
        for (block in visited.filter { grid[it] == '.' }) {
            val newGrid = grid.copy()
            newGrid[block] = '#'
            if (newGrid.isCycle()) {
                blocks++
            }
        }
        return blocks
    }

    /**
     * Attempts to move until the guard reaches the edge or hits an obstruction.
     */
    private fun MutablePosition.moveOrTurn(grid: Grid<Char>) {
        val nextPos = MutablePosition(this.coords, this.facing)
        nextPos.move()
        if (!grid.isInBounds(nextPos.coords) || grid[nextPos.coords] != '#') {
            this.move()
        } else {
            this.turnRight()
        }
    }

    /**
     * Checks if the guard is in a cycle on a grid.
     */
    private fun Grid<Char>.isCycle(): Boolean {
        val pos = MutablePosition(this.filter { it == '^' }.first(), Direction.NORTH)
        // Include facing in visited set this time to track cycle.
        val visited = mutableSetOf<String>()
        while (this.isInBounds(pos.coords)) {
            if (pos.toString() in visited) {
                return true
            }
            visited.add(pos.toString())
            pos.moveOrTurn(this)
        }
        return false
    }
}