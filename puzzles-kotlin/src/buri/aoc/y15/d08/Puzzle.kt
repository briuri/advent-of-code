package buri.aoc.y15.d08

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
        assertRun(1333, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(2046, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var originalSize = 0
        var decodeSize = 0
        var encodeSize = 0
        for (line in input) {
            originalSize += line.trim().length

            var string = line.replace("""\\""", "#")
            string = string.replace("""\"""", "#")
            string = string.replace(""""""", "")
            string = string.replace(Regex("""\\x[a-f0-9][a-f0-9]"""), "#")
            decodeSize += string.length

            string = line.replace("""\""", "##")
            string = string.replace(""""""", "##")
            string = "#$string#"
            encodeSize += string.length
        }
        if (part == ONE) {
            return originalSize - decodeSize
        }
        return encodeSize - originalSize
    }
}