package buri.aoc.y20.d05

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
        assertRun(901, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(661, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val seats = mutableListOf<Int>()
        for (line in input) {
            val row = line.take(7).replace('F', '0').replace('B', '1').toInt(2)
            val column = line.takeLast(3).replace('L', '0').replace('R', '1').toInt(2)
            seats.add(row * 8 + column)
        }
        if (part.isOne()) {
            return seats.max()
        }

        seats.sort()
        for (i in 1 until seats.size) {
            if (seats[i] - 1 != seats[i - 1]) {
                return seats[i] - 1
            }
        }
        return -1
    }
}