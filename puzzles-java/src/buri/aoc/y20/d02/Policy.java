package buri.aoc.y20.d02;

import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;

/**
 * Data model for a password policy and test password.
 *
 * @author Brian Uri!
 */
public class Policy {
	private final int _low;
	private final int _high;
	private final char _requiredChar;
	private final String _password;

	/**
	 * Constructor
	 *
	 * 9-11 p: pppppppppxblp
	 */
	public Policy(String input) {
		String[] tokens = input.split(" ");
		_low = Integer.parseInt(tokens[0].split("-")[0]);
		_high = Integer.parseInt(tokens[0].split("-")[1]);
		_requiredChar = tokens[1].charAt(0);
		_password = tokens[2];
	}

	/**
	 * Returns true if the password is valid according to the policy.
	 */
	public boolean isValid(Part part) {
		if (part == Part.ONE) {
			CharFrequency freq = new CharFrequency(getPassword());
			int count = freq.getFrequencyFor(getRequiredChar());
			return (count >= getLow() && count <= getHigh());
		}
		boolean inLowPos = getPassword().charAt(getLow() - 1) == getRequiredChar();
		boolean inHighPos = getPassword().charAt(getHigh() - 1) == getRequiredChar();
		return ((inLowPos && !inHighPos) || (!inLowPos && inHighPos));
	}

	/**
	 * Accessor for the low
	 */
	public int getLow() {
		return _low;
	}

	/**
	 * Accessor for the high
	 */
	public int getHigh() {
		return _high;
	}

	/**
	 * Accessor for the requiredChar
	 */
	public char getRequiredChar() {
		return _requiredChar;
	}

	/**
	 * Accessor for the password
	 */
	public String getPassword() {
		return _password;
	}
}