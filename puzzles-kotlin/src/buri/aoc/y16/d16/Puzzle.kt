package buri.aoc.y16.d16

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("10100101010101101", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("01100001101101001", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val diskSpace = if (part == ONE) 272 else 35651584
        var a = input[0].toCharArray()
        while (a.size < diskSpace) {
            val b = a.reversed().map { if (it == '0') '1' else '0' }
            a = a.plus('0').plus(b)
        }
        var checksum = getChecksum(a.sliceArray(0 until diskSpace))
        while (checksum.size % 2 == 0) {
            checksum = getChecksum(checksum)
        }
        return checksum.joinToString("")
    }

    /**
     * Generates a checksum for some string of 1s and 0s
     */
    private fun getChecksum(string: CharArray): CharArray {
        val checksum = CharArray(string.size / 2)
        for ((j, i) in (string.indices step 2).withIndex()) {
            val next = if (string[i] == string[i + 1]) '1' else '0'
            checksum[j] = next
        }
        return checksum
    }
}