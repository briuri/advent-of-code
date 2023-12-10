package buri.aoc.y23.d07

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
        assertRun(6440, 1)
        assertRun(250453939, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(5905, 1)
        assertRun(248652697, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val hands = input.map { Hand(part, it) }.toMutableList().sorted()
        var total = 0
        for ((index, hand) in hands.withIndex()) {
            total += (index + 1) * hand.bid
        }
        return total
    }
}

/**
 * Data class for a camel card hand.
 */
class Hand(val part: Part, input: String) : Comparable<Hand> {
    private val rawHand = input.split(" ")[0]
    val bid = input.split(" ")[1].toInt()

    private val cardOrder = if (part.isOne()) "23456789TJQKA" else "J23456789TQKA"

    // Card rankings from lowest to highest
    enum class Type {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_KIND, FULL_HOUSE, FOUR_KIND, FIVE_KIND
    }

    /**
     * Calculates the hand type based on the cards in the hand.
     */
    private fun getType(): Type {
        val cards = if (part.isOne()) rawHand else convertJokers()
        with(cards.getCharFrequency().values) {
            return if (contains(5)) {
                Type.FIVE_KIND
            } else if (contains(4)) {
                Type.FOUR_KIND
            } else if (contains(3)) {
                if (contains(2)) Type.FULL_HOUSE else Type.THREE_KIND
            } else if (contains(2)) {
                if (count { it == 2 } == 2) Type.TWO_PAIR else Type.ONE_PAIR
            } else Type.HIGH_CARD
        }
    }

    /**
     * Converts wildcards into the highest performing cards.
     */
    private fun convertJokers(): String {
        if (rawHand.indexOf('J') == -1) {
            return rawHand
        }
        val counts = rawHand.getCharFrequency()
        // Default to Ace if the whole hand is Jokers
        var highCard = cardOrder.last()
        if (counts.size > 1) {
            // Find the most frequent non-Joker card(s) and get the highest ranked one.
            val mostOther = counts.filter { it.key != 'J' }.values.max()
            val possibleCards = counts.filter { it.value == mostOther }.keys.sortedBy { cardOrder.indexOf(it) }
            highCard = possibleCards.last()
        }
        return rawHand.replace('J', highCard)
    }

    /**
     * Extension function to count the occurrences of each character.
     */
    private fun String.getCharFrequency(): Map<Char, Int> = this.map { it }.groupingBy { it }.eachCount()

    override fun compareTo(other: Hand): Int {
        var compare = getType().compareTo(other.getType())
        if (compare == 0) {
            for (i in rawHand.indices) {
                compare = cardOrder.indexOf(rawHand[i]).compareTo(cardOrder.indexOf(other.rawHand[i]))
                if (compare != 0) {
                    return compare
                }
            }
        }
        return compare
    }
}