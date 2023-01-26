package buri.aoc.y20.d06;

import buri.aoc.common.Part;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data model for a group of questionnaire responses.
 *
 * @author Brian Uri!
 */
public class AnswerGroup {
	private int _size;
	private Map<Character, Integer> _counts;

	/**
	 * Constructor
	 */
	public AnswerGroup(List<String> chunk) {
		_size = chunk.size();
		_counts = new HashMap<>();
		for (String line : chunk) {
			for (int i = 0; i < line.length(); i++) {
				char value = line.charAt(i);
				if (getCounts().get(value) == null) {
					getCounts().put(value, 0);
				}
				getCounts().put(value, getCounts().get(value) + 1);
			}
		}
	}

	/**
	 * Returns the number of people answering yes, based on part.
	 */
	public int getYesCount(Part part) {
		if (part == Part.ONE) {
			return (getCounts().keySet().size());
		}
		int count = 0;
		for (Character key : getCounts().keySet()) {
			if (getCounts().get(key) == getSize()) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Accessor for the size
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * Accessor for the counts
	 */
	private Map<Character, Integer> getCounts() {
		return _counts;
	}

}