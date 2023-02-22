package buri.aoc.y15.d24

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
        assertRun(99, 1)
        assertRun(11266889531, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(44, 1)
        assertRun(77387711, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val packages = mutableListOf<Long>()
        packages.addAll(input.map { it.toLong() })
        packages.reverse()
        val compartments = if (part.isOne()) 3 else 4
        val targetWeight = packages.sum() / compartments

        val options = getPermutations(targetWeight, packages).sortedWith(compareBy { it.size })
        val minSize = options[0].size
        var minQE = Long.MAX_VALUE
        for (option in options) {
            if (option.size == minSize) {
                minQE = minQE.coerceAtMost(option.reduce(Long::times))
            }
        }
        return minQE
    }

    /**
     * Finds all the different ways to combine packages to reach a target weight.
     */
    private fun getPermutations(targetWeight: Long, weights: List<Long>): MutableList<MutableList<Long>> {
        val permutations = mutableListOf<MutableList<Long>>()
        for (i in weights.indices) {
            if (weights[i] == targetWeight) {
                val option = mutableListOf<Long>()
                option.add(weights[i])
                permutations.add(option)
            } else if (weights[i] < targetWeight) {
                for (option in getPermutations(targetWeight - weights[i], weights.subList(i + 1, weights.size))) {
                    option.add(0, weights[i])
                    permutations.add(option)
                }
            }
        }
        return permutations
    }
}