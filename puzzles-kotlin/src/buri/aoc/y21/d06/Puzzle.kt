package buri.aoc.y21.d06

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
        assertRun(5934, 1)
        assertRun(360761, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(26984457539, 1)
        assertRun(1632779838045, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var lanternfish = MutableList(9) { 0L }
        for (fish in input[0].extractInts()) {
            lanternfish[fish] = lanternfish[fish] + 1
        }
        val times = if (part.isOne()) 80 else 256
        repeat(times) {
            val nextFish = MutableList(9) { 0L }
            nextFish[8] = lanternfish[0]
            nextFish[6] = lanternfish[0]
            for (i in 1..8) {
                nextFish[i - 1] = nextFish[i - 1] + lanternfish[i]
            }
            lanternfish = nextFish
        }
        return lanternfish.sum()
    }
}