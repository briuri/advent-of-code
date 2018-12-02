package buri.aoc.y18.d02;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day02Test {

	@Test
	public void testGetInput() {
		List<String> content = Day02.getInput(0);
		assertEquals(250, content.size());
	}

	/**
	 * abcdef twos=false, threes=false
	 * bababc twos=true, threes=true
	 * abbcde twos=true, threes=false
	 * abcccd twos=false, threes=true
	 * aabcdd twos=true, threes=false
	 * abcdee twos=true, threes=false
	 * ababab twos=false, threes=true
	 * 
	 * Of these box IDs, 4 of them contain a letter which appears exactly twice, and 3 of them contain a letter
	 * which appears exactly three times.
	 * 
	 * Multiplying these together produces a checksum of 4 * 3 = 12.
	 */
	@Test
	public void testPart1Examples() {
		assertEquals("12", Day02.getResult(Part.ONE, Day02.getInput(1)));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		String result = Day02.getResult(Part.ONE, Day02.getInput(0));
		System.out.println("Day 2 Part 1\n\t" + result);
		assertEquals("5750", result);
	}

	/**
	 * The boxes will have IDs which differ by exactly one character at the same position in both strings. For example,
	 * given the following box IDs:
	 * 
	 * abcde
	 * fghij
	 * klmno
	 * pqrst
	 * fguij
	 * axcye
	 * wvxyz
	 * 
	 * The IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.
	 * What letters are common between the two correct box IDs?
	 * 
	 * In the example above, this is found by removing the differing character from either ID, producing fgij.
	 */
	@Test
	public void testPart2Examples() {
		assertEquals("fgij", Day02.getResult(Part.TWO, Day02.getInput(2)));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		String result = Day02.getResult(Part.TWO, Day02.getInput(0));
		System.out.println("Day 2 Part 2\n\t" + result);
		assertEquals("tzyvunogzariwkpcbdewmjhxi", result);
	}
}
