package buri.aoc.y18.d12

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(325, 1)
        assertRun(1787, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1100000000475L, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var plants = mutableSetOf<Int>()
        for ((index, value) in input[0].split("state: ")[1].withIndex()) {
            if (value == '#') {
                plants.add(index)
            }
        }
        val rules = mutableMapOf<String, Boolean>()
        for (line in input.subList(2, input.size)) {
            val tokens = line.split(" => ")
            rules[tokens[0]] = (tokens[1] == "#")
        }
        val times = if (part == ONE) 20L else 94L
        for (time in 0 until times) {
            val minPlant = plants.min() - 2
            val maxPlant = plants.max() + 2
            val newPlants = mutableSetOf<Int>()
            for (plant in minPlant..maxPlant) {
                val isPlant = rules[buildPattern(plants, plant)] ?: false
                if (isPlant) {
                    newPlants.add(plant)
                }
            }
            plants = newPlants
        }
        return if (part == ONE) {
            plants.sum()
        } else {
            // After 93 iterations, the growth is linear, up by 22 plants each generation.
            plants.sum() + 22 * (50_000_000_000L - times)
        }
    }

    /**
     * Converts integer plants into a string pattern.
     */
    private fun buildPattern(plants: Set<Int>, start: Int): String {
        val pattern = StringBuilder()
        for (i in start - 2..start + 2) {
            pattern.append(if (i in plants) '#' else '.')
        }
        return pattern.toString()
    }
}