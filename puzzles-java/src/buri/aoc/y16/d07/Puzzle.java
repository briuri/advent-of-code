package buri.aoc.y16.d07;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 7: Internet Protocol Version 7
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2L, 1, false);
		assertRun(110L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3L, 2, false);
		assertRun(242L, 0, true);
	}

	/**
	 * Part 1:
	 * How many IPs in your puzzle input support TLS?
	 *
	 * Part 2:
	 * How many IPs in your puzzle input support SSL?
	 */
	protected long runLong(Part part, List<String> input) {
		int count = 0;
		for (String ip : input) {
			if ((part == Part.ONE && supportsTLS(ip)) || (part == Part.TWO && supportsSSL(ip))) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Returns true if the rules for TLS are obeyed.
	 */
	public static boolean supportsTLS(String fullIP) {
		List<String> supernets = new ArrayList<>();
		List<String> hypernets = new ArrayList<>();
		chunkIP(fullIP, supernets, hypernets);
		for (String hypernet : hypernets) {
			if (hasABBA(hypernet)) {
				return (false);
			}
		}
		for (String supernet : supernets) {
			if (hasABBA(supernet)) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Returns true if the rules for SSL are obeyed.
	 */
	public static boolean supportsSSL(String fullIP) {
		List<String> supernets = new ArrayList<>();
		List<String> hypernets = new ArrayList<>();
		chunkIP(fullIP, supernets, hypernets);
		List<String> abas = getABAs(supernets);
		for (String aba : abas) {
			if (hasBAB(hypernets, aba)) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Breaks an IP down into supernets and hypernets for processing.
	 */
	private static void chunkIP(String fullIP, List<String> supernets, List<String> hypernets) {
		String[] partialSplit = fullIP.split("\\[");
		supernets.add(partialSplit[0]);
		for (int i = 1; i < partialSplit.length; i++) {
			String[] fullSplit = partialSplit[i].split("]");
			hypernets.add(fullSplit[0]);
			supernets.add(fullSplit[1]);
		}
	}

	/**
	 * Checks if a string has any four-character sequence which consists of a pair of two different characters followed
	 * by the reverse of that pair.
	 */
	private static boolean hasABBA(String string) {
		StringBuilder back = new StringBuilder();
		for (int i = 0; i < string.length() - 3; i++) {
			String front = string.substring(i, i + 2);
			back.replace(0, 2, string.substring(i + 2, i + 4)).reverse();
			if ((front.charAt(0) != front.charAt(1)) && front.equals(back.toString())) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Searches the supernets for all ABA patterns and returns a list of them.
	 */
	private static List<String> getABAs(List<String> supernets) {
		List<String> abas = new ArrayList<>();
		for (String supernet : supernets) {
			StringBuilder back = new StringBuilder();
			for (int i = 0; i < supernet.length() - 2; i++) {
				String front = supernet.substring(i, i + 2);
				back.replace(0, 2, supernet.substring(i + 1, i + 3)).reverse();
				if ((front.charAt(0) != front.charAt(1)) && front.equals(back.toString())) {
					abas.add(supernet.substring(i, i + 3));
				}
			}
		}
		return (abas);
	}

	/**
	 * Searches for the paired BAB pattern in all the hypernets and returns true if found.
	 */
	private static boolean hasBAB(List<String> hypernets, String aba) {
		String bab = aba.substring(1, 3) + aba.charAt(1);
		for (String hypernet : hypernets) {
			if (hypernet.contains(bab)) {
				return (true);
			}
		}
		return (false);
	}
}