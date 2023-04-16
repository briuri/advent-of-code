package buri.aoc.y22.d21

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.y22.d21.Monkey.Companion.isNumber
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(152, 1)
        assertRun(194058098264286, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(301, 1)
        assertRun(3592056845086, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val monkeys = mutableMapOf<String, Monkey>()
        for (line in input) {
            val monkey = Monkey(part, line)
            monkeys[monkey.name] = monkey
        }
        if (part.isTwo()) {
            monkeys.remove("humn")
        }

        val remaining = mutableListOf<Monkey>()
        remaining.addAll(monkeys.values)
        while (true) {
            val originalSize = remaining.size
            val processed = mutableSetOf<Monkey>()
            for (monkey in remaining) {
                if (isNumber(monkey.operation)) {
                    for (changeMonkey in monkeys.values) {
                        changeMonkey.reduce(monkey)
                    }
                    processed.add(monkey)
                }
            }
            remaining.removeAll(processed)

            // No change
            if (originalSize == remaining.size) {
                break
            }
        }

        if (part.isOne()) {
            return monkeys["root"]!!.operation.toLong()
        }

        var tokens = monkeys["root"]!!.operation.split(" = ")
        var name = if (isNumber(tokens[0])) tokens[1] else tokens[0]
        var targetValue = if (isNumber(tokens[0])) tokens[0].toLong() else tokens[1].toLong()

        while (name != "humn") {
            tokens = monkeys[name]!!.operation.split(" ")
            var simpleValue: Long
            try {
                simpleValue = tokens[0].toLong()
                name = tokens[2]
                when (tokens[1]) {
                    "-" -> targetValue = simpleValue - targetValue
                    "/" -> targetValue = simpleValue / targetValue
                }
            } catch (e: NumberFormatException) {
                simpleValue = tokens[2].toLong()
                name = tokens[0]
                when (tokens[1]) {
                    "-" -> targetValue += simpleValue
                    "/" -> targetValue *= simpleValue
                }
            }
            when (tokens[1]) {
                "+" -> targetValue -= simpleValue
                "*" -> targetValue /= simpleValue
            }
        }
        return targetValue
    }
}

val alphaPattern = "\\p{Alpha}".toRegex()

class Monkey(part: Part, data: String) {
    val name: String
    var operation: String

    init {
        val tokens = data.split(": ")
        name = tokens[0]
        operation = if (part.isTwo() && name == "root") {
            val operand = tokens[1].split(" ")[1]
            tokens[1].replace(operand, "=")
        } else {
            tokens[1]
        }
    }

    /**
     * Replaces any references to this monkey.
     */
    fun reduce(monkey: Monkey) {
        operation = operation.replace(monkey.name, monkey.operation)
        if (alphaPattern.find(operation) == null && !isNumber(operation)) {
            val tokens = operation.split(" ")
            val left = tokens[0].toLong()
            val right = tokens[2].toLong()
            when (tokens[1]) {
                "+" -> operation = (left + right).toString()
                "-" -> operation = (left - right).toString()
                "*" -> operation = (left * right).toString()
                "/" -> operation = (left / right).toString()
            }
        }
    }

    companion object {
        /**
         * Returns true if the string has a numeric value.
         */
        fun isNumber(value: String): Boolean {
            return try {
                value.toLong()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
    }
}