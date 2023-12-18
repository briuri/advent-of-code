package buri.aoc.y23.d15

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
        assertRun(1320, 1)
        assertRun(515974, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(145, 1)
        assertRun(265894, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var sum = 0
        if (part.isOne()) {
            for (label in input[0].split(",")) {
                sum += toHash(label)
            }
        } else {
            val boxes = mutableMapOf<Int, MutableList<String>>()
            for (i in 0..255) {
                boxes[i] = mutableListOf()
            }

            for (token in input[0].split(",")) {
                val label = if (token.contains("-")) {
                    token.dropLast(1)
                } else {
                    token.substring(0, token.indexOf("="))
                }
                val focalLength = if (token.contains("-")) {
                    0
                } else {
                    token.substring(token.indexOf("=") + 1).toInt()
                }

                val box = toHash(label)
                val lenses = boxes[box]!!
                val lens = lenses.firstOrNull { it.startsWith(label) }
                when (focalLength) {
                    0 -> {
                        lenses.remove(lens)
                    }
                    else -> {
                        if (lens != null) {
                            lenses[lenses.indexOf(lens)] = token
                        } else {
                            lenses.add(token)
                        }
                    }
                }
            }

            for ((box, lenses) in boxes) {
                for ((slot, lens) in lenses.withIndex()) {
                    val power = (1 + box) * (slot + 1) * lens.split("=")[1].toInt()
                    sum += power
                }
            }
        }
        return sum
    }

    /**
     * Converts a string into a HASH value.
     */
    private fun toHash(label: String): Int {
        var value = 0
        for (c in label) {
            value += c.code
            value *= 17
            value %= 256
        }
        return value
    }
}