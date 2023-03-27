package buri.aoc.y20.d23

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
        assertRun("67384529", 1)
        assertRun("28946753", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("149245887792", 1)
        assertRun("519044017360", 0, true)
    }

    // Cup 1 is at index 0 in the list of cups.
    private val indexOffset = 1

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val rawCups = input[0].map { it.digitToInt() }
        val totalCups = if (part.isOne()) rawCups.size else 1_000_000

        // 0-based not 1-baesd
        val nextCups = MutableList(totalCups) { 0 }
        for (index in rawCups.indices) {
            val nextCup = if (part.isOne() && index == rawCups.lastIndex) {
                rawCups[0]
            } else if (part.isTwo() && index == rawCups.lastIndex) {
                rawCups.size + 1
            } else {
                rawCups[index + 1]
            }
            nextCups[rawCups[index] - indexOffset] = nextCup
        }
        if (part.isTwo()) {
            for (cup in rawCups.size + 1..totalCups) {
                nextCups[cup - indexOffset] = if (cup == totalCups) rawCups[0] else (cup + 1)
            }
        }

        var current = rawCups[0]
        val times = if (part.isOne()) 100 else 10_000_000
        repeat(times) {
            current = move(current, nextCups)
        }

        if (part.isOne()) {
            val result = StringBuilder()
            current = 1
            while (current.toString() !in result) {
                result.append(current)
                current = nextCups[current - indexOffset]
            }
            return result.toString().drop(1)
        }

        val first = nextCups[1 - indexOffset]
        val second = nextCups[first - indexOffset].toLong()
        return (first * second).toString()

    }

    /**
     * Does 1 turn in the game and returns the current cup.
     */
    private fun move(cup0: Int, nextCups: MutableList<Int>): Int {
        val cup1 = nextCups[cup0 - indexOffset]
        val cup2 = nextCups[cup1 - indexOffset]
        val cup3 = nextCups[cup2 - indexOffset]
        val minCup = 1
        val maxCup = nextCups.size

        var destination = if (cup0 == 1) maxCup else (cup0 - 1)
        while (destination == cup1 || destination == cup2 || destination == cup3) {
            destination = if (destination == minCup) maxCup else (destination - 1)
        }

        // Extract and reinsert sublist.
        nextCups[cup0 - indexOffset] = nextCups[cup3 - indexOffset]
        nextCups[cup3 - indexOffset] = nextCups[destination - indexOffset]
        nextCups[destination - indexOffset] = cup1
        return nextCups[cup0 - indexOffset]
    }
}