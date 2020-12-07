package buri.aoc.y20.d04;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import buri.aoc.Part;

/**
 * Data model for a potential passport.
 *
 * @author Brian Uri!
 */
public class Passport {
	private Map<String, String> _fields;

	private static final Set<String> EYE_COLOR = new HashSet<>();
	static {
		EYE_COLOR.add("amb");
		EYE_COLOR.add("blu");
		EYE_COLOR.add("brn");
		EYE_COLOR.add("gry");
		EYE_COLOR.add("grn");
		EYE_COLOR.add("hzl");
		EYE_COLOR.add("oth");
	}
	private static final Pattern HCL_PATTERN = Pattern.compile("^[a-z0-9]{6}$");
	private static final Pattern PID_PATTERN = Pattern.compile("\\d{9}");

	/**
	 * Constructor
	 */
	public Passport(String input) {
		_fields = new HashMap<>();
		for (String token : input.trim().split(" ")) {
			String[] tokens = token.split(":");
			_fields.put(tokens[0], tokens[1]);
		}
	}

	/**
	 * Checks if a passport is valid.
	 *
	 * In part 1, it has all 8 fields, or all fields except "cid".
	 *
	 * In part 2, extra validation is added to each field.
	 */
	public boolean isValid(Part part) {
		Set<String> keys = getFields().keySet();
		boolean isValid = (keys.size() == 8 || keys.size() == 7 && !keys.contains("cid"));
		if (part == Part.ONE || !isValid) {
			return (isValid);
		}
		try {
			// byr (Birth Year) - four digits; at least 1920 and at most 2002.
			isValid = isValid && (isValidYearRange("byr", 1920, 2002));

			// iyr (Issue Year) - four digits; at least 2010 and at most 2020.
			isValid = isValid && (isValidYearRange("iyr", 2010, 2020));

			// eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
			isValid = isValid && (isValidYearRange("eyr", 2020, 2030));

			// hgt (Height) - a number followed by either cm or in:
			// If cm, the number must be at least 150 and at most 193.
			// If in, the number must be at least 59 and at most 76.
			String hgt = getFields().get("hgt");
			Integer height = Integer.valueOf(hgt.substring(0, hgt.length() - 2));
			if (hgt.endsWith("cm")) {
				isValid = isValid && (height >= 150 && height <= 193);
			}
			else if (hgt.endsWith("in")) {
				isValid = isValid && (height >= 59 && height <= 76);
			}
			else {
				isValid = false;
			}

			// hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
			String hcl = getFields().get("hcl");
			isValid = isValid && (hcl.startsWith("#") && HCL_PATTERN.matcher(hcl.substring(1)).matches());

			// ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
			isValid = isValid && (EYE_COLOR.contains(getFields().get("ecl")));

			// pid (Passport ID) - a nine-digit number, including leading zeroes.
			isValid = isValid && (PID_PATTERN.matcher(getFields().get("pid")).matches());

		}
		catch (NumberFormatException e) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Returns true if a field is a valid number in an inclusive range.
	 */
	private boolean isValidYearRange(String key, int minInclusive, int maxInclusive) {
		boolean isValid;
		try {
			Integer value = Integer.valueOf(getFields().get(key));
			isValid = (value >= minInclusive && value <= maxInclusive);
		}
		catch (NumberFormatException e) {
			isValid = false;
		}
		return (isValid);
	}

	/**
	 * Accessor for the fields
	 */
	private Map<String, String> getFields() {
		return _fields;
	}
}