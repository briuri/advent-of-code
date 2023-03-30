package buri.aoc.y21.d14

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
        assertRun(1588, 1)
        assertRun(2975, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2188189693529, 1)
        assertRun(3015383850689, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val rules = mutableMapOf<String, Char>()
        for (line in input.subList(2, input.size)) {
            val tokens = line.split(" -> ")
            rules[tokens[0]] = tokens[1][0]
        }

        val template = input[0].toList()
        var pairs = mutableMapOf<String, Long>()
        for (i in 0 until template.lastIndex) {
            val key = "${template[i]}${template[i + 1]}"
            pairs.add(key, 1)
        }

        val times = if (part.isOne()) 10 else 40
        repeat(times) {
            val nextPairs = mutableMapOf<String, Long>()
            for ((pair, count) in pairs) {
                val next = rules[pair]!!
                nextPairs.add("${pair[0]}$next", count)
                nextPairs.add("$next${pair[1]}", count)
            }
            pairs = nextPairs
        }

        // Each pair overlaps so letter counts are doubled.
        val doubledCounts = mutableMapOf<String, Long>()
        for ((pair, count) in pairs) {
            for (value in pair) {
                doubledCounts.add(value.toString(), count)
            }
        }
        // Add extra first / last letter so everything is doubled.
        doubledCounts.add(template.first().toString(), 1)
        doubledCounts.add(template.last().toString(), 1)
        return ((doubledCounts.values.max() - doubledCounts.values.min()) / 2)
    }

    /**
     * Adds a pair into the map.
     */
    private fun MutableMap<String, Long>.add(key: String, value: Long) {
        this.putIfAbsent(key, 0L)
        this[key] = this[key]!! + value
    }
}