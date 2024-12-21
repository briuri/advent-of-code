package buri.aoc.y24.d19

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
        assertRun(6, 1)
        assertRun(240, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(16, 1)
        assertRun(848076019766013, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val patterns = input[0].split(", ")
        var count = 0L
        for (design in input.drop(2)) {
            if (part.isOne() && patterns.canMake(design)) {
                count++
            } else if (part.isTwo()) {
                count += patterns.countWays(design, mutableMapOf())
            }
        }
        return count
    }

    /**
     * Returns true if a design can be made from available patterns.
     */
    private fun List<String>.canMake(design: String): Boolean {
        // Base case: Empty design can be made.
        if (design.isEmpty()) {
            return true
        }
        var canMake = false
        for (pattern in this) {
            if (design.startsWith(pattern)) {
                canMake = canMake || canMake(design.drop(pattern.length))
            }
        }
        return canMake
    }


    /**
     * Counts the unique ways the design can be made from available patterns.
     */
    private fun List<String>.countWays(design: String, cache: MutableMap<String, Long>): Long {
        // Base case: Only 1 way for an empty design.
        if (design.isEmpty()) {
            return 1L
        }
        if (design !in cache) {
            var ways = 0L
            for (pattern in this) {
                if (design.startsWith(pattern)) {
                    ways += countWays(design.drop(pattern.length), cache)
                }
            }
            cache[design] = ways
        }
        return cache[design]!!
    }
}