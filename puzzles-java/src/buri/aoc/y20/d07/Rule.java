package buri.aoc.y20.d07;

import java.util.HashMap;
import java.util.Map;

/**
 * Data model for a baggage rule.
 *
 * @author Brian Uri!
 */
public class Rule {
	private final String _bagType;
	private final Map<String, Integer> _bagsInside;

	/**
	 * Constructor
	 *
	 * vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
	 * faded blue bags contain no other bags.
	 */
	public Rule(String line) {
		String[] tokens = line.split(" bags contain ");
		_bagType = tokens[0];
		_bagsInside = new HashMap<>();
		if (!tokens[1].startsWith("no")) {
			for (String contents : tokens[1].split(", ")) {
				int amount = Integer.parseInt(contents.split(" ")[0]);
				String name = contents.split(" ")[1] + " " + contents.split(" ")[2];
				getBagsInside().put(name, amount);
			}
		}
	}

	/**
	 * Returns true if this rule can contain a bag of the specific type.
	 */
	public boolean canContain(String bagType) {
		return (getBagsInside().containsKey(bagType));
	}

	/**
	 * Accessor for the bag type
	 */
	public String getBagType() {
		return _bagType;
	}

	/**
	 * Accessor for the map of what this bag contains
	 */
	public Map<String, Integer> getBagsInside() {
		return _bagsInside;
	}
}