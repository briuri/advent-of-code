package buri.aoc.y21.d18

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractLongs
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(4140, 1)
        assertRun(4057, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3993, 1)
        assertRun(4683, 0, true)
    }

    private val literalPattern = "\\d+".toRegex()
    private val simplePairPattern = "\\[\\d+,\\d+]".toRegex()
    private val orphanOnePattern = "\\[\\d+]".toRegex()
    private val orphanNonePattern = "\\[,]".toRegex()

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part.isOne()) {
            var data = input[0]
            for (line in input.drop(1)) {
                data = "[$data,$line]".reduce()
            }
            return data.magnitude()
        }

        var maxMagnitude = 0L
        for (line1 in input) {
            for (line2 in input.filter { it != line1 }) {
                maxMagnitude = maxMagnitude.coerceAtLeast("[$line1,$line2]".reduce().magnitude())
            }
        }
        return maxMagnitude
    }

    /**
     * Reduces a snailfish number
     */
    private fun String.reduce(): String {
        var reduced = this
        while (true) {
            while (true) {
                val exploded = reduced.explode()
                if (exploded == reduced) {
                    break
                }
                reduced = exploded
            }
            val split = reduced.split()
            if (split == reduced) {
                break
            }
            reduced = split
        }
        return reduced
    }

    /**
     * Performs an explode operation.
     */
    private fun String.explode(): String {
        var reduced = this
        var depth = 0
        for (i in reduced.indices) {
            if (reduced[i] == '[') {
                depth++
            } else if (reduced[i] == ']') {
                depth--
            }
            if (depth == 4) {
                val nextClosing = reduced.indexOf("]", i + 1)
                val pairMatch = simplePairPattern.find(reduced, i + 1)
                if (pairMatch != null && nextClosing in pairMatch.range) {
                    val numbers = reduced.substring(pairMatch.range).extractLongs()

                    // Find number to the right of pair and add right value.
                    val rightMatch = literalPattern.find(reduced, pairMatch.range.last + 1)
                    if (rightMatch != null) {
                        val right = numbers[1] + reduced.substring(rightMatch.range).toLong()
                        reduced = reduced.replaceRange(rightMatch.range, right.toString())
                    }

                    // Replace the original pair with 0.
                    reduced = reduced.replaceRange(pairMatch.range, "0")

                    // Find number to left of pair and add left value.
                    var leftMatch = literalPattern.find(reduced.substring(0, pairMatch.range.first))
                    if (leftMatch != null) {
                        while (leftMatch!!.next() != null) {
                            leftMatch = leftMatch.next()
                        }
                        val left = numbers[0] + reduced.substring(leftMatch.range).toLong()
                        reduced = reduced.replaceRange(leftMatch.range, left.toString())
                    }

                    // Clean up.
                    while (true) {
                        val cleaned = reduced.removeOrphans()
                        if (reduced == cleaned) {
                            break
                        }
                        reduced = cleaned
                    }
                    break
                }
            }
        }
        return reduced
    }

    /**
     * Performs a split operation.
     */
    private fun String.split(): String {
        var reduced = this
        for (number in reduced.extractLongs()) {
            if (number >= 10) {
                val left = number / 2
                val right = number / 2 + (if (number % 2 == 0L) 0 else 1)
                reduced = reduced.replaceFirst(number.toString(), "[$left,$right]")
                break
            }
        }
        return reduced
    }

    /**
     * Removes any orphaned numbers or commas / braces.
     */
    private fun String.removeOrphans(): String {
        var reduced = this
        reduced = reduced.replace("[]", "")
        reduced = reduced.replace(",]", "]")
        reduced = reduced.replace("[,", "[")

        val orphans = mutableListOf<MatchResult>()
        var orphanMatch = orphanOnePattern.find(reduced)
        if (orphanMatch != null) {
            orphans.add(orphanMatch)
        }
        orphanMatch = orphanNonePattern.find(reduced)
        if (orphanMatch != null) {
            orphans.add(orphanMatch)
        }
        for (match in orphans) {
            val orphan = reduced.substring(match.range).extractLongs()[0]
            reduced = reduced.replaceRange(match.range, orphan.toString())
        }
        return reduced
    }

    /**
     * Reduces all magnitudes and returns the last one.
     */
    private fun String.magnitude(): Long {
        var reduced = this
        var pairMatch = simplePairPattern.find(reduced)
        while (pairMatch != null) {
            val numbers = reduced.substring(pairMatch.range).extractLongs()
            val localMagnitude = 3 * numbers[0] + 2 * numbers[1]
            reduced = reduced.replaceRange(pairMatch.range, localMagnitude.toString())
            pairMatch = simplePairPattern.find(reduced)
        }
        return reduced.toLong()
    }
}