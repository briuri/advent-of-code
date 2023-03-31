package buri.aoc.y21.d15

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
        assertRun(40, 1)
        assertRun(398, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(315, 1)
        assertRun(2817, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var grid = Grid.fromIntInput(input)
        if (part.isTwo()) {
            grid = buildMap(grid)
        }
        val start = Point2D(0, 0)
        val end = Point2D(grid.width - 1, grid.height - 1)

        // Previous points mapped to the lowest risk to get there.
        val visited = mutableMapOf<Point2D<Int>, Int>()
        visited[start] = 0
        val frontier = mutableListOf<State>()
        frontier.add(State(start, 0))

        var current: State?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            if (current.point == end) {
                return current.risk
            }
            for (next in grid.getNeighbors(current.point)) {
                val risk = current.risk + grid[next]
                if (visited[next] == null || visited[next]!! > risk) {
                    frontier.add(State(next, risk))
                    visited[next] = risk
                }
            }
            frontier.sortBy { it.risk }
        }
        return -1
    }

    /**
     * Builds the larger map for Part Two.
     */
    private fun buildMap(grid1: Grid<Int>): Grid<Int> {
        val grid2 = grid1.incrementCopy()
        val grid3 = grid2.incrementCopy()
        val grid4 = grid3.incrementCopy()
        val grid5 = grid4.incrementCopy()
        val grid6 = grid5.incrementCopy()
        val grid7 = grid6.incrementCopy()
        val grid8 = grid7.incrementCopy()
        val grid9 = grid8.incrementCopy()

        val tiles = mutableMapOf<Point2D<Int>, Grid<Int>>()
        tiles[Point2D(0, 0)] = grid1
        tiles[Point2D(1, 0)] = grid2
        tiles[Point2D(2, 0)] = grid3
        tiles[Point2D(3, 0)] = grid4
        tiles[Point2D(4, 0)] = grid5

        tiles[Point2D(0, 1)] = grid2
        tiles[Point2D(1, 1)] = grid3
        tiles[Point2D(2, 1)] = grid4
        tiles[Point2D(3, 1)] = grid5
        tiles[Point2D(4, 1)] = grid6

        tiles[Point2D(0, 2)] = grid3
        tiles[Point2D(1, 2)] = grid4
        tiles[Point2D(2, 2)] = grid5
        tiles[Point2D(3, 2)] = grid6
        tiles[Point2D(4, 2)] = grid7

        tiles[Point2D(0, 3)] = grid4
        tiles[Point2D(1, 3)] = grid5
        tiles[Point2D(2, 3)] = grid6
        tiles[Point2D(3, 3)] = grid7
        tiles[Point2D(4, 3)] = grid8

        tiles[Point2D(0, 4)] = grid5
        tiles[Point2D(1, 4)] = grid6
        tiles[Point2D(2, 4)] = grid7
        tiles[Point2D(3, 4)] = grid8
        tiles[Point2D(4, 4)] = grid9

        val tileSize = grid1.width
        val tilesPerEdge = 5
        val stitchedGrid = Grid(tileSize * tilesPerEdge, tileSize * tilesPerEdge, 0)
        for (y in 0 until tilesPerEdge) {
            for (x in 0 until tilesPerEdge) {
                val tile = tiles[Point2D(x, y)]!!
                for (tileY in 0 until tileSize) {
                    for (tileX in 0 until tileSize) {
                        val newX = x * tileSize + tileX
                        val newY = y * tileSize + tileY
                        stitchedGrid[newX, newY] = tile[tileX, tileY]
                    }
                }
            }
        }
        return stitchedGrid
    }

    /**
     * Returns a copy of the grid with risk levels elevated.
     */
    private fun Grid<Int>.incrementCopy(): Grid<Int> {
        val copy = this.copy()
        for (y in copy.yRange) {
            for (x in copy.xRange) {
                var value = copy[x, y] + 1
                if (value == 10) {
                    value = 1
                }
                copy[x, y] = value
            }
        }
        return copy
    }
}

data class State(val point: Point2D<Int>, val risk: Int)