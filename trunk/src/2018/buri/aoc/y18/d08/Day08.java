package buri.aoc.y18.d08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The navigation system's license file consists of a list of numbers (your puzzle input). The numbers define a data
 * structure which, when processed, produces some kind of tree that can be used to calculate the license number.
 * 
 * The tree is made up of nodes; a single, outermost node forms the tree's root, and it contains all other nodes in the
 * tree (or contains nodes that contain nodes, and so on).
 * 
 * Specifically, a node consists of:
 * A header, which is always exactly two numbers:
 * The quantity of child nodes.
 * The quantity of metadata entries.
 * Zero or more child nodes (as specified in the header).
 * One or more metadata entries (as specified in the header).
 * 
 * Each child node is itself a node that has its own header, child nodes, and metadata.
 * 
 * @author Brian Uri!
 */
public class Day08 extends Puzzle {

	/**
	 * Input: Line of integers
	 * Output: List of integers
	 */
	public static List<Integer> getInput(int fileIndex) {
		String input = readFile("2018/08", fileIndex).get(0);
		return (convertStringsToInts(Arrays.asList(input.split(" "))));
	}

	/**
	 * Part 1:
	 * The first check done on the license file is to simply add up all of the metadata entries. What is the sum of all
	 * metadata entries?
	 * 
	 * Part 2:
	 * What is the value of the root node?
	 */
	public static int getResult(Part part, List<Integer> input) {
		List<Node> nodes = new ArrayList<>();
		addNode(input, nodes, 0);

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
	public static RecursionData addNode(List<Integer> input, List<Node> nodes, int index) {
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