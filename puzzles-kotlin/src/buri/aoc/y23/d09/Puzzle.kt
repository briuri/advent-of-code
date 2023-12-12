package buri.aoc.y23.d09

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
        assertRun(114, 1)
        assertRun(1861775706, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2, 1)
        assertRun(1082, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0
        for (line in input) {
            val histories = mutableListOf(line.extractInts().toMutableList())
            do {
                val sequence = histories.last()
                val nextSequence = mutableListOf<Int>()
                for (i in 0 until sequence.lastIndex) {
                    nextSequence.add(sequence[i + 1] - sequence[i])
                }
                histories.add(nextSequence)
            } while (!nextSequence.all { it == 0 })

            var nextValue = 0
            var prevValue = 0
            for (i in histories.lastIndex - 1 downTo 0) {
                nextValue += histories[i].last()
                histories[i].add(nextValue)
                prevValue = histories[i].first() - prevValue
                histories[i].add(0, prevValue)
            }
            sum += if (part.isOne()) histories.first().last() else histories.first().first()
        }
        return sum
    }
}