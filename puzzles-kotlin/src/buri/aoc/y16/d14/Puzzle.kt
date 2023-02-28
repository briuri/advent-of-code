package buri.aoc.y16.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.MD5
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
        // Skipping example to reduce test suite time.
        // assertRun(22728, 1)
        assertRun(15168, 0, true)
    }

    @Test
    fun runPart2() {
        // Skipping example to reduce test suite time.
        // assertRun(22551, 1)
        assertRun(20864, 0, true)
    }

    private val tripleRegex = "([a-f0-9])\\1{2}".toRegex()
    private val quintRegexs = mutableMapOf<Char, Regex>()

    // Create a pattern for each possible hex digit.
    init {
        for (value in "0123456789abcdef") {
            quintRegexs[value] = "($value)\\1{4}".toRegex()
        }
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val md5 = MD5()
        val salt = input[0]
        val stretches = if (part.isOne()) 0 else 2016

        val hashes = mutableMapOf<Int, String>()
        var index = 0
        var keys = 0
        while (keys != 64) {
            loadHash(hashes, md5, salt, index, stretches)
            val firstTriple = tripleRegex.find(hashes[index]!!)
            if (firstTriple != null) {
                for (quintIndex in index + 1 until index + 1000) {
                    loadHash(hashes, md5, salt, quintIndex, stretches)
                    if (quintRegexs[firstTriple.value[0]]!!.containsMatchIn(hashes[quintIndex]!!)) {
                        keys++
                        break
                    }
                }
            }
            index++
        }
        return index - 1
    }

    /**
     * Loads a hash into the cache if not already there.
     */
    private fun loadHash(hashes: MutableMap<Int, String>, md5: MD5, salt: String, index: Int, stretches: Int) {
        if (hashes[index] == null) {
            hashes[index] = md5.getHash(salt + index, stretches)
        }
    }
}