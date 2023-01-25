package buri.aoc.y21.d10;

import java.util.HashSet;
import java.util.Set;

/**
 * Enumeration for a bracket grouping, (), [], {}, <>.
 *
 * @author Brian Uri!
 */
public enum Group {
	PAREN('(', ')', 3, 1),
	SQUARE('[', ']', 57, 2),
	BRACE('{', '}', 1197, 3),
	GREAT('<', '>', 25137, 4);

	private char _open;
	private char _close;
	private int _corruptScore;
	private int _incompleteScore;

	public static final Set<Character> ALL_OPEN = new HashSet<>();
	static {
		for (Group group : Group.values()) {
			ALL_OPEN.add(group.getOpen());
		}
	}

	/**
	 * Constructor
	 */
	private Group(char open, char close, int corruptScore, int incompleteScore) {
		_open = open;
		_close = close;
		_corruptScore = corruptScore;
		_incompleteScore = incompleteScore;
	}

	/**
	 * Lookup method for groups.
	 */
	public static Group getGroupForOpen(Character open) {
		for (Group group : Group.values()) {
			if (group.getOpen() == open) {
				return (group);
			}
		}
		return (null);
	}

	/**
	 * Lookup method for groups.
	 */
	public static Group getGroupForClose(Character close) {
		for (Group group : Group.values()) {
			if (group.getClose() == close) {
				return (group);
			}
		}
		return (null);
	}

	/**
	 * Accessor for the open
	 */
	public char getOpen() {
		return _open;
	}

	/**
	 * Accessor for the close
	 */
	public char getClose() {
		return _close;
	}

	/**
	 * Accessor for the corruptScore
	 */
	public int getCorruptScore() {
		return _corruptScore;
	}

	/**
	 * Accessor for the incompleteScore
	 */
	public int getIncompleteScore() {
		return _incompleteScore;
	}
}