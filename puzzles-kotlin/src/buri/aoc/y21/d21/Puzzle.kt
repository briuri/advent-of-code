package buri.aoc.y21.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Point4D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(739785, 1)
        assertRun(926610, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(444356092776315, 1)
        assertRun(146854918035875, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var position1 = input[0].extractInts()[1]
        var position2 = input[1].extractInts()[1]
        val finalScore = if (part.isOne()) 1000 else 21

        if (part.isOne()) {
            var score1 = 0
            var score2 = 0
            val dice = Dice()
            while (true) {
                position1 = move(position1, dice.nextThree())
                score1 += position1
                if (score1 >= finalScore) {
                    return score2 * dice.rollCount
                }
                position2 = move(position2, dice.nextThree())
                score2 += position2
                if (score2 >= finalScore) {
                    return score1 * dice.rollCount
                }
            }
        }

        // Chances of each number when rolling a 3-sided dice 3 times.
        val rolls = mutableMapOf<Int, Int>()
        rolls[3] = 1
        rolls[4] = 3
        rolls[5] = 6
        rolls[6] = 7
        rolls[7] = 6
        rolls[8] = 3
        rolls[9] = 1

        var wins1 = 0L
        var wins2 = 0L

        var universes = mutableMapOf<Point4D<Int>, Long>()
        universes[Point4D(position1, 0, position2, 0)] = 1L

        while (universes.isNotEmpty()) {
            val nextUniverses = mutableMapOf<Point4D<Int>, Long>()
            for (state in universes.keys) {
                val p1 = state.x
                val score1 = state.y
                val p2 = state.z
                val score2 = state.t
                val stateCount = universes[state]!!

                for (roll1 in rolls.keys) {
                    val nextPosition1 = move(p1, roll1)
                    val nextScore1 = score1 + nextPosition1
                    if (nextScore1 >= finalScore) {
                        wins1 += stateCount * rolls[roll1]!!
                        continue
                    }
                    for (roll2 in rolls.keys) {
                        val nextPosition2 = move(p2, roll2)
                        val nextScore2 = score2 + nextPosition2
                        val increment = stateCount * rolls[roll1]!! * rolls[roll2]!!
                        if (nextScore2 >= finalScore) {
                            wins2 += increment
                            continue
                        }
                        val nextState = Point4D(nextPosition1, nextScore1, nextPosition2, nextScore2)
                        nextUniverses[nextState] = nextUniverses.getOrDefault(nextState, 0L) + increment
                    }
                }
            }
            universes = nextUniverses
        }
        return wins1.coerceAtLeast(wins2)
    }

    /**
     * Updates the position of a player.
     */
    private fun move(position: Int, roll: Int) = (position + roll - 1) % 10 + 1
}

class Dice {
    var rollCount = 0

    /**
     * Returns the next three rolls.
     */
    fun nextThree(): Int = next() + next() + next()

    /**
     * Returns the next roll.
     */
    fun next(): Int {
        rollCount++
        return (rollCount - 1) % 100 + 1
    }
}