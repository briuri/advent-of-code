package buri.aoc.y15.d13

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
        assertRun(330, 1)
        assertRun(733, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(725, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val happiness = mutableMapOf<String, Int>()
        val people = mutableSetOf<String>()
        for (line in input) {
            val tokens = line.split(" ")
            val name1 = tokens[0]
            val direction = if (tokens[2] == "gain") 1 else -1
            val amount = tokens[3].toInt()
            val name2 = tokens[tokens.lastIndex].dropLast(1)

            happiness["$name1 $name2"] = direction * amount
            people.add(name1)
        }
        if (part.isTwo()) {
            for (person in people) {
                happiness["$person Me"] = 0
                happiness["Me $person"] = 0
            }
            people.add("Me")
        }

        val seatingCharts = generatePermutations(people.toList(), 0)
        var maxHappiness = 0
        for (seats in seatingCharts) {
            var localHappiness = 0
            for (i in seats.indices) {
                val nextIndex = if (i == seats.lastIndex) 0 else (i + 1)
                localHappiness += happiness["${seats[i]} ${seats[nextIndex]}"]!!
                localHappiness += happiness["${seats[nextIndex]} ${seats[i]}"]!!
            }
            maxHappiness = maxHappiness.coerceAtLeast(localHappiness)
        }
        return maxHappiness
    }
}