package buri.aoc.y19.d20

import buri.aoc.common.*
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
        val grid = Grid(input[0].length, input.size, '#')
        val letters = mutableMapOf<Char, MutableList<Point2D<Int>>>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                grid[x, y] = value
                if (value.isLetterOrDigit()) {
                    letters.putIfAbsent(value, mutableListOf())
                    letters[value]!!.add(Point2D(x, y))
                }
            }
        }

        // Resolve ports
        val ports = mutableMapOf<Point2D<Int>, Point2D<Int>>()
        for (pair in letters.values.filter { it.size == 2 }) {
            ports[pair.first()] = grid.getDestination(pair.last())
            ports[pair.last()] = grid.getDestination(pair.first())
        }
        val start2D = grid.getDestination(letters['0']!!.first())
        val end2D = grid.getDestination(letters['1']!!.first())

        if (part.isOne()) {
            // Use a pathfinder that avoids walls.
            val pathfinder = Pathfinder { current ->
                resolvePorts2D(grid, ports, current.getNeighbors().filter { grid[it] != '#' })
            }
            val cameFrom = pathfinder.exploreFrom(start2D)
            return cameFrom.countSteps(start2D, end2D)
        }

        val start3D = Point3D(start2D.x, start2D.y, 0)
        val end3D = Point3D(end2D.x, end2D.y, 0)
        val cameFrom = exploreFrom3D(grid, ports, start3D)
        return cameFrom.countSteps(start3D, end3D)
    }

    /**
     * Returns a "came from" map showing all the reachable spaces from a particular space. The entry for the
     * starting position start will have a null value.
     */
    private fun exploreFrom3D(
        grid: Grid<Char>,
        ports: Map<Point2D<Int>, Point2D<Int>>,
        start: Point3D<Int>
    ): Map<Point3D<Int>, Point3D<Int>?> {
        val frontier = ArrayDeque<Point3D<Int>>()
        frontier.add(start)

        val cameFrom = mutableMapOf<Point3D<Int>, Point3D<Int>?>()
        cameFrom[start] = null

        var current: Point3D<Int>?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            val neighbors = resolvePorts3D(grid, ports, current)
            for (next in neighbors.filter { !cameFrom.containsKey(it) }) {
                frontier.add(next)
                cameFrom[next] = current
            }
        }
        return cameFrom
    }

    /**
     * Handles teleporters in Part One.
     */
    private fun resolvePorts2D(
        grid: Grid<Char>, ports: Map<Point2D<Int>, Point2D<Int>>,
        neighbors: List<Point2D<Int>>
    ): List<Point2D<Int>> {
        val newNeighbors = mutableListOf<Point2D<Int>>()
        for (neighbor in neighbors) {
            val value = grid[neighbor]
            // Skip the start / end markers.
            if (value.isDigit()) {
                continue
            }
            // Swap out teleports.
            if (value.isLetter()) {
                newNeighbors.add(ports[neighbor]!!)
            } else {
                // Keep regular squares as-is.
                newNeighbors.add(neighbor)
            }
        }
        return newNeighbors
    }

    /**
     * Handles teleporters in Part Two.
     */
    private fun resolvePorts3D(
        grid: Grid<Char>, ports: Map<Point2D<Int>, Point2D<Int>>,
        current: Point3D<Int>
    ): List<Point3D<Int>> {
        val neighbors2D = Point2D(current.x, current.y).getNeighbors().filter { grid[it] != '#' }
        val neighbors3D = mutableListOf<Point3D<Int>>()
        for (neighbor in neighbors2D) {
            val value = grid[neighbor]
            // Skip the start / end markers
            if (value.isDigit()) {
                continue
            }
            // Skip outer ports at depth 0
            if (value.isLetter() && current.z == 0 && grid.isOuterPort(neighbor)) {
                continue
            }
            // Skip inner ports that are probably too deep, since exit is at surface.
            if (value.isLetter() && current.z == 30 && !grid.isOuterPort(neighbor)) {
                continue
            }
            // Swap out teleports.
            if (value.isLetter()) {
                val newDepth = if (grid.isOuterPort(neighbor)) (current.z - 1) else (current.z + 1)
                val destination = ports[neighbor]!!
                neighbors3D.add(Point3D(destination.x, destination.y, newDepth))
            } else {
                // Keep regular squares on the same plane.
                neighbors3D.add(Point3D(neighbor.x, neighbor.y, current.z))
            }
        }
        return neighbors3D
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
}