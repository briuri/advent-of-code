package buri.aoc.y18.d09

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(32, 1)
        assertRun(380705, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3171801582, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input[0].extractInts()
        val numPlayers = numbers[0]
        val maxValue = if (part == ONE) numbers[1] else (numbers[1] * 100)

        val scores = mutableMapOf<Int, Long>()
        for (i in 0..numPlayers) {
            scores[i] = 0L
        }
        val circle = ArrayDeque<Int>()
        circle.addFirst(0)

        var marbleValue = 1
        var currentPlayer = 0
        while (marbleValue <= maxValue) {
            if (marbleValue % 23 == 0) {
                // Rotate 7 CCW and remove marble for score.
                repeat (7) {
                    circle.addFirst(circle.removeLast())
                }
                scores[currentPlayer] = scores[currentPlayer]!! + marbleValue + circle.removeFirst()
            }
            else {
                // Rotate 2 CW.
                repeat (2) {
                    circle.addLast(circle.removeFirst())
                }
                circle.addFirst(marbleValue)
            }
            marbleValue++
            currentPlayer++
            if (currentPlayer == numPlayers) {
                currentPlayer = 0
            }
        }
        return scores.values.max()
    }
}