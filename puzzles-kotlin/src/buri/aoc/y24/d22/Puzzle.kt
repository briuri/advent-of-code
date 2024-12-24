package buri.aoc.y24.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun(37327623, 1)
        assertRun(19822877190, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(23, 2)
        assertRun(2277, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val secretNumbers = mutableMapOf<Long, List<Long>>()
        val sequenceToPrice = mutableMapOf<Long, MutableMap<List<Int>, Int>>()

        for (buyer in input.joinToString(",").extractLongs()) {
            val buyersNumbers = mutableListOf<Long>()
            buyersNumbers.add(buyer)
            repeat(2000) {
                buyersNumbers.add(buyersNumbers.last().evolve())
            }
            val prices = buyersNumbers.map { it.toString().last().digitToInt() }
            val differences = mutableListOf<Int>()
            for (i in 1..prices.lastIndex) {
                differences.add(prices[i] - prices[i - 1])
            }
            val sequencedPrices = mutableMapOf<List<Int>, Int>()
            for (i in 0..differences.lastIndex - 3) {
                val sequence = differences.subList(i, i + 4)

                // Only record the first occurrence.
                if (sequence !in sequencedPrices.keys) {
                    sequencedPrices[sequence] = prices[i + 4]
                }
            }
            secretNumbers[buyer] = buyersNumbers
            sequenceToPrice[buyer] = sequencedPrices
        }
        if (part.isOne()) {
            return secretNumbers.values.sumOf { it.last() }
        }

        // Part TWO
        var maxSum = Long.MIN_VALUE
        val uniqueSequences = sequenceToPrice.values.map { it.keys }.flatten().toSet()
        for (sequence in uniqueSequences) {
            var priceSum = 0L
            for (sequencedPrices in sequenceToPrice.values) {
                if (sequence in sequencedPrices.keys) {
                    priceSum += sequencedPrices[sequence]!!
                }
            }
            maxSum = maxSum.coerceAtLeast(priceSum)
        }
        return maxSum
    }

    /**
     * Evolves a secret number.
     */
    private fun Long.evolve(): Long {
        var end = this
        end = ((end * 64L) xor end) % 16777216L
        end = ((end / 32L) xor end) % 16777216L
        end = ((end * 2048L) xor end) % 16777216L
        return end
    }
}