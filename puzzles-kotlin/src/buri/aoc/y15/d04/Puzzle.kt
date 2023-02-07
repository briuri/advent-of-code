package buri.aoc.y15.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.MD5
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
        assertRun(609043, 1)
        assertRun(117946, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(6742839, 1)
        assertRun(3938038, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val md5 = MD5()
        val leadingZeroes = if (part == ONE) 5 else 6
        var answer = 0
        while (true) {
            val hash = md5.getHashWithLeadingZeroes(input[0] + answer.toString(), leadingZeroes)
            if (hash.isNotEmpty()) {
                return answer
            }
            answer++
        }
    }
}