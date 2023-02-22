package buri.aoc.y15.d19

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
        assertRun(509, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(195, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val string = input[input.lastIndex]
        val rules = mutableListOf<Pair<String, String>>()
        for (line in input) {
            if (line.isEmpty()) {
                break
            }
            val tokens = line.split(" => ")
            rules.add(Pair(tokens[0], tokens[1]))
        }

        if (part.isOne()) {
            val uniqueChanges = mutableSetOf<String>()
            for (rule in rules) {
                for (i in findOccurences(string, rule.first)) {
                    val change = string.substring(0 until i) +
                            rule.second +
                            string.substring(i + rule.first.length, string.length)
                    uniqueChanges.add(change)
                }
            }
            return uniqueChanges.size
        }

        while (true) {
            var steps = 0
            var current = string
            // Try random permutations.
            rules.shuffle()
            while (true) {
                val previous = current
                for (rule in rules) {
                    for (i in findOccurences(current, rule.second)) {
                        current = current.substring(0 until i) +
                                rule.first +
                                current.substring(i + rule.second.length, current.length)
                        steps++
                    }
                }
                when (current) {
                    "e" -> return steps
                    previous -> break
                }
            }
        }
    }

    /**
     * Finds all occurrences of a token within a string and returns in reverse order.
     */
    private fun findOccurences(string: String, token: String): List<Int> {
        val list = mutableListOf<Int>()
        var index = string.indexOf(token)
        while (index != -1) {
            list.add(index)
            index = string.indexOf(token, index + 1)
        }
        return list.reversed()
    }
}