package buri.aoc.y21.d18;

import buri.aoc.common.data.CharFrequency;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Data model for a snailfish number (a pair of nested numbers).
 *
 * Each member of the pair is either another pair or a regular number.
 *
 * @author Brian Uri!
 */
public class Number {
	private Number _left = null;
	private Number _right = null;
	private Number _parent = null;
	private Integer _value = null;

	/**
	 * Constructor
	 */
	public Number(String line, Number parent) {
		// Regular number
		if (!line.contains(",")) {
			setValue(Integer.valueOf(line));
			// The parent only applies to pairs. The parent pair of this regular number will have the true parent.
			setParent(null);
		}
		// Pair of somethings
		else {
			int middle = getMiddleComma(line);
			setLeft(new Number(line.substring(1, middle), this));
			setRight(new Number(line.substring(middle + 1, line.length() - 1), this));
			setParent(parent);
		}
	}

	/**
	 * Constructor for a number created by adding two numbers.
	 */
	public Number(Number x, Number y) {
		setLeft(x);
		setRight(y);
		reduce();
	}

	/**
	 * The magnitude of a pair is 3 times the magnitude of its left element plus 2 times the magnitude of its right
	 * element. The magnitude of a regular number is just that number.
	 */
	public int getMagnitude() {
		if (isRegularNumber()) {
			return (getValue());
		}
		return (3 * getLeft().getMagnitude() + 2 * getRight().getMagnitude());
	}

