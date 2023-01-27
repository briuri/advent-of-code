package buri.aoc.y20.d21;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Day 21: Allergen Assessment
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("5", 1, false);
		assertRun("2493", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("mxmxvkd,sqjhc,fvjkl", 1, false);
		assertRun("kqv,jxx,zzt,dklgl,pmvfzk,tsnkknk,qdlpbt,tlgrhdh", 0, true);
	}

	/**
	 * - Each allergen is found in exactly one ingredient.
	 * - Each ingredient contains zero or one allergen.
	 * - Allergens aren't always marked. If an allergen isn't listed, the ingredient that contains that allergen could
	 * still be present.
	 *
	 * Part 1:
	 * How many times do any of those ingredients appear?
	 *
	 * Part 2:
	 * What is your canonical dangerous ingredient list?
	 */
	protected String runString(Part part, List<String> input) {
		List<Food> foods = new ArrayList<>();
		for (String line : input) {
			foods.add(new Food(line));
		}

		// Map of ingredient name to number of times it appears
		Map<String, Integer> ingredients = new HashMap<>();

		// Map of allergen name to potential ingredients
		Map<String, Set<String>> allergens = new HashMap<>();

		for (Food food : foods) {
			for (String ingredient : food.getIngredients()) {
				if (!ingredients.containsKey(ingredient)) {
					ingredients.put(ingredient, 0);
				}
				ingredients.put(ingredient, ingredients.get(ingredient) + 1);
			}
			for (String allergen : food.getAllergens()) {
				if (!allergens.containsKey(allergen)) {
					allergens.put(allergen, new HashSet<>());
				}
			}
		}

		// Start by presuming that each allergen might be in any of the ingredients.
		for (String allergen : allergens.keySet()) {
			allergens.get(allergen).addAll(ingredients.keySet());
		}
		// Remove any that are impossible based on puzzle input.
		for (Food food : foods) {
			for (String allergen : food.getAllergens()) {
				for (String ingredient : ingredients.keySet()) {
					if (!food.getIngredients().contains(ingredient)) {
						allergens.get(allergen).remove(ingredient);
					}
				}
			}
		}

		if (part == Part.ONE) {
			// See which ingredients remain as potential candidates and count the others.
			Set<String> remainingChoices = new HashSet<>();
			for (Set<String> remaining : allergens.values()) {
				remainingChoices.addAll(remaining);
			}
			int count = 0;
			for (String ingredient : ingredients.keySet()) {
				if (!remainingChoices.contains(ingredient)) {
					count += ingredients.get(ingredient);
				}
			}
			return (String.valueOf(count));
		}

		// Map of allergen name to specific ingredient (alphabetized by allergen name)
		Map<String, String> canonicalList = new TreeMap<>();
		for (int i = 0; i < allergens.size(); i++) {
			// Find allegrens with only 1 possible choice for ingredient.
			for (String allergen : allergens.keySet()) {
				Set<String> choices = allergens.get(allergen);
				if (choices.size() == 1) {
					// Add to canonical list and remove that ingredient from other allergens.
					String ingredient = new ArrayList<>(choices).get(0);
					canonicalList.put(allergen, ingredient);
					for (String eliminated : allergens.keySet()) {
						if (!allergen.equals(eliminated)) {
							allergens.get(eliminated).remove(ingredient);
						}
					}
				}
			}
			// Eventually all allergens will be accounted for.
		}

		// Print alphabetically by allergen
		StringBuilder builder = new StringBuilder();
		for (String allergen : canonicalList.keySet()) {
			builder.append(canonicalList.get(allergen)).append(",");
		}
		// Remove last comma
		return (builder.substring(0, builder.length() - 1));
	}
}