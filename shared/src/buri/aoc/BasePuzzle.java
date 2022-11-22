package buri.aoc;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import buri.aoc.data.tuple.Pair;

/**
 * Superclass of all puzzles, for shared helper utilities.
 *
 * @author Brian Uri!
 */
public abstract class BasePuzzle {

	/**
	 * Returns the input file as a list of strings.
	 */
	public static List<String> getInput(int fileIndex) {
		String packageName = new Throwable().getStackTrace()[1].getClassName();
		String day = packageName.substring(packageName.indexOf(".d") + 2, packageName.indexOf(".Puzzle"));

		Path path = Paths.get("data/" + day + "-" + fileIndex + ".txt");
		try {
			return (Files.readAllLines(path));
		}
		// Current puzzle stores data in a holding area for faster saving / browsing.
		catch (IOException e) {
			Path newPath = Paths.get("dataNew/" + day + "-" + fileIndex + ".txt");
			try {
				return (Files.readAllLines(newPath));
			}
			catch (IOException e2) {
				// Fall through.
			}
			throw new IllegalArgumentException("Invalid file: " + path.toAbsolutePath(), e);
		}
	}

	/**
	 * Converts strings into integers.
	 */
	public static List<Integer> convertStringsToInts(List<String> rawIntegers) {
		return (rawIntegers.stream().map(Integer::valueOf).collect(Collectors.toList()));
	}

	/**
	 * Converts strings into longs.
	 */
	public static List<Long> convertStringsToLongs(List<String> rawLongs) {
		return (rawLongs.stream().map(Long::valueOf).collect(Collectors.toList()));
	}

	/**
	 * Renders a sparse set of points as a visual grid.
	 */
	public static <T> void showAsGrid(Map<Pair<Integer>, T> grid) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Pair<Integer> point : grid.keySet()) {
			minX = Math.min(minX, point.getX());
			maxX = Math.max(maxX, point.getX());
			minY = Math.min(minY, point.getY());
			maxY = Math.max(maxY, point.getY());
		}
		StringBuilder builder = new StringBuilder();
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x < maxX; x++) {
				builder.append(grid.get(new Pair<>(x, y)));
			}
			builder.append("\n");
		}
		builder.append("\n");
		System.out.println(builder);
	}

	/**
	 * Gets the entry with the minimum value from a Map
	 */
	public static <S, T extends Comparable> Map.Entry<S, T> getMin(Map<S, T> map) {
		Map.Entry<S, T> minEntry = null;
		for (Map.Entry<S, T> entry : map.entrySet()) {
			if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
				minEntry = entry;
			}
		}
		return (minEntry);
	}

	/**
	 * Gets the entry with the maximum value from a Map
	 */
	protected static <S, T extends Comparable> Map.Entry<S, T> getMax(Map<S, T> map) {
		Map.Entry<S, T> maxEntry = null;
		for (Map.Entry<S, T> entry : map.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return (maxEntry);
	}

	/**
	 * Converts a long into a BigInteger
	 */
	protected static BigInteger toBigInt(long value) {
		return (new BigInteger(String.valueOf(value)));
	}

	/**
	 * Gets the sum of a list of integers
	 */
	protected static int getIntSum(List<Integer> list) {
		return (list.stream().mapToInt(Integer::intValue).sum());
	}

	/**
	 * Gets the sum of a list of longs
	 */
	protected static long getLongSum(List<Long> list) {
		return (list.stream().mapToLong(Long::longValue).sum());
	}

	/**
	 * Calculates the least common multiple of two numbers.
	 */
	protected static long getLCM(long a, long b) {
		return (a / getGCD(a, b) * b);
	}

	/**
	 * Recursively finds the greatest common denominator of two numbers.
	 */
	public static long getGCD(long a, long b) {
		if (a == 0) {
			return (b);
		}
		return (getGCD(b % a, a));
	}
}