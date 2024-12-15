package buri.aoc.y24.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(55312, 1)
        assertRun(222461, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(264350935776416, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var counts = mutableMapOf<Long, Long>()
        for (stone in input.first().extractLongs()) {
            counts.putIfAbsent(stone, 0)
            counts[stone] = counts[stone]!! + 1
        }

        val rounds = if (part.isOne()) 25 else 75
        for (i in 0..<rounds) {
            val nextCounts = mutableMapOf<Long, Long>()
            for ((stone, count) in counts) {
                for (blinkedStone in blink(stone)) {
                    nextCounts.putIfAbsent(blinkedStone, 0)
                    nextCounts[blinkedStone] = nextCounts[blinkedStone]!! + count
                }
            }
            counts = nextCounts
        }
        return counts.values.sum()
    }

    /**
     * Blinks a single stone.
     */
    private fun blink(stone: Long): List<Long> {
        val newStones = mutableListOf<Long>()
        val length = stone.toString().length
        if (stone == 0L) {
            newStones.add(1L)
        } else if (length % 2 == 0) {
            val split = length / 2
            newStones.add(stone.toString().dropLast(split).toLong())
            newStones.add(stone.toString().drop(split).toLong())
        } else {
            newStones.add(2024L * stone)
        }
        return newStones
    }
}