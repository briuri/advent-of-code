package buri.aoc.y18.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
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
        val frontier = mutableListOf<State>()
        frontier.add(cave.mouth)

        var current: State?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()

            // Consider moves with the same tool.
            val moves = mutableSetOf<State>()
            for (next in cave.getNeighbors(Point2D(current.x, current.y), false)) {
                // Only consider moves to terrain compatible with this tool.
                if (cave.getType(next.x, next.y) != current.tool.invalidType) {
                    moves.add(State(next.x, next.y, current.tool, current.minutes + 1))
                }
            }
            // Consider swapping tools and staying here.
            for (tool in Tool.values()) {
                val type = cave.getType(current.x, current.y)
                if (tool != current.tool && tool.invalidType != type) {
                    moves.add(current.copy(tool = tool, minutes = current.minutes + 7))
                    break
                }
            }

            // Add moves if we haven't been there at a lower cost before.
            for (move in moves) {
                if (visited[move.position] == null || visited[move.position]!! > move.minutes) {
                    frontier.add(move)
                    visited[move.position] = move.minutes
                }
            }
            // Explore lower cost paths first.
            frontier.sortBy { it.minutes }
        }
        return visited[target]!!
    }
}

enum class Tool(val invalidType: Int) { NONE(0), TORCH(1), CLIMB(2) }

data class State(val x: Int, val y: Int, val tool: Tool, val minutes: Int) {
    val position = Triple(x, y, tool)
}

class Cave(private val depth: Int, val target: Triple<Int, Int, Tool>) :
    Grid<Int>(target.first + 39, target.second + 39, 0) {
    val mouth = State(0, 0, Tool.TORCH, 0)

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
        val geoIndex = if (point == mouth.position || point == target) {
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
        for (y in 0..target.second) {
            for (x in 0..target.first) {
                sum += getRisk(x, y)
            }
        }
        return sum
    }
}