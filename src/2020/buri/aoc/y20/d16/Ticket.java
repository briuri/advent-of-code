package buri.aoc.y20.d16;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data model for a train ticket.
 *
 * @author Brian Uri!
 */
public class Ticket {
	List<Integer> _fields;

	/**
	 * Constructor
	 */
	public Ticket(String line) {
		_fields = new ArrayList<>();
		for (String field : line.split(",")) {
			_fields.add(Integer.valueOf(field));
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<Integer> iter = getFields().iterator(); iter.hasNext();) {
			buffer.append(iter.next());
			if (iter.hasNext()) {
				buffer.append(",");
			}
		}
		return (buffer.toString());
	}

	/**
	 * Returns the sum of all fields that do not fit ANY rules.
	 */
	public int getInvalidSum(List<Rule> rules) {
		int sum = 0;
		for (Integer value : getFields()) {
			boolean atLeastOne = false;
			for (Rule range : rules) {
				atLeastOne = atLeastOne || range.inRange(value);
			}
			if (!atLeastOne) {
				sum += value;
			}
		}
		return (sum);
	}

	/**
	 * Accessor for a field value
	 */
	public int getField(int index) {
		return (getFields().get(index));
	}

	/**
	 * Accessor for the number of fields
	 */
	public int getSize() {
		return (_fields.size());
	}

	/**
	 * Accessor for the fields
	 */
	private List<Integer> getFields() {
		return (_fields);
	}
}
