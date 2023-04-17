package buri.aoc.y22.d11

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.extractLongs
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(10605, 1)
        assertRun(50616, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(2713310158, 1)
        assertRun(11309046332, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val monkeys = mutableListOf<Monkey>()
        for (chunk in input.chunked(7)) {
            monkeys.add(Monkey(chunk))
        }
        val gcd = monkeys.map { it.testDivisor }.reduce { acc, i -> acc * i }

        val rounds = if (part.isOne()) 20 else 10_000
        for (round in 1..rounds) {
            for (monkey in monkeys) {
                while (monkey.hasItems()) {
                    val item = monkey.inspectItem(part, gcd)
                    val toMonkey = monkey.testItem(item)
                    monkeys[toMonkey].addItem(item)
                }
            }
        }
        val inspections = monkeys.map { it.inspectCount }.sortedDescending()
        return inspections[0] * inspections[1]
    }
}

class Monkey(data: List<String>) {
    private val operation = data[2].split(" = ")[1]
    val testDivisor = data[3].extractLongs().first()
    private val trueMonkey = data[4].extractInts().first()
    private val falseMonkey = data[5].extractInts().first()
    private val items = ArrayDeque<Long>()
    var inspectCount = 0L
        private set

    init {
        for (item in data[1].extractLongs()) {
            addItem(item)
        }
    }

    /**
     * Returns true if this monkey has items.
     */
    fun hasItems(): Boolean {
        return items.isNotEmpty()
    }

    /**
     * Removes the first item and adjusts its worry level.
     */
    fun inspectItem(part: Part, gcd: Long): Long {
        inspectCount++
        val item = items.removeFirst()
        val numbers = operation.replace("old", item.toString()).extractLongs()
        val result = if ('+' in operation) numbers.sum() else (numbers[0] * numbers[1])
        return if (part.isOne()) (result / 3) else (result % gcd)
    }

    /**
     * Tests the item to determine which monkey to throw to.
     */
    fun testItem(item: Long): Int = if (item % testDivisor == 0L) trueMonkey else falseMonkey

    /**
     * Adds item at the end.
     */
    fun addItem(item: Long) {
        items.addLast(item)
    }
}