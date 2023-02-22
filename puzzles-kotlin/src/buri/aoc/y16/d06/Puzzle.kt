package buri.aoc.y16.d06

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
        assertRun("easter", 1)
        assertRun("mlncjgdg", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("advent", 1)
        assertRun("bipjaytb", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val frequencies = Array<MutableMap<Char, Int>>(input[0].length) { mutableMapOf() }
        for (line in input) {
            for ((index, value) in line.withIndex()) {
                frequencies[index].putIfAbsent(value, 0)
                frequencies[index][value] = frequencies[index][value]!! + 1
            }
        }

        val message = StringBuilder()
        for (frequency in frequencies) {
            val minMax = if (part.isOne()) frequency.values.max() else frequency.values.min()
            for (letter in frequency.keys) {
                if (frequency[letter] == minMax) {
                    message.append(letter)
                    break
                }
            }
        }
        return message.toString()
    }
}