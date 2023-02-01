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

        if (part == Part.ONE) {
            val uniqueChanges = mutableSetOf<String>()
            for (rule in rules) {
                for (index in findOccurences(string, rule.first)) {
                    val change = string.substring(0 until index) + rule.second + string.substring(
                        index + rule.first.length,
                        string.length
                    )
                    uniqueChanges.add(change)
                }
            }
            return uniqueChanges.size
        }

        while (true) {
            var steps = 0
            var current = string
            rules.shuffle()
            while (true) {
                val previous = current
                for (rule in rules) {
                    for (index in findOccurences(current, rule.second)) {
                        current = current.substring(0 until index) + rule.first + current.substring(
                            index + rule.second.length,
                            current.length
                        )
                        steps++
                    }
                }
                if (current == "e") {
                    return steps
                }
                if (previous == current) {
                    break
                }
            }
        }
    }

    /**
     * Finds all occurrences of a token within a string.
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