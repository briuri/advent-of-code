package buri.aoc.y18.d08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 8: Memory Maneuver
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(138, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(48260, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(66, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(25981, result);
	}

	/**
	 * Part 1:
	 * The first check done on the license file is to simply add up all of the metadata entries. What is the sum of all
	 * metadata entries?
	 *
	 * Part 2:
	 * What is the value of the root node?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Integer> data = convertStringsToInts(Arrays.asList(input.get(0).split(" ")));

		List<Node> nodes = new ArrayList<>();
		addNode(data, nodes, 0);

		if (part == Part.ONE) {
			int sum = 0;
			for (Node node : nodes) {
				sum += node.getMetadataSum();
			}
			return (sum);
		}
		// Part TWO
		// The last node in the list is the first node processed.
		return (nodes.get(nodes.size() - 1).getValue());
	}

	/**
	 * Processing the input in sequential order, diving depth-first recursively to generate node tree.
	 */
	private static RecursionData addNode(List<Integer> input, List<Node> nodes, int index) {
		// Process Header and move forward
		int childCount = input.get(index);
		int metadataCount = input.get(index + 1);
		index = index + 2;

		// Process any children.
		List<Node> children = new ArrayList<>();
		while (childCount > 0) {
			RecursionData data = addNode(input, nodes, index);
			index = data.getNextIndex();
			children.add(data.getGeneratedNode());
			childCount--;
		}

		// With all data in hand, build the node at this level.
		Node node = new Node(input.subList(index, index + metadataCount));
		for (Node child : children) {
			node.addChild(child);
		}
		nodes.add(node);
		return (new RecursionData(index + metadataCount, node));
	}
}