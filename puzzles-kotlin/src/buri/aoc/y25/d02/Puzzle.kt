package buri.aoc.y25.d02

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
        assertRun(1227775554, 1)
        assertRun(44854383294, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4174379265, 1)
        assertRun(55647141923, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val ranges = mutableListOf<LongRange>()
        for (range in input[0].split(",")) {
            val numbers = range.split("-")
            ranges.add(LongRange(numbers[0].toLong(), numbers[1].toLong()))
        }

        var sum = 0L
        for (range in ranges) {
            for (id in range) {
                val idString = id.toString()
                if (part.isOne() && idString.length % 2 != 0) {
                    continue
                }
                val start = if (part.isOne() && idString.length % 2 == 0) {
                    idString.length / 2
                } else {
                    1
                }
                for (j in start..idString.length / 2) {
                    val ids = idString.chunked(j)
                    if (ids.toSet().size == 1) {
                        sum += id
                        break
                    }
                }
            }
        }
        return sum
    }
}