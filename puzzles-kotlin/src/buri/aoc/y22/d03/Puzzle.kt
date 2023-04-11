package buri.aoc.y22.d03

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
        assertRun(8515, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2434, 0, true)
    }

    private val priorities = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part.isOne()) {
            return input.sumOf { findDupe(it) }
        }
        var sum = 0
        for (subList in input.chunked(3)) {
            sum += findBadge(subList)
        }
        return sum
    }

    /**
     * Finds the item that appears twice.
     */
    private fun findDupe(sack: String): Int {
        val left = sack.substring(0, sack.length / 2).toSet()
        val right = sack.substring(sack.length / 2).toSet()
        val dupe = left.intersect(right).first()
        return priorities.indexOf(dupe)
    }

    private fun findBadge(sacks: List<String>): Int {
        val badge = sacks[0].toSet().intersect(sacks[1].toSet()).intersect(sacks[2].toSet()).first()
        return priorities.indexOf(badge)
    }
}