	/**
	 * Returns true if this number is just a number and not a pair of numbers.
	 */
	public boolean isRegularNumber() {
		return (_value != null);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isRegularNumber()) {
			return (String.valueOf(getValue()));
		}
		return ("[" + getLeft().toString() + "," + getRight().toString() + "]");
	}

	/**
	 * To reduce a snailfish number, you must repeatedly do the first action in this list that applies to the snailfish
	 * number:
	 * - If any pair is nested inside four pairs, the leftmost such pair explodes.
	 * - If any regular number is 10 or greater, the leftmost such regular number splits.
	 * Once no action in the above list applies, the snailfish number is reduced.
	 */
	private void reduce() {
		boolean inReduce = true;
		while (inReduce) {
			while (explode()) {}
			// As soon as a split happens, jump back to the beginning and look for explodes again.
			inReduce = split();
		}
	}

	/**
	 * To explode a pair, the pair's left value is added to the first regular number to the left of the exploding pair
	 * (if any), and the pair's right value is added to the first regular number to the right of the exploding pair (if
	 * any). Exploding pairs will always consist of two regular numbers. Then, the entire exploding pair is replaced
	 * with the regular number 0.
	 */
	private boolean explode() {
		if (isRegularNumber()) {
			return (false);
		}
		if (getDepth() == 4) {
			Number leftNeighbor = getNeighbor(this, true);
			Number rightNeighbor = getNeighbor(this, false);
			if (leftNeighbor != null) {
				leftNeighbor.setValue(leftNeighbor.getValue() + getLeft().getValue());
			}
			if (rightNeighbor != null) {
				rightNeighbor.setValue(rightNeighbor.getValue() + getRight().getValue());
			}
			setLeft(null);
			setRight(null);
			setValue(0);
			return true;
		}
		if (getLeft().explode()) {
			return true;
		}
		return getRight().explode();
	}

	/**
	 * To split a regular number, replace it with a pair; the left element of the pair should be the regular number
	 * divided by two and rounded down, while the right element of the pair should be the regular number divided by two
	 * and rounded up.
	 */
	private boolean split() {
		if (isRegularNumber()) {
			if (getValue() >= 10) {
				int newLeft = getValue() / 2;
				int newRight = (int) Math.ceil((double) getValue() / 2);
				setLeft(new Number(String.valueOf(newLeft), this));
				setRight(new Number(String.valueOf(newRight), this));
				setValue(null);
				return true;
			}
		}
		else {
			if (getLeft().split()) {
				return true;
			}
			return getRight().split();
		}
		return false;
	}

	/**
	 * Returns the regular number to the left or right of this number in the string.
	 */
	private static Number getNeighbor(Number number, boolean isLeft) {
		// Walk back to the top root.
		Number root = number;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		// Create a flat ordering of all the numbers.
		List<Number> allNumbers = root.getAllNumbers();
		// Set the desired index for left/right neighbor.
		int shift = (isLeft ? -1 : 2);
		int index = allNumbers.indexOf(number.getLeft()) + shift;
		// No neighbor in that direction
		if (index < 0 || index >= allNumbers.size()) {
			return (null);
		}
		return (allNumbers.get(index));
	}

	/**
	 * Returns a list of all numbers under this one for traversal.
	 */
	private List<Number> getAllNumbers() {
		List<Number> all = new ArrayList<>();
		if (isRegularNumber()) {
			all.add(this);
		}
		else {
			all.addAll(getLeft().getAllNumbers());
			all.addAll(getRight().getAllNumbers());
		}
		return (all);
	}

	/**
	 * Returns the index of the middle comma in a pair
	 */
	private static int getMiddleComma(String line) {
		StringBuilder builder = new StringBuilder(line);
		CharFrequency freq = new CharFrequency(builder.toString());
		// Reduce the line down to the topmost pair.
		while (freq.getFrequencyFor('[') > 1) {
			for (int i = 1; i < builder.length(); i++) {
				char value = builder.charAt(i);
				if (value == '[') {
					int closingIndex = getMatchingClosingIndex(line, i);
					for (int j = i; j < closingIndex + 1; j++) {
						builder.replace(j, j + 1, " ");
					}
				}
			}
			freq = new CharFrequency(builder.toString());
		}
		return (builder.indexOf(","));
	}

	/**
	 * Returns the index of a closing bracket that matches the opening one at the index
	 */
	private static int getMatchingClosingIndex(String line, int openIndex) {
		Stack<Character> stack = new Stack<>();
		for (int i = openIndex + 1; i < line.length(); i++) {
			char value = line.charAt(i);
			if (value == '[') {
				stack.push(value);
			}
			else if (value == ']') {
				if (stack.size() > 0) {
					stack.pop();
				}
				else {
					return (i);
				}
			}
		}
		throw new RuntimeException("Couldn't find matching bracket");
	}

	/**
	 * Accessor for the depth of this number
	 */
	private int getDepth() {
		if (getParent() == null) {
			return (0);
		}
		return (getParent().getDepth() + 1);
	}

	/**
	 * Accessor for the left member of the pair (if this is a pair).
	 */
	private Number getLeft() {
		if (isRegularNumber()) {
			throw new RuntimeException("This is not a pair.");
		}
		return _left;
	}

	/**
	 * Accessor for the left member of the pair (if this is a pair).
	 */
	private void setLeft(Number left) {
		if (left != null) {
			left.setParent(this);
		}
		_left = left;
	}

	/**
	 * Accessor for the right member of the pair (if this is a pair).
	 */
	private Number getRight() {
		if (isRegularNumber()) {
			throw new RuntimeException("This is not a pair.");
		}
		return _right;
	}

	/**
	 * Accessor for the right member of the pair (if this is a pair).
	 */
	private void setRight(Number right) {
		if (right != null) {
			right.setParent(this);
		}
		_right = right;
	}

	/**
	 * Accessor for the regular number value (if this is not a pair).
	 */
	private Integer getValue() {
		if (!isRegularNumber()) {
			throw new RuntimeException("This is a pair.");
		}
		return _value;
	}

	/**
	 * Accessor for the regular number value (if this is not a pair).
	 */
	private void setValue(Integer value) {
		_value = value;
	}

	/**
	 * Accessor for the parent
	 */
	public Number getParent() {
		return (_parent);
	}

	/**
	 * Accessor for the parent
	 */
	private void setParent(Number parent) {
		_parent = parent;
	}
}