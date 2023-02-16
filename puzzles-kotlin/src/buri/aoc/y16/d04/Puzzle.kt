package buri.aoc.y16.d04

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(1514, 1)
        assertRun(137896, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(501, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var realSum = 0
        for (line in input) {
            val frequencies = mutableMapOf<Char, Int>()
            val name = line.substring(0, line.lastIndexOf("-"))
            val id = line.substring(line.lastIndexOf("-") + 1, line.indexOf("[")).toInt()
            val checksum = line.substring(line.indexOf("[") + 1, line.lastIndex)
            // Ignore hyphens when counting frequencies
            for (value in name.filter { it.isLowerCase() }) {
                frequencies.putIfAbsent(value, 0)
                frequencies[value] = frequencies[value]!! + 1
            }

            val testChecksum = StringBuilder()
            // Max frequency will be at the beginning
            for (count in frequencies.values.distinct().sorted().reversed()) {
                // When multiple letters have same frequency go in alphabetical order
                for (key in frequencies.keys.sorted()) {
                    if (frequencies[key] == count) {
                        testChecksum.append(key)
                    }
                }
            }
            if (testChecksum.substring(0, 5) == checksum) {
                realSum += id
                val unencrypted = rotate(name, id)
                if (part == TWO && unencrypted == "northpole object storage") {
                    return id
                }
            }
        }
        return realSum
    }

    /**
     * Decrypts the name using the sector ID
     */
    private fun rotate(name: String, id: Int): String {
        val rotation = id % 26
        var next = ""
        for (i in name.indices) {
            if (name[i] == '-') {
                next += ' '
            } else {
                var value = name[i] + rotation
                if (value > 'z') {
                    value -= 26
                }
                next += value
            }
        }
        return next
    }
}