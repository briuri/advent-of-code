package buri.aoc.y16.d05

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
        // Skipping example to reduce test suite time.
        // assertRun("18f47a30", 1)
        assertRun("2414bc77", 0, true)
    }

    @Test
    fun runPart2() {
        // Skipping example to reduce test suite time.
        // assertRun("05ace8e3", 1)
        assertRun("437e60fc", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val md5 = MD5()
        val password = StringBuilder("########")
        var index = 0
        while (password.contains('#')) {
            val hash = md5.getHashWithLeadingZeroes(input[0] + index.toString(), 5)
            if (hash.isNotEmpty()) {
                if (part == ONE) {
                    password[password.indexOf("#")] = hash[5]
                } else if (hash[5] in "01234567" && password[hash[5].digitToInt()] == '#') {
                    password[hash[5].digitToInt()] = hash[6]
                }
            }
            index++
        }
        return password.toString().lowercase()
    }

}