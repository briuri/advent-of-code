package buri.aoc.y22.d25

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun("2=-1=0", 1)
        assertRun("2=-0=1-0012-=-2=0=01", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        return toSnafu(input.sumOf { toLong(it) })
    }

    /**
     * Converts from snafu to long.
     */
    private fun toLong(snafu: String): Long {
        var total = 0L
        for (i in snafu.lastIndex downTo 0) {
            val place = 5.toDouble().pow(snafu.lastIndex - i).toLong()
            when (snafu[i]) {
                '=' -> total -= place * 2
                '-' -> total -= place
                '1' -> total += place
                '2' -> total += place * 2
            }
        }
        return total
    }

    /**
     * Converts from long to snafu.
     */
    private fun toSnafu(value: Long): String {
        var remaining = value
        val output = StringBuilder()
        while (remaining != 0L) {
            when (remaining % 5) {
                0L -> output.append("0")
                1L -> {
                    output.append("1")
                    remaining -= 1
                }

                2L -> {
                    output.append("2")
                    remaining -= 2
                }

                3L -> {
                    output.append("=")
                    remaining += 2
                }

                4L -> {
                    output.append("-")
                    remaining += 1
                }
            }
            remaining /= 5
        }
        return output.reverse().toString()
    }
}