package buri.aoc.y19.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.countSteps
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Point2D
import buri.aoc.common.position.Point3D
import buri.aoc.common.position.getNeighbors
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(628, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(7506, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromInput(input, '#')
        val letters = mutableMapOf<Char, MutableList<Point2D<Int>>>()
        for (point in grid.filter { it.isLetterOrDigit() }) {
            val value = grid[point]
            letters.putIfAbsent(value, mutableListOf())
            letters[value]!!.add(point)
        }

        // Resolve ports
        val ports = mutableMapOf<Point2D<Int>, Point2D<Int>>()
        for (pair in letters.values.filter { it.size == 2 }) {
            ports[pair.first()] = grid.getDestination(pair.last())
            ports[pair.last()] = grid.getDestination(pair.first())
        }
        val start = grid.getDestination(letters['0']!!.first()).to3D()
        val end = grid.getDestination(letters['1']!!.first()).to3D()

        val pathfinder = Pathfinder<Point3D<Int>> { current ->
            resolvePorts(part, grid, ports, current)
        }
        val cameFrom = pathfinder.exploreFrom(start)
        return cameFrom.countSteps(start, end)
    }

    /**
     * Handles teleporters.
     */
    private fun resolvePorts(
        part: Part, grid: Grid<Char>, ports: Map<Point2D<Int>, Point2D<Int>>,
        current: Point3D<Int>
    ): List<Point3D<Int>> {
        val newNeighbors = mutableListOf<Point3D<Int>>()
        for (neighbor in current.to2D().getNeighbors().filter { grid[it] != '#' }) {
            val value = grid[neighbor]
            // Skip the start / end markers.
            if (value.isDigit()) {
                continue
            }

            // In Part Two, skip outer ports at depth 0 and inner ports that are probably too deep.
            if (part.isTwo() && value.isLetter()) {
                val isOuter = grid.isOuterPort(neighbor)
                if ((isOuter && current.z == 0) || (!isOuter && current.z == 30)) {
                    continue
                }
            }

            // Swap out teleports.
            if (value.isLetter()) {
                var z = current.z
                if (part.isTwo()) {
                    z += if (grid.isOuterPort(neighbor)) -1 else 1
                }
                val destination2D = ports[neighbor]!!
                newNeighbors.add(destination2D.to3D(z))
            }
            // Keep regular squares as-is.
            else {
                newNeighbors.add(neighbor.to3D(current.z))
            }
        }
        return newNeighbors
    }

    /**
     * Finds the closest spot to a teleport marker to land.
     */
    private fun Grid<Char>.getDestination(port: Point2D<Int>): Point2D<Int> {
        return this.getNeighbors(port).first { this[it] == '.' }
    }

    /**
     * Returns true if a port letter is on the outer edge.
     */
    private fun Grid<Char>.isOuterPort(port: Point2D<Int>): Boolean {
        return (port.x == 0 || port.x == this.width - 1 || port.y == 0 || port.y == this.height - 1)
    }

    /**
     * Converts 2D to 3D with a default 0 z-value.
     */
    private fun Point2D<Int>.to3D(z: Int = 0): Point3D<Int> {
        return Point3D(this.x, this.y, z)
    }

    /**
     * Converts 3D to 2D by dropping the z-value.
     */
    private fun Point3D<Int>.to2D(): Point2D<Int> {
        return Point2D(this.x, this.y)
    }
}