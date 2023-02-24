package buri.aoc.y18.d14

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
        assertRun("5158916779", 1)
        assertRun("7162937112", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("18", 2)
        assertRun("20195890", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val recipes = mutableListOf(3, 7)
        val recipesNeeded = input[0].extractInts()[0]
        val scoresNeeded = input[0].map { it.digitToInt() }.toList()
        var elf1 = 0
        var elf2 = 1
        while (true) {
            val scores = (recipes[elf1] + recipes[elf2]).toString().map { it.digitToInt() }
            for (score in scores) {
                recipes.add(score)
                if (part.isOne() && recipes.size == (recipesNeeded + 10)) {
                    return recipes.subList(recipesNeeded, recipesNeeded + 10).joinToString("")
                }
                if (part.isTwo() && recipes.size >= scoresNeeded.size) {
                    val leftIndex = recipes.size - scoresNeeded.size
                    if (recipes.subList(leftIndex, recipes.size) == scoresNeeded) {
                        return leftIndex.toString()
                    }
                }
            }
            elf1 = (elf1 + recipes[elf1] + 1) % recipes.size
            elf2 = (elf2 + recipes[elf2] + 1) % recipes.size
        }
    }
}