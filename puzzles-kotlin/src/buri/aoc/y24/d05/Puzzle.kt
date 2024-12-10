package buri.aoc.y24.d05

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(143, 1)
        assertRun(5964, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(123, 1)
        assertRun(4719, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val beforeRules = mutableMapOf<Int, MutableList<Int>>()
        val afterRules = mutableMapOf<Int, MutableList<Int>>()
        val spaceIndex = input.indexOf("")
        for (line in input.subList(0, spaceIndex)) {
            val tokens = line.extractInts()
            beforeRules.putIfAbsent(tokens[0], mutableListOf())
            afterRules.putIfAbsent(tokens[1], mutableListOf())
            beforeRules[tokens[0]]!!.add(tokens[1])
            afterRules[tokens[1]]!!.add(tokens[0])
        }

        var middlePages = 0
        for (update in input.drop(spaceIndex + 1)) {
            val pages = update.extractInts()
            val sortedPages = pages.getCorrection(beforeRules, afterRules)
            // In Part One, only include pages already in the right order.
            if (part.isOne() && pages == sortedPages) {
                middlePages += sortedPages[sortedPages.size / 2]
            }
            // In Part Two, only include pages that were incorrect.
            if (part.isTwo() && pages != sortedPages) {
                middlePages += sortedPages[sortedPages.size / 2]
            }
        }
        return middlePages
    }

    /**
     * Sorts the pages into the right order by moving the first incorrect page one position back.
     */
    private fun List<Int>.getCorrection(beforeRules: Map<Int, List<Int>>, afterRules: Map<Int, List<Int>>): List<Int> {
        for (i in 0..<this.lastIndex) {
            val current = this[i]
            val next = this[i + 1]
            if (beforeRules[current]?.contains(next) == false || afterRules[next]?.contains(current) == false) {
                val swap = mutableListOf<Int>()
                swap.addAll(this)
                swap.removeAt(i + 1)
                swap.add(i, next)
                return swap.getCorrection(beforeRules, afterRules)
            }
        }
        return this
    }
}