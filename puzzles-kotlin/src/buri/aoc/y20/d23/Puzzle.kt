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
        // Skipping example to reduce test suite time.
//        assertRun("149245887792", 1)
        assertRun("519044017360", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val nextCups = mutableMapOf<Int, Int>()
        val cups = input[0].map { it.digitToInt() }.toMutableList()
        if (part.isTwo()) {
            for (i in cups.size + 1 .. 1_000_000) {
                cups.add(i)
            }
        }
        for (i in cups.indices) {
            val cup = cups[i]
            val nextIndex = if (i == cups.lastIndex) 0 else i + 1
            nextCups[cup] = cups[nextIndex]
        }
        var current = cups[0]
        val times = if (part.isOne()) 100 else 10_000_000
        repeat (times) {
            current = move(current, nextCups)
        }

        if (part.isOne()) {
            val result = StringBuilder()
            current = 1
            while (current.toString() !in result) {
                result.append(current)
                current = nextCups[current]!!
            }
            return result.toString().drop(1)
        }

        val first = nextCups[1]!!
        val second = nextCups[first]!!.toLong()
        return (first * second).toString()

    }

    /**
     * Does 1 turn in the game and returns the current cup.
     */
    private fun move(cup0: Int, nextCups: MutableMap<Int, Int>): Int {
        val cup1 = nextCups[cup0]!!
        val cup2 = nextCups[cup1]!!
        val cup3 = nextCups[cup2]!!
        val minCup = 1
        val maxCup = nextCups.size

        var destination = cup0 - 1
        if (destination < minCup) {
            destination = maxCup
        }
        while (destination == cup1 || destination == cup2 || destination == cup3) {
            destination -= 1
            if (destination < minCup) {
                destination = maxCup
            }
        }

        // Extract sublist.
        nextCups[cup0] = nextCups[cup3]!!
        // Reinsert sublist.
        nextCups[cup3] = nextCups[destination]!!
        nextCups[destination] = cup1

        return nextCups[cup0]!!
    }
}