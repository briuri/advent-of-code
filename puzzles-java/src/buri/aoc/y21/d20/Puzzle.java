package buri.aoc.y21.d20;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 20: Trench Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(35L, 1, false);
		assertRun(5663L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(3351L, 1, false);
		assertRun(19638L, 0, true);
	}

	private static final char LIT = '#';

	/**
	 * Part 1:
	 * How many pixels are lit in the resulting image? (2 times)
	 *
	 * Part 2:
	 * How many pixels are lit in the resulting image? (50 times)
	 */
	protected long runLong(Part part, List<String> input) {
		String algorithm = input.get(0);
		input = input.subList(2, input.size());
		Map<Pair<Integer>, Integer> image = new HashMap<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(0).length(); x++) {
				char value = input.get(y).charAt(x);
				// Convert character representation into 1s and 0s.
				image.put(new Pair<>(x, y), (value == LIT ? 1 : 0));
			}
		}

		int maxSteps = (part == Part.ONE ? 2 : 50);
		image = enhanceImage(image, algorithm, maxSteps);

		// Since # is represented as 1, the sum of all values will be the number of lit pixels.
		return (PuzzleMath.getIntSum(new ArrayList<>(image.values())));
	}

	/**
	 * Runs all steps of the enhancement on an image.
	 */
	private static Map<Pair<Integer>, Integer> enhanceImage(Map<Pair<Integer>, Integer> image, String algorithm, int maxSteps) {
		for (int step = 0; step < maxSteps; step++) {
			// Figure out bounds of all known points and add a padding around edges (padding points depend on inner
			// ones).
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;
			for (Pair<Integer> point : image.keySet()) {
				minX = Math.min(minX, point.getX());
				maxX = Math.max(maxX, point.getX());
				minY = Math.min(minY, point.getY());
				maxY = Math.max(maxY, point.getY());
			}
			final int padding = 2;

			Map<Pair<Integer>, Integer> enhancedImage = new HashMap<>();
			for (int y = minY - padding; y < maxY + padding; y++) {
				for (int x = minX - padding; x < maxX + padding; x++) {
					Pair<Integer> point = new Pair<>(x, y);
					char newValue = enhancePoint(image, algorithm, point, step);
					enhancedImage.put(point, (newValue == LIT ? 1 : 0));
				}
			}
			image = enhancedImage;
		}
		return (image);
	}

	/**
	 * Builds a binary string using the 9 numbers centered around a point and converts to integer. Then returns the
	 * lit/unlit value in the enhancement algorithm.
	 *
	 * Uses different default values (for the edge space) on odd/even steps, based on the 0th/511th position in the
	 * algorithm.
	 * - When the 0th character is ., all unlit edge space pixels stay unlit after an enhance (like the sample).
	 * - When the 0th character is #, all unlit edge space pixels turn on after an enhance so we must avoid storing
	 * infinite points on odd numbered enhances.
	 */
	private static char enhancePoint(Map<Pair<Integer>, Integer> image, String algorithm, Pair<Integer> point, int step) {
		// When 0th is '#', everything outside the area of interest is lit during odd steps (0-indexed).
		int defaultValue = (algorithm.charAt(0) == LIT && step % 2 == 1 ? 1 : 0);

		StringBuilder builder = new StringBuilder();
		builder.append(image.getOrDefault(new Pair<>(point.getX() - 1, point.getY() - 1), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX(), point.getY() - 1), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX() + 1, point.getY() - 1), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX() - 1, point.getY()), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX(), point.getY()), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX() + 1, point.getY()), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX() - 1, point.getY() + 1), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX(), point.getY() + 1), defaultValue));
		builder.append(image.getOrDefault(new Pair<>(point.getX() + 1, point.getY() + 1), defaultValue));
		int algorithmIndex = Integer.parseInt(builder.toString(), 2);

		return (algorithm.charAt(algorithmIndex));
	}
}