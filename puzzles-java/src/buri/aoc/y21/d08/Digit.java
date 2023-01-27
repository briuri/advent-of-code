package buri.aoc.y21.d08;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model for an LED display.
 *
 * Names the 7 segments using the original puzzle letters:
 *  A
 * B C
 *  D
 * E F
 *  G
 *
 * Instantiates with the inputs (left of | in file) and deduces which wires map to which segments.
 *
 * Number 	#Wires 		DefaultConfig
 * 1 		2 (unique) 	cf
 * 7 		3 (unique) 	acf
 * 4 		4 (unique) 	bcdf
 * 8 		7 (unique) 	abcdefg
 * 2 		5 			acdeg
 * 3 		5 			acdfg
 * 5 		5 			abdfg
 * 0 		6 			abcefg
 * 6 		6 			abdefg
 * 9 		6 			abcdfg
 *
 * Compares frequencies of segments between Numbers to narrow down possibilities in the 5/6 length.
 *
 * LengthFive Frequencies:
 * 		a3, b1, c2, d3, e1, f2, g3
 * LengthSix Frequencies:
 * 		a3, b3, c2, d2, e2, f3, g3
 *
 * @author Brian Uri!
 */
public class Digit {
	private final char[] _segments = new char[7];

	/**
	 * Constructor connects wires to correct segments so digits can be parsed.
	 */
	public Digit(String[] input) {
		// Unique configurations
		String one = getWires(input, 2).get(0);
		String seven = getWires(input, 3).get(0);
		String four = getWires(input, 4).get(0);
		String eight = getWires(input, 7).get(0);
		// Numbers with same lengths
		List<String> lenFive = getWires(input, 5);
		List<String> lenSix = getWires(input, 6);

		// Find A wire
		for (char value : seven.toCharArray()) {
			if (one.indexOf(value) == -1) {
				setSegA(value);
			}
		}

		// Find B, D wire
		for (char value : four.toCharArray()) {
			if (one.indexOf(value) == -1) {
				if (countOccurrences(lenFive, value) == 1) {
					setSegB(value);
				}
				if (countOccurrences(lenFive, value) == 3) {
					setSegD(value);
				}
			}
		}

		// Find C, F wire
		for (char value : one.toCharArray()) {
			if (countOccurrences(lenFive, value) == 2 && countOccurrences(lenSix, value) == 2) {
				setSegC(value);
			}
			if (countOccurrences(lenFive, value) == 2 && countOccurrences(lenSix, value) == 3) {
				setSegF(value);
			}
		}

		// Find E, G wire
		for (char value : eight.toCharArray()) {
			if (seven.indexOf(value) == -1 && four.indexOf(value) == -1) {
				if (countOccurrences(lenFive, value) == 1 && countOccurrences(lenSix, value) == 2) {
					setSegE(value);
				}
				if (countOccurrences(lenFive, value) == 3 && countOccurrences(lenSix, value) == 3) {
					setSegG(value);
				}
			}
		}
	}

	/**
	 * Takes a wire configuration and rewires it to match the correct segments.
	 *
	 * @param out a single digit in the output
	 * @return the digit that should appear on the display
	 */
	public char parse(String out) {
		if (out.length() == 2) {
			return ('1');
		}
		if (out.length() == 3) {
			return ('7');
		}
		if (out.length() == 4) {
			return ('4');
		}
		if (out.length() == 7) {
			return ('8');
		}
		if (out.length() == 5) {
			// Every number in this group has A, D, and G, so no need to check for those.
			if (out.indexOf(getSegC()) != -1 && out.indexOf(getSegE()) != -1) {
				return ('2');
			}
			if (out.indexOf(getSegC()) != -1 && out.indexOf(getSegF()) != -1) {
				return ('3');
			}
			if (out.indexOf(getSegB()) != -1 && out.indexOf(getSegF()) != -1) {
				return ('5');
			}
		}

		// 6
		// Every number in this group has A, B, F, and G, so no need to check for those.
		if (out.indexOf(getSegC()) != -1 && out.indexOf(getSegE()) != -1) {
			return ('0');
		}
		if (out.indexOf(getSegD()) != -1 && out.indexOf(getSegE()) != -1) {
			return ('6');
		}
		if (out.indexOf(getSegC()) != -1 && out.indexOf(getSegD()) != -1) {
			return ('9');
		}
		throw new RuntimeException("Couldn't parse: " + out);
	}


	/**
	 * Counts how many times a wire appears in a list of inputs.
	 */
	protected static int countOccurrences(List<String> ins, char value) {
		int sum = 0;
		for (String in : ins) {
			if (in.indexOf(value) != -1) {
				sum++;
			}
		}
		return (sum);
	}

	/**
	 * Returns a list of all inputs that match the given length.
	 */
	protected static List<String> getWires(String[] input, int length) {
		List<String> ins = new ArrayList<>();
		for (String in : input) {
			if (in.length() == length) {
				ins.add(in);
			}
		}
		return (ins);
	}

	/**
	 * Accessor for the wire leading to segment A
	 */
	private void setSegA(char value) {
		_segments[0] = value;
	}

	/**
	 * Accessor for the wire leading to segment B
	 */
	private char getSegB() {
		return (_segments[1]);
	}

	/**
	 * Accessor for the wire leading to segment B
	 */
	private void setSegB(char value) {
		_segments[1] = value;
	}

	/**
	 * Accessor for the wire leading to segment C
	 */
	private char getSegC() {
		return (_segments[2]);
	}

	/**
	 * Accessor for the wire leading to segment C
	 */
	private void setSegC(char value) {
		_segments[2] = value;
	}

	/**
	 * Accessor for the wire leading to segment D
	 */
	private char getSegD() {
		return (_segments[3]);
	}

	/**
	 * Accessor for the wire leading to segment D
	 */
	private void setSegD(char value) {
		_segments[3] = value;
	}

	/**
	 * Accessor for the wire leading to segment E
	 */
	private char getSegE() {
		return (_segments[4]);
	}

	/**
	 * Accessor for the wire leading to segment E
	 */
	private void setSegE(char value) {
		_segments[4] = value;
	}

	/**
	 * Accessor for the wire leading to segment F
	 */
	private char getSegF() {
		return (_segments[5]);
	}

	/**
	 * Accessor for the wire leading to segment F
	 */
	private void setSegF(char value) {
		_segments[5] = value;
	}

	/**
	 * Accessor for the wire leading to segment G
	 */
	private void setSegG(char value) {
		_segments[6] = value;
	}
}