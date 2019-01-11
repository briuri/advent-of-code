package buri.aoc.y15.d12;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 12: JSAbacusFramework.io
 * 
 * @author Brian Uri!
 */
public class Day12 extends Puzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2015/12", fileIndex).get(0));
	}

	/**
	 * Part 1:
	 * What is the sum of all numbers in the document?
	 * 
	 * Part 2:
	 * Ignore any object (and all of its children) which has any property with the value "red". Do this only for objects
	 * ({...}), not arrays ([...]).
	 */
	public static int getResult(Part part, String input) {
		JsonNode root;
		try {
			ObjectMapper mapper = new ObjectMapper();
			root = mapper.readTree(input);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file.", e);
		}

		return (getSum(part, root));
	}

	/**
	 * Returns the sum of all numbers in this JSON node.
	 */
	private static int getSum(Part part, JsonNode node) {
		int sum = 0;
		if (node.isArray()) {
			ArrayNode array = (ArrayNode) node;
			for (Iterator<JsonNode> iterator = array.elements(); iterator.hasNext();) {
				sum += getSum(part, iterator.next());
			}
		}
		else if (node.isObject()) {
			ObjectNode object = (ObjectNode) node;
			boolean hasRedProperty = false;
			for (Iterator<JsonNode> iterator = object.elements(); iterator.hasNext();) {
				JsonNode child = iterator.next();
				if (child.isValueNode() && child.asText().equals("red")) {
					hasRedProperty = true;
				}
			}
			if (part == Part.ONE || (part == Part.TWO && !hasRedProperty)) {
				for (Iterator<JsonNode> iterator = object.elements(); iterator.hasNext();) {
					sum += getSum(part, iterator.next());
				}
			}
		}
		else if (node.isNumber()) {
			sum += node.asInt();
		}
		return (sum);
	}
}