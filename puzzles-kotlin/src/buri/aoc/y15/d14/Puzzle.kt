package buri.aoc.y15.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(2640, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1102, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val endRace = 2503
        val scores = mutableMapOf<Reindeer, Int>()
        for (line in input) {
            val tokens = line.split(" ")
            val numbers = line.extractInts()
            val reindeer = Reindeer(tokens[0], numbers[0], numbers[1], numbers[2])
            scores[reindeer] = 0
        }
        for (i in 1..endRace) {
            var currentMax = 0
            val currentPositions = mutableMapOf<Reindeer, Int>()
            for (reindeer in scores.keys) {
                val position = reindeer.getPosition(i)
                currentPositions[reindeer] = position
                currentMax = currentMax.coerceAtLeast(position)
            }
            for (reindeer in scores.keys.filter { currentPositions[it] == currentMax }) {
                scores[reindeer] = scores[reindeer]!! + 1
            }
            if (part.isOne() && i == endRace) {
                return currentMax
            }
        }
        return scores.values.max()
    }
}

data class Reindeer(val name: String, val speed: Int, val flyTime: Int, val restTime: Int) {
    /**
     * Calculates the reindeer's position at some time.
     */
    fun getPosition(time: Int): Int {
        val period = flyTime + restTime
        val totalFlyTime = (time / period) * flyTime + (time % period).coerceAtMost(flyTime)
        return (totalFlyTime * speed)
    }
}