package buri.aoc.y17.d04;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class Day04Test {

	@Test
	public void testGetInput() {
		List<List<String>> rows = Day04.getInput(0);
		assertEquals(512, rows.size());
		assertEquals(10, rows.get(0).size());
		assertEquals("pphsv", rows.get(0).get(0));
	}
	
	/**
	 * aa bb cc dd ee is valid.
	 */
	@Test
	public void testPart1Example1() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("aa");
		passphrase.add("bb");
		passphrase.add("cc");
		passphrase.add("dd");
		passphrase.add("ee");
		passphrases.add(passphrase);
		assertEquals(1, Day04.getResult(Part.ONE, passphrases));
	}

	/**
	 * aa bb cc dd aa is not valid - the word aa appears more than once.
	 */
	@Test
	public void testPart1Example2() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("aa");
		passphrase.add("bb");
		passphrase.add("cc");
		passphrase.add("dd");
		passphrase.add("aa");
		passphrases.add(passphrase);
		assertEquals(0, Day04.getResult(Part.ONE, passphrases));
	}

	/**
	 * aa bb cc dd aaa is valid - aa and aaa count as different words.
	 */
	@Test
	public void testPart1Example3() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("aa");
		passphrase.add("bb");
		passphrase.add("cc");
		passphrase.add("dd");
		passphrase.add("aaa");
		passphrases.add(passphrase);
		assertEquals(1, Day04.getResult(Part.ONE, passphrases));
	}

	/**
	 * Solves the Part 1 puzzle against the real input.
	 */
	@Test
	public void testPart1Puzzle() {
		int result = Day04.getResult(Part.ONE, Day04.getInput(0));
		System.out.println("Day 4 Part 1\n\t" + result);
		assertEquals(466, result);
	}

	/**
	 * abcde fghij is a valid passphrase.
	 */
	@Test
	public void testPart2Example1() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("abcde");
		passphrase.add("fghij");
		passphrases.add(passphrase);
		assertEquals(1, Day04.getResult(Part.TWO, passphrases));
	}

	/**
	 * abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
	 */
	@Test
	public void testPart2Example2() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("abcde");
		passphrase.add("xyz");
		passphrase.add("ecdab");
		passphrases.add(passphrase);
		assertEquals(0, Day04.getResult(Part.TWO, passphrases));
	}

	/**
	 * a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
	 */
	@Test
	public void testPart2Example3() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("a");
		passphrase.add("ab");
		passphrase.add("abc");
		passphrase.add("abd");
		passphrase.add("abf");
		passphrase.add("abj");
		passphrases.add(passphrase);
		assertEquals(1, Day04.getResult(Part.TWO, passphrases));
	}

	/**
	 * iiii oiii ooii oooi oooo is valid.
	 */
	@Test
	public void testPart2Example4() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("iiii");
		passphrase.add("oiii");
		passphrase.add("ooii");
		passphrase.add("oooi");
		passphrase.add("oooo");
		passphrases.add(passphrase);
		assertEquals(1, Day04.getResult(Part.TWO, passphrases));
	}

	/**
	 * oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
	 */
	@Test
	public void testPart2Example5() {
		List<List<String>> passphrases = new ArrayList<>();
		List<String> passphrase = new ArrayList<>();
		passphrase.add("oiii");
		passphrase.add("ioii");
		passphrase.add("iioi");
		passphrase.add("iiio");
		passphrases.add(passphrase);
		assertEquals(0, Day04.getResult(Part.TWO, passphrases));
	}

	/**
	 * Solves the Part 2 puzzle against the real input.
	 */
	@Test
	public void testPart2Puzzle() {
		int result = Day04.getResult(Part.TWO, Day04.getInput(0));
		System.out.println("Day 4 Part 2\n\t" + result);
		assertEquals(251, result);
	}
}
