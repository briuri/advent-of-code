package buri.aoc.y15.d15

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
        assertRun(222870, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(117936, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val totalProperties = 5
        val properties: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until totalProperties) {
            properties.add(mutableListOf())
        }

        for (line in input) {
            val tokens = line.split(" ")
            for (i in 0 until totalProperties) {
                var value = tokens[2 * (i + 1)]
                if (value.contains(",")) {
                    value = value.dropLast(1)
                }
                properties[i].add(value.toInt())
            }
        }

        var maxScore = 0
        for (a in 0..100) {
            for (b in 0..100) {
                for (c in 0..100) {
                    for (d in 0..100) {
                        if (a + b + c + d != 100) {
                            continue
                        }
                        var score = 1
                        for (i in 0 until totalProperties - 1) {
                            score *= getScore(properties[i], a, b, c, d)
                        }
                        val calories = getScore(properties[properties.lastIndex], a, b, c, d)
                        if (part.isOne() || calories == 500) {
                            maxScore = maxScore.coerceAtLeast(score)
                        }
                    }
                }
            }
        }
        return maxScore
    }

    /**
     * Computes a single property's score.
     */
    private fun getScore(values: List<Int>, a: Int, b: Int, c: Int, d: Int): Int {
        return (a * values[0] + b * values[1] + c * values[2] + d * values[3]).coerceAtLeast(0)
    }
}