package buri.aoc.y23.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test
import kotlin.math.pow

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(13, 1)
        assertRun(21485, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(30, 1)
        assertRun(11024379, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val scores = mutableMapOf<Int, Int>()
        val counts = mutableMapOf<Int, Int>()
        for (id in 1..input.size) {
            scores[id] = 0
            counts[id] = 1
        }

        for ((index, card) in input.map { it.split(": ")[1] }.withIndex()) {
            val id = index + 1
            val tokens = card.split("|")
            val cardNumbers = tokens[0].extractInts()
            val myNumbers = tokens[1].extractInts()
            val winCount = cardNumbers.intersect(myNumbers.toSet()).size
            if (winCount > 0) {
                scores[id] = 2.toDouble().pow(winCount - 1).toInt()
                for (offset in 1..winCount) {
                    counts[id + offset] = counts[id + offset]!! + counts[id]!!
                }
            }
        }
        return if (part.isOne()) scores.values.sum() else counts.values.sum()
    }
}