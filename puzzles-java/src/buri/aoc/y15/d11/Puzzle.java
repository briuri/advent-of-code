package buri.aoc.y15.d11;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Day 11: Corporate Policy
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("hepxxyzz", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("heqaabcc", 0, true);
	}

	private static final Pattern FORBIDDEN_CHARS = Pattern.compile("[iol]");
	private static final Pattern STRAIGHT = Pattern.compile(
		"(abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz)+");
	private static final Pattern REPEATING_CHARS = Pattern.compile("(\\w)\\1+");

	/**
	 * Part 1:
	 * Given Santa's current password (your puzzle input), what should his next password be?
	 *
	 * Part 2:
	 * Santa's password expired again. What's the next one?
	 */
	protected String runString(Part part, List<String> input) {
		String password = input.get(0);
		int times = (part == Part.ONE ? 1 : 2);
		for (int i = 0; i < times; i++) {
			password = increment(password);
			while (!isValid(password)) {
				password = increment(password);
			}
		}
		return (password);
	}

	/**
	 * Checks if a password meets the corporate policy.
	 *
	 * Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to
	 * xyz. They cannot skip letters; abd doesn't count.
	 * Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are
	 * therefore confusing.
	 * Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
	 */
	public static boolean isValid(String password) {
		if (FORBIDDEN_CHARS.matcher(password).find() || !STRAIGHT.matcher(password).find()) {
			return (false);
		}
		Set<String> repeats = new HashSet<>();
		Matcher matcher = REPEATING_CHARS.matcher(password);
		while (matcher.find()) {
			repeats.add(matcher.group());
		}
		return (repeats.size() > 1);
	}

	/**
	 * Increments the password.
	 */
	private static String increment(String password) {
		StringBuilder builder = new StringBuilder(password);
		for (int i = password.length() - 1; i >= 0; i--) {
			boolean wrapped = incrementCharacter(builder, i);
			if (!wrapped) {
				break;
			}
		}
		return (builder.toString());
	}

	/**
	 * Increments the character at the given index by 1. Returns true on an overflow / wrap.
	 */
	private static boolean incrementCharacter(StringBuilder builder, int index) {
		char value = builder.charAt(index);
		value = (value == 'z' ? 'a' : (char) (value + 1));
		builder.setCharAt(index, value);
		return (value == 'a');
	}
}