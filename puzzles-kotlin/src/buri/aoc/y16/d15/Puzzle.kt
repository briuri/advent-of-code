package buri.aoc.y16.d15

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(5, 1)
        assertRun(203660, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2408135, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val discs = mutableMapOf<Int, Disc>()
        for (line in input) {
            val num = line[6].digitToInt()
            val total = line.split(" ")[3].toInt()
            val start = line.split(" ")[11].dropLast(1).toInt()
            discs[num] = Disc(total, start)
        }
        if (part == TWO) {
            discs[discs.keys.max() + 1] = Disc(11, 0)
        }

        var time = 0
        while (true) {
            if (discs.all { it.value.getPositionAt(time + it.key) == 0 }) {
                return time
            }
            time++
        }
    }
}

data class Disc(val total: Int, val start: Int) {
    /**
     * Returns the position at the given time.
     */
    fun getPositionAt(time: Int): Int {
        return (start + time) % total
    }
}