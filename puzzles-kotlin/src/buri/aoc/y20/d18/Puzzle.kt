package buri.aoc.y20.d18

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
        assertRun(464478013511, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(85660197232452, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        return input.sumOf { eval(part, it) }
    }

    /**
     * Recursively evaluates an equation.
     */
    private fun eval(part: Part, equation: String): Long {
        var reduced = equation
        while (reduced.contains("(")) {
            val openIndex = reduced.indexOf("(")
            val closeIndex = getClosingIndex(reduced, openIndex)

            val prefix = reduced.substring(0, openIndex)
            val nested = eval(part, reduced.substring(openIndex + 1, closeIndex))
            val suffix = reduced.substring(closeIndex + 1, reduced.length)
            reduced = prefix + nested + suffix
        }
        val tokens = reduced.split(" ").toMutableList()

        if (part.isOne()) {
            var result = tokens.removeFirst().toLong()
            while (tokens.isNotEmpty()) {
                val operation = tokens.removeFirst()
                val next = tokens.removeFirst().toLong()
                when (operation) {
                    "+" -> result += next
                    "*" -> result *= next
                }
            }
            return result
        }

        reduce(tokens, "+")
        reduce(tokens, "*")
        return tokens.first().toLong()
    }

    /**
     * Removes three values representing one operation from the tokens.
     */
    private fun reduce(tokens: MutableList<String>, operation: String) {
        while (tokens.any { it == operation }) {
            for (i in 0 until tokens.lastIndex) {
                if (tokens[i + 1] == operation) {
                    val first = tokens.removeAt(i).toLong()
                    tokens.removeAt(i)
                    val next = tokens.removeAt(i).toLong()

                    val result = if (operation == "+") (first + next) else (first * next)
                    tokens.add(i, result.toString())
                    break
                }
            }
        }
    }

    /**
     * Returns the index of the closing parenthesis that is balanced with the opening one.
     */
    private fun getClosingIndex(equation: String, openingIndex: Int): Int {
        var depth = 0
        for (i in openingIndex + 1..equation.lastIndex) {
            if (equation[i] == '(') {
                depth++
            } else if (equation[i] == ')') {
                if (depth == 0) {
                    return i
                }
                depth--
            }
        }
        return -1
    }
}