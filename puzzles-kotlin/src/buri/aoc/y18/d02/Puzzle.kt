package buri.aoc.y18.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.*
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("5658", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("nmgyjkpruszlbaqwficavxneo", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        if (part == ONE) {
            var twoCount = 0
            var threeCount = 0
            for (line in input) {
                val frequency = mutableMapOf<Char, Int>()
                for (value in line) {
                    frequency.putIfAbsent(value, 0)
                    frequency[value] = frequency[value]!! + 1
                }
                if (frequency.containsValue(2)) {
                    twoCount++
                }
                if (frequency.containsValue(3)) {
                    threeCount++
                }
            }
            return (twoCount * threeCount).toString()
        }

        for (line1 in input) {
            for (line2 in input.filter { it != line1 }) {
                val builder = StringBuilder()
                for (i in line1.indices) {
                    if (line1[i] == line2[i]) {
                        builder.append(line1[i])
                    }
                }
                if (builder.length == line1.length - 1) {
                    return builder.toString()
                }
            }
        }
        return ""
    }


}