package buri.aoc.y20.d16

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
        assertRun(71, 1)
        assertRun(24110, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(6766503490793, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val rules = mutableListOf<Rule>()
        val ruleSpace = input.indexOf("")
        for (line in input.subList(0, ruleSpace)) {
            rules.add(Rule(line))
        }
        val yourTicket = Ticket(input[ruleSpace + 2])
        var tickets = mutableListOf<Ticket>()
        for (line in input.subList(ruleSpace + 5, input.size)) {
            tickets.add(Ticket(line))
        }

        if (part.isOne()) {
            return tickets.sumOf { it.getInvalidValues(rules) }
        }

        // Discard invalid tickets.
        tickets = tickets.filter { it.getInvalidValues(rules) == 0 }.toMutableList()

        // Group field values from all tickets by index
        val fields = mutableMapOf<Int, MutableList<Int>>()
        for (ticket in tickets) {
            for ((index, value) in ticket.values.withIndex()) {
                fields.putIfAbsent(index, mutableListOf())
                fields[index]!!.add(value)
            }
        }

        // Identify fields until we have found the 6 departure fields.
        val departureFields = mutableListOf<Long>()
        while (departureFields.size < 6) {
            for ((index, values) in fields) {
                val rulesMet = rules.filter { it.isValid(values) }
                if (rulesMet.size == 1) {
                    val rule = rulesMet.first()
                    rules.remove(rule)
                    if (rule.name.startsWith("departure")) {
                        departureFields.add(yourTicket[index].toLong())
                    }
                }
            }
        }
        return departureFields.reduce { acc, i -> acc * i }
    }
}

class Rule(data: String) {
    val name = data.split(": ")[0]
    private val lowRange: IntRange
    private val highRange: IntRange

    init {
        val ranges = data.extractInts(false)
        lowRange = ranges[0]..ranges[1]
        highRange = ranges[2]..ranges[3]
    }

    /**
     * Checks if every value satisfies this rule.
     */
    fun isValid(values: List<Int>): Boolean = values.all { isValid(it) }

    /**
     * Checks if a value satisfies this rule.
     */
    fun isValid(value: Int): Boolean = value in lowRange || value in highRange

    override fun toString(): String = "$name: $lowRange or $highRange"
}

class Ticket(data: String) {
    val values = data.extractInts(false)

    /**
     * Returns a value at an index.
     */
    operator fun get(index: Int): Int = values[index]

    /**
     * Returns the sum of any fields that don't work for any rules.
     */
    fun getInvalidValues(rules: List<Rule>): Int {
        var invalid = 0
        for (value in values) {
            if (rules.count { it.isValid(value) } == 0) {
                invalid += value
            }
        }
        return invalid
    }

    override fun toString(): String = "$values"
}