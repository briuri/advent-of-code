package buri.aoc.y17.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Direction.NORTH
import buri.aoc.common.position.MutablePosition
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(5587, 1)
        assertRun(5399, 0, true)
    }

    @Test
    fun runPart2() {
        // Skipping example to reduce test suite time.
        // assertRun(2511944, 1)
        assertRun(2511776, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val nodes = mutableMapOf<Pair<Int, Int>, State>()
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                if (value == '#') {
                    nodes[Pair(x, y)] = State.INFECTED
                }
            }
        }

        val virus = MutablePosition(Pair(input[0].length / 2, input.size / 2), NORTH)
        var count = 0
        repeat(if (part.isOne()) 10_000 else 10_000_000) {
            nodes.putIfAbsent(virus.coords, State.CLEAN)
            when (nodes[virus.coords]!!) {
                State.CLEAN -> virus.turnLeft()
                State.WEAK -> {}
                State.INFECTED -> virus.turnRight()
                State.FLAGGED -> virus.turnAround()
            }
            when (nodes[virus.coords]!!) {
                State.CLEAN -> {
                    if (part.isOne()) {
                        nodes[virus.coords] = State.INFECTED
                        count++
                    } else {
                        nodes[virus.coords] = State.WEAK
                    }
                }
                State.WEAK -> {
                    nodes[virus.coords] = State.INFECTED
                    count++
                }
                State.INFECTED -> {
                    nodes[virus.coords] = if (part.isOne()) State.CLEAN else State.FLAGGED
                }
                State.FLAGGED -> nodes[virus.coords] = State.CLEAN
            }
            virus.move()
        }
        return count
    }
}

enum class State { CLEAN, WEAK, INFECTED, FLAGGED }