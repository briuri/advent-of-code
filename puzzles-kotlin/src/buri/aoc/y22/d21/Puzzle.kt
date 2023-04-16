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
        do {
            val beforeSize = remaining.size
            for (monkey in remaining.filter { isNumber(it.operation) }) {
                for (changeMonkey in monkeys.values) {
                    changeMonkey.reduce(monkey)
                }
                remaining.remove(monkey)
            }
        } while (beforeSize != remaining.size)

        val root = monkeys["root"]!!
        if (part.isOne()) {
            return root.operation.toLong()
        }

        var tokens = root.operation.split(" = ")
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

class Monkey(part: Part, data: String) {
    val name: String
    var operation: String
    private val alphaPattern = "\\p{Alpha}".toRegex()

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
            val result = when (tokens[1]) {
                "+" -> left + right
                "-" -> left - right
                "*" -> left * right
                else -> left / right
            }
            operation = result.toString()
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