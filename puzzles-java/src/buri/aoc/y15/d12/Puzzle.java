package buri.aoc.y15.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Day 12: JSAbacusFramework.io
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(111754L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(65402L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the sum of all numbers in the document?
	 *
	 * Part 2:
	 * Ignore any object (and all of its children) which has any property with the value "red". Do this only for objects
	 * ({...}), not arrays ([...]).
	 */
	protected long runLong(Part part, List<String> input) {
		JsonNode root;
		try {
			ObjectMapper mapper = new ObjectMapper();
			root = mapper.readTree(input.get(0));
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