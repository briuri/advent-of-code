package buri.aoc.y23.d23

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.countSteps
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
        assertRun(94, 1)
        assertRun(2298, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(154, 1)
        assertRun(6602, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)
        val start = Point2D<Int>(1, 0)
        val end = Point2D(input[0].length - 2, input.size - 1)
        val nodes = mutableMapOf<Point2D<Int>, Node>()

        // Reduce maze down to just the start, intersection points, and end, with pre-calculated distances between each.
        val nodeFrontier = mutableListOf<Point2D<Int>>()
        nodeFrontier.add(start)
        while (nodeFrontier.isNotEmpty()) {
            val current = nodeFrontier.removeFirst()
            if (current != end && current !in nodes.keys) {
                val node = Node.fromGridInput(part, grid, current, end)
                nodes[node.coords] = node
                nodeFrontier.addAll(node.to.keys)
            }
        }

        // Now run the possible paths from start to end to find the longest.
        var maxSteps = 0
        val frontier = mutableListOf<State>()
        frontier.add(State(mutableSetOf(), start, 0))
        while (frontier.isNotEmpty()) {
            frontier.sortByDescending { it.steps }
            val state = frontier.removeFirst()
            if (state.current == end) {
                maxSteps = maxSteps.coerceAtLeast(state.steps)
                continue
            }
            val node = nodes[state.current]!!
            // Look at next possible nodes to travel to, ignoring any we've seen previously.
            for ((next, nextSteps) in node.to.filter { it.key !in state.previous }) {
                val nextPrevious = mutableSetOf<Point2D<Int>>()
                nextPrevious.addAll(state.previous)
                nextPrevious.add(state.current)
                frontier.add(State(nextPrevious, next, state.steps + nextSteps))
            }
        }
        return maxSteps
    }
}

/**
 * Data class for a pathfinding state in the maze.
 */
data class State(val previous: MutableSet<Point2D<Int>>, val current: Point2D<Int>, val steps: Int)

/**
 * Data class for an intersection point in the maze.
 */
data class Node(val coords: Point2D<Int>) {
    val to = mutableMapOf<Point2D<Int>, Int>()

    /**
     * Adds a possible destination.
     */
    fun addTo(point: Point2D<Int>, distance: Int) {
        to[point] = distance
    }

    override fun toString() = "[$coords to $to]"

    companion object {
        fun fromGridInput(part: Part, grid: Grid<Char>, start: Point2D<Int>, end: Point2D<Int>): Node {
            val node = Node(start)

            val frontier = ArrayDeque<Point2D<Int>>()
            frontier.add(start)
            val cameFrom = mutableMapOf<Point2D<Int>, Point2D<Int>?>()
            cameFrom[start] = null
            var current: Point2D<Int>?
            while (frontier.isNotEmpty()) {
                current = frontier.removeFirst()

                // Reached the end, which is the only valid way to go from this intersection.
                if (current == end) {
                    node.addTo(end, cameFrom.countSteps(start, end))
                    break
                }

                // Reached an intersection point.
                val neighbors = grid.getNeighbors(current).filter { isTraversable(part, grid, current, it) }
                if (current != start && neighbors.size > 2) {
                    node.addTo(current, cameFrom.countSteps(start, current))
                    continue
                }

                // Continue exploring
                for (neighbor in neighbors.filter { it !in cameFrom.keys }) {
                    frontier.add(neighbor)
                    cameFrom[neighbor] = current
                }
            }
            return node
        }

        /**
         * Returns true if movement is possible. Ignores slopes in part 2.
         */
        private fun isTraversable(part: Part, grid: Grid<Char>, current: Point2D<Int>, next: Point2D<Int>): Boolean {
            if (part.isOne()) {
                when (grid[current]) {
                    '^' -> {
                        return next.y == current.y - 1
                    }

                    '>' -> {
                        return next.x == current.x + 1
                    }

                    'v' -> {
                        return next.y == current.y + 1
                    }

                    '<' -> {
                        return next.x == current.x - 1
                    }
                }
            }
            return grid[next] != '#'
        }
    }
}