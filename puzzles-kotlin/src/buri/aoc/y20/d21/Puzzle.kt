package buri.aoc.y20.d21

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
        assertRun("5", 1)
        assertRun("2734", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("mxmxvkd,sqjhc,fvjkl", 1)
        assertRun("kbmlt,mrccxm,lpzgzmk,ppj,stj,jvgnc,gxnr,plrlg", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val allergens = mutableMapOf<String, MutableSet<String>>()
        val ingredients = mutableMapOf<String, Int>()
        for (line in input) {
            val rawAllergens = line.split("contains ")[1].dropLast(1).split(", ")
            val rawIngredients = line.split(" (")[0].split(" ").toSet()
            for (allergen in rawAllergens) {
                allergens[allergen] = if (allergens[allergen] == null) {
                    rawIngredients.toMutableSet()
                } else {
                    allergens[allergen]!!.intersect(rawIngredients).toMutableSet()
                }
            }
            for (ingredient in rawIngredients) {
                ingredients.putIfAbsent(ingredient, 0)
                ingredients[ingredient] = ingredients[ingredient]!! + 1
            }
        }

        if (part.isOne()) {
            val inert = ingredients.keys.filter { it !in allergens.values.flatten() }
            return ingredients.filter { it.key in inert }.values.sum().toString()
        }

        val solvedAllergens = mutableMapOf<String, String>()
        while (allergens.values.any { it.size > 0 }) {
            for ((key, value) in allergens) {
                // Get the first allergen-ingredient pairing that is confirmed.
                if (value.size == 1) {
                    val found = value.first()
                    solvedAllergens[key] = found
                    allergens.forEach { it.value.remove(found) }
                }
            }
        }
        return solvedAllergens.toSortedMap().values.joinToString(",")
    }
}