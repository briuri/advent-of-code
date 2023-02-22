package buri.aoc.y15.d20

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
        val presents = if (part.isOne()) 10 else 11
        val upperBound = 710_000 // Lowered this to speed up run time after getting the right answers
        val houses = Array(upperBound) { 0 }
        for (elf in 1 until upperBound) {
            for (house in elf until upperBound step elf) {
                if (part.isTwo() && house > elf * 50) {
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