package buri.aoc.y16.d09

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
        assertRun(57, 1)
        assertRun(120765, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(242394, 2)
        assertRun(11658395076, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        return input.sumOf { getDecompressedLength(part, it) }
    }

    /**
     * Recursively figures out the length of a decompressed string.
     */
    private fun getDecompressedLength(part: Part, string: String): Long {
        if (!string.contains('(')) {
            return string.length.toLong()
        }

        var size = 0L
        var i = 0
        while (i < string.length) {
            // Seek ahead to first marker
            if (string[i] != '(') {
                size++
                i++
            } else {
                val endMarker = string.indexOf(')', i)
                val marker = string.substring(i + 1, endMarker).split("x")
                val numChars = marker[0].toInt()
                val times = marker[1].toLong()

                // Calculate decompression.
                i = endMarker + 1
                val repeated = string.substring(i, i + numChars)
                val repeatedSize = if (part.isOne()) repeated.length.toLong() else getDecompressedLength(part, repeated)
                size += repeatedSize * times
                i += repeated.length
            }
        }
        return size
    }
}