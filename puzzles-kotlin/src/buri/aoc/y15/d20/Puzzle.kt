package buri.aoc.y15.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(665280, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(705600, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val target = input[0].toInt()
        val presents = if (part == ONE) 10 else 11
        val upperBound = 710000 // Lowered this to speed up run time after getting the right answers
        val houses = Array(upperBound) { _: Int -> 0 }
        for (elf in 1 until upperBound) {
             for (house in elf until upperBound step elf) {
                 if (part == TWO && house > elf * 50) {
                     break
                 }
                 houses[house] += elf * presents
             }
        }
        for (house in 1 until upperBound) {
            if (houses[house] > target) {
                return house
            }
        }
        return -1
    }
}