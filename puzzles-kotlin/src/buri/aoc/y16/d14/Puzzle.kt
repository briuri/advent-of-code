package buri.aoc.y16.d14

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
        assertRun(22728, 1)
        assertRun(15168, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(22551, 1)
        assertRun(20864, 0, true)
    }

    private val tripleRegEx = "([a-f0-9])\\1{2}".toRegex()

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val md5 = MD5()
        val salt = input[0]
        val stretches = if (part == ONE) 0 else 2016

        val hashes = mutableMapOf<Int, String>()
        var index = 0
        var keys = 0
        while (keys != 64) {
            if (hashes[index] == null) {
                hashes[index] = md5.getHash(salt + index, stretches)
            }
            val firstTriple = tripleRegEx.find(hashes[index]!!)
            if (firstTriple != null) {
                val repeating = firstTriple.value[0]
                for (quintIndex in index + 1 until index + 1000) {
                    if (hashes[quintIndex] == null) {
                        hashes[quintIndex] = md5.getHash(salt + quintIndex, stretches)
                    }
                    val quintRegEx = "($repeating)\\1{4}".toRegex()
                    if (quintRegEx.containsMatchIn(hashes[quintIndex]!!)) {
                        keys++
                        break
                    }
                }
            }
            index++
        }
        return index - 1
    }
}