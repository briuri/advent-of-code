package buri.aoc.y20.d02;

import buri.aoc.Part;
import buri.aoc.data.CharFrequency;

/**
 * Data model for a password policy and test password.
 *
 * @author Brian Uri!
 */
public class Policy {
	private int _low;
	private int _high;
	private char _requiredChar;
	private String _password;

	/**
	 * Constructor
	 *
	 * 9-11 p: pppppppppxblp
	 */
	public Policy(String input) {
		String[] tokens = input.split(" ");
		_low = Integer.valueOf(tokens[0].split("-")[0]);
		_high = Integer.valueOf(tokens[0].split("-")[1]);
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