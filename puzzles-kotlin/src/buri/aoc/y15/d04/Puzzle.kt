package buri.aoc.y15.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

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
        val key = input[0]
        val target = if (part == ONE) "00000" else "000000"
        var answer = 0
        while (true) {
            val hash = getHash(key + answer.toString())
            if (hash.startsWith(target)) {
                return answer
            }
            answer++
        }
    }

    /**
     * Creates an MD5 hash of some string.
     */
    private fun getHash(input: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return DatatypeConverter.printHexBinary(bytes)
    }
}