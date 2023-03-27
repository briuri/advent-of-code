package buri.aoc.y19.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.toBigInt
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
        assertRun(7665, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(41653717360577, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val deck = Deck(if (part.isOne()) 10_007L else 119_315_717_514_047L)
        for (line in input) {
            val tokens = line.split(" ")
            if (tokens[0] == "cut") {
                deck.cut(tokens[1].toLong())
            } else if (tokens[0] == "deal" && tokens[1] == "into") {
                deck.dealIntoNewStack()
            } else {
                deck.dealWithIncrement(tokens[3].toLong())
            }
        }
        return if (part.isOne()) {
            deck.getPositionOfCard(2019)
        } else {
            deck.getCardAtPosition(2020, 101_741_582_076_661L)
        }
    }
}

/**
 * Card deck based on mod math, (aq + b) % size
 */
class Deck(rawSize: Long) {
    private var a: BigInteger = BigInteger.ONE
    private var b: BigInteger = BigInteger.ZERO
    private val size = rawSize.toBigInt()

    /**
     * Cuts the deck
     *
     * b = cardAtIncrement
     */
    fun cut(increment: Long) {
        b = getCardAtPosition(increment)
    }

    /**
     * Reverses the deck.
     *
     * b = lastCard
     * a *= -1
     */
    fun dealIntoNewStack() {
        b = getCardAtPosition(-1L)
        a *= (-1L).toBigInt()
    }

    /**
     * Deals by increment (increment * position)
     *
     * a /= increment
     */
    fun dealWithIncrement(increment: Long) {
        a *= increment.toBigInt().modInverse(size)
    }

    /**
     * Returns the position of a card, or -1 if not found.
     */
    fun getPositionOfCard(card: Long): Long {
        var position = 0L
        while (position < size.toLong()) {
            if (getCardAtPosition(position).toLong() == card) {
                return position
            }
            position++
        }
        return -1
    }

    /**
     * Returns the card at some position after the deck has been shuffled some number of times.
     */
    fun getCardAtPosition(position: Long, shuffles: Long): Long {
        val q = position.toBigInt()
        val t = shuffles.toBigInt()

        // Powers:
        // 1: a * q + b
        // 2: a * (a * q + b) + b= a^2 * q + a * b + b = a^2 * q + (a + 1) * b
        // 3: a * (a^2 * q + a * b + b) + b = a^3 * q + a^2 * b + a * b + b = a^3 * q + (a^2 + a + 1) * b
        // t: a^t * q + ((a^t - 1) / (a - 1)) * b
        val x1 = a.modPow(t, size) * q // a^t * q
        val x2 = a.modPow(t, size) - BigInteger.ONE // (a^t - 1)
        val x3 = (a - BigInteger.ONE).modInverse(size) // 1/(a - 1)
        val x4 = x2 * x3 * b // ((a^t - 1) / (a - 1)) * b
        val result = (x1 + x4) % size // a^t * q + ((a^t - 1) / (a - 1)) * b
        return result.toLong()
    }

    /**
     * Returns the card at this position.
     *
     * card = (a * position + b) % size
     */
    private fun getCardAtPosition(position: Long): BigInteger {
        return (a * position.toBigInt() + b).mod(size)
    }
}