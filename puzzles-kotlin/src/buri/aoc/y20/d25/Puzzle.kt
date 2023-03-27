package buri.aoc.y20.d25

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.toBigInt
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(14897079, 1)
        assertRun(181800, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val cardPki = input[0].toLong()
        val doorPki = input[1].toLong().toBigInt()
        // Set upper bound based on input to speed up execution.
        val upperBound = if (cardPki == 5764801L) 15L else 5_500_000L
        val startingValue = (7L).toBigInt()
        val modulus = (20201227L).toBigInt()

        // cardPki = 7^cardLoop mod 20201227
        // doorPki = 7^doorLoop mod 20201227
        // key = doorPki^cardLoop mod 20201227 = cardPki^doorLoop mod 20201227
        for (loopSize in upperBound downTo 1L) {
            val exponent = loopSize.toBigInt()
            if (startingValue.modPow(exponent, modulus) == doorPki) {
                return (cardPki.toBigInt().modPow(exponent, modulus).toLong())
            }
        }
        return -1
    }
}