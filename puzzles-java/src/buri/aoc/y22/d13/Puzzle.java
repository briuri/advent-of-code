package buri.aoc.y22.d13;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Day 13: Distress Signal
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(13L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5588L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(140L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(23958L, result);
	}

	private static final int IN_ORDER = -1;
	private static final int NO_DECISION = 0;
	private static final int NOT_IN_ORDER = 1;

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final List<String> DIVIDERS = new ArrayList<>();
	static {
		DIVIDERS.add("[[2]]");
		DIVIDERS.add("[[6]]");
	}

	/**
	 * Custom comparator for two packets.
	 */
	private static final Comparator<JsonNode> COMPARATOR = new Comparator<JsonNode>() {
		@Override
		public int compare(JsonNode left, JsonNode right) {
			return comparePackets(left, right);
		}
	};

	/**
	 * Part 1:
	 * What is the sum of the indices of those pairs?
	 *
	 * Part 2:
	 * What is the decoder key for the distress signal?
	 */
	public static long getResult(Part part, List<String> input) {
		if (part == Part.ONE) {
			int pair = 1;
			long sum = 0;
			for (int i = 0; i < input.size(); i = i + 3) {
				int compare = comparePackets(buildNode(input.get(i)), buildNode(input.get(i + 1)));
				if (compare == IN_ORDER) {
					sum += pair;
				}
				pair++;
			}
			return sum;
		}

		// Part 2
		List<JsonNode> allNodes = new ArrayList<>();
		for (String node : input) {
			if (node.length() > 0) {
				allNodes.add(buildNode(node));
			}
		}
		for (String divider : DIVIDERS) {
			allNodes.add(buildNode(divider));
		}
		allNodes.sort(COMPARATOR);

		long product = 1;
		for (int i = 0; i < allNodes.size(); i++) {
			if (DIVIDERS.contains(allNodes.get(i).toString())) {
				product = product * (i + 1);
			}
		}
		return (product);
	}

	/**
	 * Compares packets using the rules.
	 */
	protected static int comparePackets(JsonNode left, JsonNode right) {
		// If exactly one value is an integer, convert it to a list containing that integer and retry comparison.
		if (right.isArray() && left.isNumber()) {
			left = buildNode("[" + left.asInt() + "]");
		}
		if (left.isArray() && right.isNumber()) {
			right = buildNode("[" + right.asInt() + "]");
		}

		// If both values are integers, the lower integer should come first. No decision if both are equal.
		if (left.isNumber() && right.isNumber()) {
			if (left.asInt() < right.asInt()) {
				return IN_ORDER;
			}
			if (left.asInt() > right.asInt()) {
				return NOT_IN_ORDER;
			}
			return NO_DECISION;
		}

		// If both values are lists, compare the first value of each list, then the second value, and so on.
		// If the left list runs out of items first, the inputs are in the right order.
		// If the right list runs out of items first, the inputs are not in the right order.
		// Otherwise, no decision.
		else if (left.isArray() && right.isArray()) {
			for (int i = 0; i < left.size(); i++) {
				if (i >= right.size()) {
					return NOT_IN_ORDER;
				}
				int nestedCompare = comparePackets(left.get(i), right.get(i));
				if (nestedCompare != NO_DECISION) {
					return nestedCompare;
				}
			}
			if (left.size() < right.size()) {
				return (IN_ORDER);
			}
		}
		return NO_DECISION;
	}

	/**
	 * Utility method to build a JsonNode and convert checked exceptions.
	 */
	protected static JsonNode buildNode(String nodeString) {
		try {
			return (MAPPER.readTree(nodeString));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}