package buri.aoc.y15.d16

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
        assertRun(213, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(323, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val expected = mutableMapOf<String, Int>()
        expected["children"] = 3
        expected["cats"] = 7
        expected["samoyeds"] = 2
        expected["pomeranians"] = 3
        expected["akitas"] = 0
        expected["vizslas"] = 0
        expected["goldfish"] = 5
        expected["trees"] = 3
        expected["cars"] = 2
        expected["perfumes"] = 1

        for (line in input) {
            val index = line.split(": ")[0].split(" ")[1].toInt()
            val map = mutableMapOf<String, Int>()
            for (token in line.substring(line.indexOf(":") + 2).split(", ")) {
                val tokens = token.split(": ")
                map[tokens[0]] = tokens[1].toInt()
            }
            var isMatch = true
            for (pair in expected) {
                if (!map.containsKey(pair.key)) {
                    continue
                }
                val condition = if (part == Part.TWO && (pair.key == "cats" || pair.key == "trees")) {
                    map[pair.key]!! > pair.value
                } else if (part == Part.TWO && (pair.key == "pomeranians" || pair.key == "goldfish")) {
                    map[pair.key]!! < pair.value
                } else {
                    map[pair.key]!! == pair.value
                }
                isMatch = isMatch && condition
            }
            if (isMatch) {
                return index
            }
        }
        return -1
    }
}