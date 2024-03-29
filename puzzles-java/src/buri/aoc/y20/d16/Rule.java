package buri.aoc.y20.d16;

import buri.aoc.common.data.tuple.Quad;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Single rule for a train ticket field.
 *
 * @author Brian Uri!
 */
public class Rule {
	private final String _name;
	private final Quad<Integer> _range;

	/**
	 * Constructor
	 */
	public Rule(String line) {
		_name = line.split(": ")[0];
		String[] range = line.split(": ")[1].split(" or ");
		String[] left = range[0].split("-");
		String[] right = range[1].split("-");
		int x = Integer.parseInt(left[0]);
		int y = Integer.parseInt(left[1]);
		int z = Integer.parseInt(right[0]);
		int t = Integer.parseInt(right[1]);
		_range = new Quad<>(x, y, z, t);
	}

	/**
	 * Returns true if some value is in range for this rule.
	 */
	public boolean inRange(int value) {
		return (getRange().getX() <= value && getRange().getY() >= value) || (getRange().getZ() <= value
			&& getRange().getT() >= value);
	}

	/**
	 * Returns indexes of all fields that are valid for this rule, ignoring any fields that have already been labeled.
	 */
	public List<Integer> getValidFields(List<Ticket> tickets, Set<Integer> labeledIndexes) {
		List<Integer> valid = new ArrayList<>();
		for (int i = 0; i < tickets.get(0).getSize(); i++) {
			if (!labeledIndexes.contains(i)) {
				int validCount = 0;
				for (Ticket ticket : tickets) {
					if (inRange(ticket.getField(i))) {
						validCount++;
					}
				}
				if (validCount == tickets.size()) {
					valid.add(i);
				}
			}
		}
		return (valid);
	}

	/**
	 * Accessor for the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the range
	 */
	private Quad<Integer> getRange() {
		return _range;
	}
}