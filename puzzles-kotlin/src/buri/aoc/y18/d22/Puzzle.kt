package buri.aoc.y18.d22

import buri.aoc.common.*
import buri.aoc.common.position.Grid
import org.junit.Test
import kotlin.math.min

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(114, 1)
        assertRun(8681, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(45, 1)
        assertRun(1070, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val targetXY = input[1].extractInts()
        val target = Triple(targetXY[0], targetXY[1], Tool.TORCH)
        val cave = Cave(input[0].extractInts()[0], target)
        if (part.isOne()) {
            return cave.getRiskSum()
        }

        // Previous states mapped to the fewest number of minutes to get there.
        val visited = mutableMapOf<Triple<Int, Int, Tool>, Int>()
        val frontier = mutableListOf<Position>()
        frontier.add(Position(cave.mouth.first, cave.mouth.second, Tool.TORCH, 0))

        var current: Position?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()

            // Consider moves with the same tool.
            val point = Pair(current.x, current.y)
            for (neighbor in point.getNeighbors(false).filter { cave.isInBounds(it) }) {
                // Only consider moves to terrain compatible with this tool.
                val x = neighbor.first
                val y = neighbor.second
                if (cave.getType(x, y) != current.tool.invalidType) {
                    val next = current.copy(x = x, y = y, minutesSoFar = current.minutesSoFar + 1)
                    if (visited[next.position] == null || visited[next.position]!! > next.minutesSoFar) {
                        frontier.add(next)
                        visited[next.position] = next.minutesSoFar
                    }
                }
            }

            // Consider swapping tools and staying here.
            for (tool in Tool.values()) {
                val type = cave.getType(current.x, current.y)
                if (tool != current.tool && tool.invalidType != type) {
                    val next = current.copy(tool = tool, minutesSoFar = current.minutesSoFar + 7)
                    if (visited[next.position] == null || visited[next.position]!! > next.minutesSoFar) {
                        frontier.add(next)
                        visited[next.position] = next.minutesSoFar
                    }
                }
            }
            frontier.sortBy { it.getSortOrder(current, target) }
        }
        return visited[target]!!
    }
}

enum class Type(val risk: Int) { ROCKY(0), WET(1), NARROW(2) }

enum class Tool(val invalidType: Int) { NONE(0), TORCH(1), CLIMB(2) }

data class Position(val x: Int, val y: Int, val tool: Tool, val minutesSoFar: Int) {
    val position = Triple(x, y, tool)

    /**
     * Returns a score favoring lower minutes.
     */
    fun getSortOrder(current: Position, target: Triple<Int, Int, Tool>): Int {
        return minutesSoFar
    }
}

class Cave(private val depth: Int, val target: Triple<Int, Int, Tool>) :
    Grid<Int>(target.first + 39, target.second + 39, 0) {
    val mouth = Triple(0, 0, Tool.TORCH)

    init {
        for (y in 0 until height) {
            for (x in 0 until width) {
                set(x, y, getErosionLevelFor(x, y))
            }
        }
    }

    /**
     * Calculates the erosion level for a square.
     */
    private fun getErosionLevelFor(x: Int, y: Int): Int {
        val point = Triple(x, y, Tool.TORCH)
        val geoIndex = if (point == mouth || point == target) {
            0
        } else if (y == 0) {
            x * 16807
        } else if (x == 0) {
            y * 48271
        } else {
            get(x - 1, y) * get(x, y - 1)
        }
        return (geoIndex + depth) % 20183
    }

    /**
     * Returns the risk level (which is the same as the zone type) for a square.
     */
    private fun getRisk(x: Int, y: Int): Int = get(x, y) % 3
    fun getType(x: Int, y: Int): Int = getRisk(x, y)

    /**
     * Calculates the sum of all risk levels up to the target.
     */
    fun getRiskSum(): Int {
        var sum = 0
        for (y in 0 until target.second + 1) {
            for (x in 0 until target.first + 1) {
                sum += getRisk(x, y)
            }
        }
        return sum
    }
}