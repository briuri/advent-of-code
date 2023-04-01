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
            var equation = input[0]
            for (addend in input.drop(1)) {
                equation = "[$equation,$addend]".reduce()
            }
            return equation.magnitude()
        }

        var maxMagnitude = 0L
        for (addend1 in input) {
            for (addend2 in input.filter { it != addend1 }) {
                maxMagnitude = maxMagnitude.coerceAtLeast("[$addend1,$addend2]".reduce().magnitude())
            }
        }
        return maxMagnitude
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
            reduced = reduced.replace(pairMatch, localMagnitude)
            pairMatch = simplePairPattern.find(reduced)
        }
        return reduced.toLong()
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
                // Find a pair needed to explode before the depth decreases again.
                val nextClosing = reduced.indexOf("]", i + 1)
                val pairMatch = simplePairPattern.find(reduced, i + 1)
                if (pairMatch != null && nextClosing in pairMatch.range) {
                    val numbers = reduced.substring(pairMatch.range).extractLongs()

                    // Find number to the right of pair and add right value.
                    val rightMatch = literalPattern.find(reduced, pairMatch.range.last + 1)
                    if (rightMatch != null) {
                        val right = numbers[1] + reduced.substring(rightMatch.range).toLong()
                        reduced = reduced.replace(rightMatch, right)
                    }

                    // Replace the original pair with 0.
                    reduced = reduced.replace(pairMatch, 0L)

                    // Find number to left of pair and add left value.
                    var leftMatch = literalPattern.find(reduced.substring(0, pairMatch.range.first))
                    if (leftMatch != null) {
                        while (leftMatch!!.next() != null) {
                            leftMatch = leftMatch.next()
                        }
                        val left = numbers[0] + reduced.substring(leftMatch.range).toLong()
                        reduced = reduced.replace(leftMatch, left)
                    }

                    // Clean up.
                    while (true) {
                        val cleaned = reduced.cleanup()
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
        val number = reduced.extractLongs().firstOrNull { it >= 10 }
        if (number != null) {
            val left = number / 2
            val right = number / 2 + (if (number % 2 == 0L) 0 else 1)
            reduced = reduced.replaceFirst(number.toString(), "[$left,$right]")
        }
        return reduced
    }

    /**
     * Removes any orphaned numbers or commas / braces.
     */
    private fun String.cleanup(): String {
        var reduced = this
        reduced = reduced.replace("[]", "")
        reduced = reduced.replace(",]", "]")
        reduced = reduced.replace("[,", "[")

        val orphans = mutableListOf<MatchResult?>()
        orphans.add(orphanOnePattern.find(reduced))
        orphans.add(orphanNonePattern.find(reduced))
        for (match in orphans.filterNotNull()) {
            val orphan = reduced.substring(match.range).extractLongs()[0]
            reduced = reduced.replace(match, orphan)
        }
        return reduced
    }

    /**
     * Simplifies a matched value to be a simple number and returns the modified string.
     */
    private fun String.replace(match: MatchResult, value: Long): String {
        return this.replaceRange(match.range, value.toString())
    }
}