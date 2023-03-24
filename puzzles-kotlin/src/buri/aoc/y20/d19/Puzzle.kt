package buri.aoc.y20.d19

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
        assertRun(2, 1)
        assertRun(124, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(12, 2)
        assertRun(228, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val divider = input.indexOf("")
        val rules = mutableMapOf<Int, String>()
        for (line in input.subList(0, divider)) {
            val tokens = line.replace("\"", "").split(": ")
            rules[tokens[0].toInt()] = tokens[1]
        }

        val pattern = buildPattern(part, rules, mutableMapOf(), 0)
        return input.subList(divider + 1, input.size).count { pattern.matches(it) }
    }

    /**
     * Recursively builds a pattern.
     */
    private fun buildPattern(part: Part, rules: Map<Int, String>, cache: MutableMap<Int, String>, key: Int): Regex {
        if (cache[key] == null) {
            val rule = rules[key]!!
            if (rule[0].isLetter()) {
                cache[key] = rule
            } else {
                val pattern = StringBuilder("(")

                // 8: 42 | 42 8 is the same as 8: 42*n
                if (part.isTwo() && key == 8) {
                    pattern.append(buildPattern(part, rules, cache, 42))
                    pattern.append("+")
                }

                // 11: 42 31 | 42 11 31 is the same as 11: 42*n 31*n
                else if (part.isTwo() && key == 11) {
                    // Only need to go deep enough for our puzzle input message length.
                    for (i in 1 until 6) {
                        if (i > 1) {
                            pattern.append('|')
                        }
                        pattern.append('(')
                        for (n in 0 until i) {
                            pattern.append(buildPattern(part, rules, cache, 42))
                        }
                        for (n in 0 until i) {
                            pattern.append(buildPattern(part, rules, cache, 31))
                        }
                        pattern.append(')')
                    }
                }
                // All other rules
                else {
                    for (token in rule.split(" ")) {
                        if (token == "|") {
                            pattern.append(token)
                        } else {
                            pattern.append(buildPattern(part, rules, cache, token.toInt()))
                        }
                    }
                }
                pattern.append(")")
                cache[key] = pattern.toString()
            }
        }
        return cache[key]!!.toRegex()
    }
}