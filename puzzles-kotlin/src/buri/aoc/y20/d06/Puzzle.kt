package buri.aoc.y20.d06

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
        assertRun(6310, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(3193, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val groups = mutableSetOf<Group>()

        var i = 0
        val data = mutableListOf<String>()
        while (true) {
            if (input[i].isEmpty()) {
                groups.add(Group(data))
                data.clear()
            } else {
                data.add(input[i])
            }
            i++
            // One more group in the queue that doesn't end with a blank line.
            if (i > input.lastIndex) {
                groups.add(Group(data))
                break
            }
        }

        return if (part.isOne()) {
            groups.sumOf { it.getAnyYesCounts() }
        } else {
            groups.sumOf { it.getAllYesCounts() }
        }
    }
}

class Group(data: List<String>) {
    val size = data.size
    val map = mutableMapOf<Char, Int>()

    init {
        for (line in data) {
            for (value in line.toCharArray()) {
                map.putIfAbsent(value, 0)
                map[value] = map[value]!! + 1
            }
        }
    }

    /**
     * Returns the number of questions that anyone answered yes to.
     */
    fun getAnyYesCounts(): Int {
        return map.size
    }

    /**
     * Returns the number of questions that everyone answered yes to.
     */
    fun getAllYesCounts(): Int {
        return map.count { it.value == size }
    }
}