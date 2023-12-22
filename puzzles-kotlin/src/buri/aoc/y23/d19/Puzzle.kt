package buri.aoc.y23.d19

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(19114, 1)
        assertRun(353046, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(167409079868000, 1)
        assertRun(125355665599537, 0, true)
    }

    private val firstFlow = "in"
    private val defaultRange = 1..4000

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val separatorIndex = input.indexOf("")
        val flows = mutableMapOf<String, Workflow>()
        for (line in input.subList(0, separatorIndex)) {
            val flow = Workflow(line)
            flows[flow.name] = flow
        }
        val parts = input.drop(separatorIndex + 1).map { MachinePart(it) }.toSet()

        if (part.isOne()) {
            return parts.filter { isValid(flows, it) }.sumOf { it.sum() }
        }

        val ranges = mutableMapOf('x' to defaultRange, 'm' to defaultRange, 'a' to defaultRange, 's' to defaultRange)
        return testRanges(flows, firstFlow, ranges)
    }

    /**
     * Part 1: Returns true if the part is accepted.
     */
    private fun isValid(flows: Map<String, Workflow>, part: MachinePart): Boolean {
        var current = firstFlow
        while (current !in listOf("A", "R")) {
            val flow = flows[current]!!
            for (step in flow.steps) {
                if (!step.isCondition()
                    || (step.op == '<' && part[step.key] < step.value) || (step.op == '>' && part[step.key] > step.value)
                ) {
                    current = step.target
                    break
                }
            }
        }
        return (current == "A")
    }

    /**
     * Recursively counts the distinct combinations that lead to an accepted state.
     */
    private fun testRanges(flows: Map<String, Workflow>, flowName: String, ranges: MutableMap<Char, IntRange>): Long {
        var combos = 0L
        val flow = flows[flowName]!!
        for (step in flow.steps) {
            with(step) {
                if (isCondition()) {
                    val nextRanges = ranges.toMutableMap()
                    val keyRange = ranges[key]!!
                    // Slice ranges at the test value.
                    if (op == '<' && keyRange.first < value) {
                        nextRanges[key] = keyRange.first..keyRange.last.coerceAtMost(value - 1)
                        ranges[key] = keyRange.first.coerceAtLeast(value)..keyRange.last
                    } else if (op == '>' && keyRange.last > value) {
                        ranges[key] = keyRange.first..keyRange.last.coerceAtMost(value)
                        nextRanges[key] = keyRange.first.coerceAtLeast(value + 1)..keyRange.last
                    }
                    // Recursively explore the new slices.
                    if (target == "A") {
                        combos += nextRanges.countCombos()
                    } else if (target != "R") {
                        combos += testRanges(flows, target, nextRanges)
                    }
                } else {
                    // Reached the end of this level of execution.
                    if (target == "A") {
                        combos += ranges.countCombos()
                    } else if (target != "R") {
                        combos += testRanges(flows, target, ranges)
                    }
                }
            }
        }
        return combos
    }

    /**
     * Does the math for part 2.
     */
    private fun MutableMap<Char, IntRange>.countCombos(): Long {
        return values.map { it.last - it.first + 1 }.product()
    }
}

/**
 * Data class for a workflow step.
 */
class Step(val line: String) {
    val key = if (isCondition()) line[0] else '?'
    val op = if (isCondition()) line[1] else ""
    val value = if (isCondition()) line.drop(2).split(":")[0].toInt() else 0
    val target = if (isCondition()) line.split(":")[1] else line

    /**
     * Returns true if this is a condition statement.
     */
    fun isCondition(): Boolean = line.contains(":")
}

/**
 * Data class for a workflow.
 */
class Workflow(val line: String) {
    val name: String = line.split("{")[0]
    val steps = mutableListOf<Step>()

    init {
        for (step in line.split("{")[1].dropLast(1).split(",")) {
            steps.add(Step(step))
        }
    }
}

/**
 * Data class for a part.
 */
class MachinePart(val line: String) {
    private val map = mutableMapOf<Char, Int>()

    init {
        for (token in line.drop(1).dropLast(1).split(",")) {
            map[token[0]] = token.split("=")[1].toInt()
        }
    }

    /**
     * Sums all values.
     */
    fun sum(): Int = map.values.sum()

    /**
     * Looks up a value.
     */
    operator fun get(key: Char): Int = map[key]!!
}