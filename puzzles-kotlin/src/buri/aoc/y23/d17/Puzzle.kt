package buri.aoc.y23.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction
import buri.aoc.common.position.Grid
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.position.Point2D
import org.junit.Test
import java.lang.Exception

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(102, 1)
        assertRun(855, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(94, 1)
        assertRun(71, 2)
        assertRun(980, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromIntInput(input)
        val end = Point2D(grid.xRange.last, grid.yRange.last)
        val crucibleRange = if (part.isOne()) (1..3) else (4..10)

        val visited = mutableMapOf<String, Int>()
        val frontier = mutableListOf<State>()
        frontier.add(State(Point2D(0, 0), Direction.EAST, 0, 0))
        frontier.add(State(Point2D(0, 0), Direction.SOUTH, 0, 0))

        var current: State?
        var bestHeatLoss = Int.MAX_VALUE
        while (frontier.isNotEmpty()) {
            frontier.sortBy { it.heatLoss }
            current = frontier.removeFirst()

            // The first time we process the end tile is the best heat loss.
            if (current.pos == end) {
                return current.heatLoss
            }

            val moves = mutableSetOf<State>()
            // Go straight.
            if (current.faceCount < crucibleRange.last) {
                // Post-solve optimization: Skip to minimum range omitting interim steps, then go 1 step at a time.
                val stepSize = if (current.faceCount == 0) crucibleRange.first else 1
                moves.add(buildMove(grid, stepSize, current, DirectionType.STRAIGHT))
            }
            // Turn left or right.
            if (current.faceCount >= crucibleRange.first) {
                // Post-solve optimization: After turn, skip to minimum range omitting interim steps.
                val stepSize = crucibleRange.first
                moves.add(buildMove(grid, stepSize, current, DirectionType.LEFT))
                moves.add(buildMove(grid, stepSize, current, DirectionType.RIGHT))
            }

            // Add moves if in bounds and we haven't been there at a better heat loss before.
            for (move in moves.filter { grid.isInBounds(it.pos) && it.heatLoss <= bestHeatLoss }) {
                if (visited[move.key] == null || visited[move.key]!! > move.heatLoss) {
                    visited[move.key] = move.heatLoss
                    if (move.pos == end) {
                        bestHeatLoss = bestHeatLoss.coerceAtMost(move.heatLoss)
                    }
                    frontier.add(move)
                }
            }
        }
        throw Exception("Never reached end.")
    }

    /**
     * Builds the next move. Doesn't check bounds of new coordinates yet.
     */
    private fun buildMove(grid: Grid<Int>, stepSize: Int, current: State, nextDir: DirectionType): State {
        val next = MutablePosition(current.pos, current.facing)
        var faceCount = if (nextDir == DirectionType.STRAIGHT) current.faceCount else 0
        when (nextDir) {
            DirectionType.LEFT -> next.turnLeft()
            DirectionType.RIGHT -> next.turnRight()
            else -> {}
        }
        var heatLoss = current.heatLoss
        repeat(stepSize) {
            next.move()
            if (grid.isInBounds(next.coords)) {
                heatLoss += grid[next.coords]
            }
        }
        return State(next, faceCount + stepSize, heatLoss)
    }
}

enum class DirectionType {
    STRAIGHT, LEFT, RIGHT
}

/**
 * A position on the grid, keeping track of how far we've gone in a straight line and heat loss so far.
 */
data class State(val pos: Point2D<Int>, val facing: Direction, val faceCount: Int, val heatLoss: Int) {
    val x = pos.x
    val y = pos.y
    val key = "$pos|$facing|$faceCount"

    constructor(next: MutablePosition, nextFaceCount: Int, heatLoss: Int) : this(
        next.coords, next.facing,
        nextFaceCount, heatLoss
    )
}