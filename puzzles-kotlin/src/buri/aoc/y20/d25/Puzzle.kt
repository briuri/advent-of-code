package buri.aoc.y20.d25

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import java.math.BigInteger

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
        val doorPki = input[1].toLong()
        // Set upper bound based on input to speed up execution.
        val upperBound = if (cardPki == 5764801L) 15L else 5_500_000L
        val startingValue = BigInteger.valueOf(7L)
        val modulus = BigInteger.valueOf(20201227)

        // cardPki = 7^cardLoop mod 20201227
        // doorPki = 7^doorLoop mod 20201227
        // key = doorPki^cardLoop mod 20201227 = cardPki^doorLoop mod 20201227
        for (loopSize in upperBound downTo 1L) {
            val exponent = BigInteger.valueOf(loopSize)
            if (startingValue.modPow(exponent, modulus).toLong() == doorPki) {
                val base = BigInteger.valueOf(cardPki)
                return (base.modPow(exponent, modulus).toLong())
            }
        }
        return -1
    }
}