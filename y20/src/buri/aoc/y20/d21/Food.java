package buri.aoc.y20.d21;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Data model for a list of ingredients and list of potential allergens.
 *
 * @author Brian Uri!
 */
public class Food {
	private Set<String> _ingredients;
	private Set<String> _allergens;

	/**
	 * Constructor
	 */
	public Food(String line) {
		_ingredients = new HashSet<>();
		_allergens = new HashSet<>();

		String[] tokens = line.split(" \\(contains ");
		getIngredients().addAll(Arrays.asList(tokens[0].split(" ")));
		tokens[1] = tokens[1].replaceAll("\\)", "");
		getAllergens().addAll(Arrays.asList(tokens[1].split(", ")));
	}

	/**
	 * Accessor for the ingredients
	 */
	public Set<String> getIngredients() {
		return _ingredients;
	}

	/**
	 * Accessor for the allergens
	 */
	public Set<String> getAllergens() {
		return _allergens;
	}
}
