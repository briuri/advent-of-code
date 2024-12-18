package buri.aoc.y24.d16

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
        assertRun(7036, 1)
        assertRun(11048, 2)
        assertRun(134588, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(45, 1)
        assertRun(64, 2)
        assertRun(631, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val grid = Grid.fromCharInput(input)

        val bestScore = grid.solve(Part.ONE)
        if (part.isOne()) {
            return bestScore
        }
        return grid.solve(part, bestScore)
    }

    /**
     * Finds the lowest score possible in the maze in part 1, or the total squares of all best paths in part 2.
     * Shorten part 2 runtime by ignoring paths with higher scores.
     */
    private fun Grid<Char>.solve(part: Part, bestScore: Int = Int.MAX_VALUE): Int {
        val start = this.filter { it == 'S' }.first()
        val end = this.filter { it == 'E' }.first()

        val frontier = mutableListOf<State>()
        frontier.add(State(mutableSetOf(), start, Direction.EAST, 0))
        val lowestScores = mutableMapOf<String, Int>()

        val involvedTiles = mutableSetOf<Point2D<Int>>()
        while (frontier.isNotEmpty()) {
            frontier.sortBy { it.score }
            val state = frontier.removeFirst()

            // In part 1, ignore paths that reach a square with the same score or higher (only care about score).
            val scoreConditional = if (part.isOne()) {
                (((lowestScores[state.key] ?: Int.MAX_VALUE) <= state.score))
            }
            // In part 2, include paths with the same score because they might have taken a different route, and cap at bestScore.
            else {
                (((lowestScores[state.key] ?: Int.MAX_VALUE) < state.score) || state.score > bestScore)
            }
            if (scoreConditional) {
                continue
            }
            lowestScores[state.key] = state.score

            if (state.current == end) {
                involvedTiles.addAll(state.pathSoFar)
                if (part.isOne()) {
                    return state.score
                }
            }

            val pathSoFar = state.pathSoFar.toMutableSet()
            pathSoFar.add(state.current)

            val forwardTile =
                Point2D(state.current.x + state.facing.asSlope().x, state.current.y + state.facing.asSlope().y)
            if (this[forwardTile] != '#') {
                frontier.add(State(pathSoFar, forwardTile, state.facing, state.score + 1))
            }
            // Don't turn if it will just make us face a wall.
            val turn = MutablePosition(state.current, state.facing)
            turn.turnLeft()
            val leftTile =
                Point2D(state.current.x + turn.facing.asSlope().x, state.current.y + turn.facing.asSlope().y)
            if (this[leftTile] != '#') {
                frontier.add(State(pathSoFar, state.current, turn.facing, state.score + 1000))
            }
            turn.turnAround()
            val rightTile =
                Point2D(state.current.x + turn.facing.asSlope().x, state.current.y + turn.facing.asSlope().y)
            if (this[rightTile] != '#') {
                frontier.add(State(pathSoFar, state.current, turn.facing, state.score + 1000))
            }
        }
        // Add 1 for the END spot.
        return involvedTiles.size + 1
    }
}

data class State(
    val pathSoFar: MutableSet<Point2D<Int>>,
    val current: Point2D<Int>,
    val facing: Direction,
    val score: Int
) {
    val key = "$current $facing"
}