package buri.aoc.y19.d18

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Pathfinder
import buri.aoc.common.position.Grid
import buri.aoc.common.position.Point2D
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
        assertRun(86, 1)
        assertRun(6286, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(32, 2)
        assertRun(2140, 3, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid(input[0].length, input.size, '#')
        val robotStarterKeys = mutableMapOf<Char, MutableList<Char>>()
        val keys = mutableMapOf<Char, Point2D<Int>>()
        val doors = mutableMapOf<Char, Point2D<Int>>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                grid[x, y] = value
                val point = Point2D(x, y)
                if (value.isDigit()) {
                    robotStarterKeys[value] = mutableListOf()
                    // Temporarily store starts so we can generate routes from them.
                    keys[value] = point
                }
                if (value.isLetter()) {
                    if (value.isLowerCase()) {
                        keys[value] = point
                    } else {
                        doors[value] = point
                    }
                }
            }
        }

        // Use a pathfinder that avoids walls.
        val pathfinder = Pathfinder<Point2D<Int>> { current ->
            current.getNeighbors().filter { grid[it] != '#' }
        }

        // Build a graph of keys to all other keys
        val routes = mutableMapOf<Char, List<Route>>()
        for ((startKey, startPoint) in keys) {
            val cameFrom = pathfinder.exploreFrom(startPoint)
            val otherKeys = keys.filter { it.key != startKey && !it.key.isDigit() }
            routes[startKey] = getRoutes(grid, cameFrom, startPoint, otherKeys)
        }

        // Remove start points from keys once routes are generated.
        for (robot in robotStarterKeys.keys) {
            keys.remove(robot)
        }
        // Give each robot any keys it cannot access on its own.
        for (robot in robotStarterKeys.keys) {
            val reachableKeys = routes[robot]!!.map { it.key }
            robotStarterKeys[robot]!!.addAll(keys.keys - reachableKeys.toSet())
        }

        // Explore from starts to find the shortest path to get every key.
        var steps = 0
        for (robot in robotStarterKeys.keys) {
            val results = mutableSetOf<Int>()
            explore(routes, keys.size, results, mutableMapOf(), robot, 0, robotStarterKeys[robot]!!.toSet())
            steps += results.min()
        }
        return steps
    }

    /**
     * Explores to find the humber of steps needed to collect every key from some previous state.
     */
    private fun explore(
        routes: Map<Char, List<Route>>, totalKeys: Int,
        results: MutableSet<Int>, visited: MutableMap<String, Int>,
        startKey: Char, stepsSoFar: Int, keysSoFar: Set<Char>,
    ) {
        val keys = keysSoFar.toMutableSet()
        if (!startKey.isDigit()) {
            keys.add(startKey)
        }
        // We have collected every key.
        if (keys.size == totalKeys) {
            results.add(stepsSoFar)
        }
        // Track older states based on where we are and how many keys we have.
        val visitedKey = startKey + "-" + keys.sorted().joinToString("")
        if (visitedKey !in visited || visited[visitedKey]!! > stepsSoFar) {
            visited[visitedKey] = stepsSoFar
            for (route in routes[startKey]!!.filter { it.isOpen(keys) && it.key !in keys }) {
                explore(routes, totalKeys, results, visited, route.key, stepsSoFar + route.steps, keys)
            }
        }
    }

    /**
     * Calculates all routes from one location to all other keys.
     */
    private fun getRoutes(
        grid: Grid<Char>,
        cameFrom: Map<Point2D<Int>, Point2D<Int>?>,
        startPoint: Point2D<Int>,
        otherKeys: Map<Char, Point2D<Int>>
    ): List<Route> {
        val routes = mutableListOf<Route>()
        for ((key, point) in otherKeys) {
            var steps = 0
            val requiredKeys = mutableListOf<Char>()
            var current: Point2D<Int>? = point
            while (current != null) {
                steps++
                current = cameFrom[current]
                // No path to this key.
                if (current == null) {
                    break
                }
                // Path exists.
                if (current == startPoint) {
                    routes.add(Route(key, steps, requiredKeys))
                    break
                }
                // We stepped through a locked door.
                if (grid[current].isUpperCase()) {
                    requiredKeys.add(grid[current].lowercaseChar())
                }
            }
        }
        return routes
    }
}

class Route(val key: Char, val steps: Int, private val requiredKeys: List<Char>) {

    /**
     * Returns true if we have all the keys we need for this route.
     */
    fun isOpen(acquiredKeys: Set<Char>): Boolean = requiredKeys.all { it in acquiredKeys }

    override fun toString(): String {
        return "to $key in $steps steps $requiredKeys"
    }
}