package buri.aoc.y22.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(15422, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(15442, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        return input.sumOf { play(part, it) }
    }

    private val scores = mapOf('A' to 1, 'B' to 2, 'C' to 3)

    /**
     * Plays a game of RPS and returns player 2's score.
     */
    private fun play(part: Part, data: String): Int {
        val p1 = data[0]
        val p2 = if (part.isOne()) {
            (data[2].code - 23).toChar()
        } else {
            when (data[2]) {
                // Lose to p1
                'X' -> when (p1) {
                    'A' -> 'C'
                    'B' -> 'A'
                    else -> 'B'
                }
                // Draw with p1
                'Y' -> p1
                // Win over p1
                else -> when (p1) {
                    'A' -> 'B'
                    'B' -> 'C'
                    else -> 'A'
                }
            }
        }
        val winScore = if ("$p1$p2" in listOf("AB", "BC", "CA")) {
            6
        } else if (p1 == p2) {
            3
        } else {
            0
        }
        return scores[p2]!! + winScore
    }
}