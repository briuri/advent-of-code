package buri.aoc.y22.d11;

import buri.aoc.Part;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model for a worrying monkey.
 *
 * @author Brian Uri!
 */
public class Monkey {
	private final int _id;
	private final List<Long> _items = new ArrayList<>();
	private final String _operation;
	private final long _testDivisor;
	private final int _targetTrue;
	private final int _targetFalse;
	private long _inspectCount = 0L;

	/**
	 * Constructor
	 *
	 * Monkey 0:
	 * Starting items: 79, 98
	 * Operation: new = old * 19
	 * Test: divisible by 23
	 * If true: throw to monkey 2
	 * If false: throw to monkey 3
	 */
	public Monkey(List<String> input) {
		_id = Integer.parseInt(input.get(0).split(" ")[1].split(":")[0]);
		String[] items = input.get(1).split(": ")[1].split(", ");
		for (String item : items) {
			getItems().add(Long.parseLong(item));
		}
		_operation = input.get(2).split("= ")[1];
		_testDivisor = Long.parseLong(input.get(3).split("by ")[1]);
		_targetTrue = Character.getNumericValue(input.get(4).charAt(input.get(4).length() - 1));
		_targetFalse = Character.getNumericValue(input.get(5).charAt(input.get(5).length() - 1));
	}

	/**
	 * Inspects an item and returns its new worry level. Modding by GCD ensures that
	 * all "divisible by" tests still work in Part 2.
	 */
	public long inspect(Part part, long worryLevel, long gcd) {
		incrementInspectCount();

		String[] tokens = getOperation().split(" ");
		long increment = (tokens[2].equals("old") ? worryLevel : Long.parseLong(tokens[2]));
		long decrease = (part == Part.ONE ? 3L : 1L);
		if (tokens[1].equals("+")) {
			worryLevel += increment;
		}
		else {
			worryLevel *= increment;
		}
		return (worryLevel / decrease) % gcd;
	}

	/**
	 * Returns the monkey who receives an item with the given worry level.
	 */
	public int getTarget(long worryLevel) {
		return (worryLevel % getTestDivisor() == 0 ? getTargetTrue() : getTargetFalse());
	}

	@Override
	public String toString() {
		return "Monkey " + getId() + ": " + getItems();
	}

	/**
	 * Accessor for the monkey's ID
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Accessor for the monkey's items.
	 */
	public List<Long> getItems() {
		return _items;
	}

	/**
	 * Accessor for the worry level operation.
	 */
	private String getOperation() {
		return _operation;
	}

	/**
	 * Accessor for the divisible by value.
	 */
	public long getTestDivisor() {
		return _testDivisor;
	}

	/**
	 * Accessor for the monkey who gets true-test items.
	 */
	private int getTargetTrue() {
		return _targetTrue;
	}

	/**
	 * Accessor for the monkey who gets false-test items.
	 */
	private int getTargetFalse() {
		return _targetFalse;
	}

	/**
	 * Accessor for the inspect count.
	 */
	public long getInspectCount() {
		return _inspectCount;
	}

	/**
	 * Add one to the inspect count for this monkey.
	 */
	private void incrementInspectCount() {
		_inspectCount++;
	}
}