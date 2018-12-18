package buri.aoc.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data class to count the number of character occurrences in a string.
 * 
 * @author Brian Uri!
 */
public class CharFrequency {

	private Map<Character, Integer> _frequency;

	/**
	 * Constructor
	 */
	public CharFrequency() {
		_frequency = new HashMap<>();
	}

	/**
	 * Constructor based on a string
	 */
	public CharFrequency(String input) {
		this();
		for (char value : input.toCharArray()) {
			add(value);
		}
	}

	/**
	 * Adds 1 to the count for a character.
	 */
	public void add(char value) {
		if (getFrequency().get(value) == null) {
			getFrequency().put(value, 0);
		}
		getFrequency().put(value, getFrequency().get(value) + 1);
	}

	/**
	 * Returns the most used letters.
	 */
	public String getHighestFrequencyChars(int num) {
		List<Map.Entry<Character, Integer>> list = getDescendingFrequency();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < Math.min(num, list.size()); i++) {
			buffer.append(list.get(i).getKey());
		}
		return (buffer.toString());
	}

	/**
	 * Returns a map entry with the most used letter and how many times it is used.
	 */
	public Map.Entry<Character, Integer> getHighestFrequency() {
		List<Map.Entry<Character, Integer>> list = getDescendingFrequency();
		return (list.get(0));
	}

	/**
	 * Returns a map entry with the least used letter and how many times it is used.
	 */
	public Map.Entry<Character, Integer> getLowestFrequency() {
		List<Map.Entry<Character, Integer>> list = getDescendingFrequency();
		return (list.get(list.size() - 1));
	}

	/**
	 * Sorts the map in descending order.
	 */
	private List<Map.Entry<Character, Integer>> getDescendingFrequency() {
		List<Map.Entry<Character, Integer>> list = new ArrayList<>(getFrequency().entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
			@Override
			public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
				int compare = o2.getValue() - o1.getValue();
				if (compare == 0) {
					compare = o1.getKey().compareTo(o2.getKey());
				}
				return (compare);
			}
		});
		return (list);
	}

	/**
	 * Accessor for the frequency
	 */
	private Map<Character, Integer> getFrequency() {
		return _frequency;
	}
}
