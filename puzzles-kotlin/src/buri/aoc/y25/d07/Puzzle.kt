package buri.aoc.y25.d07

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
        assertRun(21, 1)
        assertRun(1630, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(40, 1)
        assertRun(47857642990160, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val start = grid.filter { it == 'S' }.first()

        if (part.isOne()) {
            val beams = mutableListOf<Point2D<Int>>()
            beams.add(start)

            val splitters = mutableSetOf<Point2D<Int>>()
            val visited = mutableSetOf<Point2D<Int>>()
            while (beams.isNotEmpty()) {
                val beam = beams.removeFirst()
                if (beam !in visited) {
                    visited.add(beam)
                    val nextSplitter = grid.getSplitter(beam)
                    if (nextSplitter != null) {
                        splitters.add(nextSplitter)
                        beams.add(Point2D(nextSplitter.x - 1, nextSplitter.y))
                        beams.add(Point2D(nextSplitter.x + 1, nextSplitter.y))
                    }
                }
            }
            return splitters.size
        }

        // Part Two
        return grid.getWorldCount(mutableMapOf(), start)
    }

    fun Grid<Char>.getSplitter(beam: Point2D<Int>): Point2D<Int>? {
        var y = beam.y + 1
        while (y in this.yRange) {
            val test = Point2D(beam.x, y)
            if (this[Point2D(beam.x, y)] == '^') {
                return test
            }
            y++
        }
        return null
    }

    fun Grid<Char>.getWorldCount(visited: MutableMap<Point2D<Int>, Long>, start: Point2D<Int>): Long {
        if (start !in visited) {
            var y = start.y + 1
            while (y in this.yRange) {
                // Beam splits into 2 beams.
                if (this[Point2D(start.x, y)] == '^') {
                    val left = this.getWorldCount(visited, Point2D(start.x - 1, y))
                    val right = this.getWorldCount(visited, Point2D(start.x + 1, y))
                    visited[start] = left + right
                    break
                }
                y++
            }
            // Base Case: Beam reaches bottom without splitting.
            if (y !in this.yRange) {
                visited[start] = 1
            }
        }
        return visited[start]!!
    }
}
