package buri.aoc.y19.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import kotlin.math.ceil

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(1590844, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1184209, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val rules = mutableMapOf<String, Rule>()
        for (line in input) {
            val rawMakes = line.split(" => ")[1].split(" ")
            val makes = Chemical(rawMakes[1], rawMakes[0].toInt())
            val needs = mutableListOf<Chemical>()
            for (token in line.split(" => ")[0].split(", ")) {
                val rawNeeds = token.split(" ")
                needs.add(Chemical(rawNeeds[1], rawNeeds[0].toInt()))
            }
            rules[makes.name] = Rule(makes, needs)
        }

        val oreNeeded = reduceToOre(rules, mutableMapOf(), "FUEL", 1L)
        if (part.isOne()) {
            return oreNeeded
        }

        // Do a binary search to find the amount of fuel.
        val targetOre = 1_000_000_000_000
        var minFuel = 0L
        var maxFuel = 10_000_000L
        var midFuel = maxFuel / 2
        while (minFuel + 1 < maxFuel) {
            val ore = reduceToOre(rules, mutableMapOf(), "FUEL", midFuel)
            if (ore > targetOre) {
                maxFuel = midFuel - 1
            } else {
                minFuel = midFuel
            }
            midFuel = (minFuel + maxFuel) / 2L
        }
        return midFuel
    }

    /**
     * Recursively breaks down a rule to show how much ore is needed.
     */
    private fun reduceToOre(
        rules: Map<String, Rule>,
        surplus: MutableMap<String, Long>,
        makes: String, amount: Long
    ): Long {
        if (makes == "ORE") {
            return amount
        }

        val adjustedAmount = amount - surplus.getOrDefault(makes, 0L)
        val rule = rules[makes]!!
        val numReactions = ceil(adjustedAmount / rule.makes.amount.toDouble()).toLong()
        surplus[makes] = (numReactions * rule.makes.amount) - adjustedAmount

        var subAmounts = 0L
        for (chemical in rule.needs) {
            subAmounts += reduceToOre(rules, surplus, chemical.name, chemical.amount * numReactions)
        }
        return subAmounts
    }
}

class Rule(val makes: Chemical, val needs: List<Chemical>)

class Chemical(val name: String, val amount: Int)