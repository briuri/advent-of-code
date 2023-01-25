package buri.aoc.common;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Quad;
import buri.aoc.y15.d01.Puzzle;

import static org.junit.Assert.assertEquals;

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
		String year = packageName.substring(packageName.indexOf("aoc.y") + 5, packageName.indexOf(".d"));
		String day = packageName.substring(packageName.indexOf(".d") + 2, packageName.indexOf(".Puzzle"));

		Path path = Paths.get("data/y" + year + "/" + day + "-" + fileIndex + ".txt");
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
				T value = grid.get(new Pair<>(x, y));
				builder.append(value == null ? ' ' : value);
			}
			builder.append("\n");
		}
		builder.append("\n");
		System.out.println(builder);
	}

	/**
	 * Finds the bounds of a rectangle.
	 */
	public static Quad<Integer> getBounds(Set<Pair<Integer>> pairs) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Pair<Integer> pair : pairs) {
			minX = Math.min(minX, pair.getX());
			maxX = Math.max(maxX, pair.getX());
			minY = Math.min(minY, pair.getY());
			maxY = Math.max(maxY, pair.getY());
		}
		return (new Quad<>(minX, maxX, minY, maxY));
	}

	/**
	 * Renders a sparse set of points as a visual grid.
	 */
	public static void showAsGrid(Set<Pair<Integer>> grid) {
		Quad<Integer> bounds = getBounds(grid);
		StringBuilder builder = new StringBuilder();
		for (int y = bounds.getZ(); y <= bounds.getT(); y++) {
			for (int x = bounds.getX(); x <= bounds.getY(); x++) {
				Pair<Integer> point = new Pair<>(x, y);
				if (grid.contains(point)) {
					builder.append('■');
				}
				else {
					builder.append(' ');
				}
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

	/**
	 * Builds an identifier for test output, based on the package name (buri.aoc.yXX.dYY) and test name
	 * (testPartXPuzzle).
	 */
	protected String getIdentifier() {
		String packageName = this.getClass().getPackage().getName();
		String year = packageName.substring(packageName.indexOf("aoc.y") + 5, packageName.indexOf(".d"));
		String day = packageName.substring(packageName.indexOf(".d") + 2);

		String testName = Thread.currentThread().getStackTrace()[3].getMethodName();
		String part = testName.substring(testName.indexOf("Part") + 4).substring(0, 1);

		StringBuilder builder = new StringBuilder();
		builder.append("### Year ").append(year).append(" Day ").append(day).append(" Part ").append(part).append(
				" ###");
		return (builder.toString());
	}

	/**
	 * Copies a result to the console and the system clipboard.
	 */
	protected <T> void toConsole(T result) {
		String value = String.valueOf(result);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(value), null);
		System.out.println(getIdentifier());
		System.out.println(value);
	}
}