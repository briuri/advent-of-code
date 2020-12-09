package buri.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Superclass of all puzzles, for shared helper utilities.
 *
 * @author Brian Uri!
 */
public abstract class BasePuzzle {

	/**
	 * Loads a file as a list of string lines.
	 */
	protected static List<String> readFile(int fileIndex) {
		String packageName = new Throwable().getStackTrace()[1].getClassName();
		String year = "20" + packageName.substring(packageName.indexOf("aoc.y") + 5, packageName.indexOf(".d"));
		String day = packageName.substring(packageName.indexOf(".d") + 2, packageName.indexOf(".Day"));

		Path path = Paths.get("data/" + year + "/" + day + "-" + fileIndex + ".txt");
		try {
			return (Files.readAllLines(path));
		}
		catch (IOException e) {
			// Input file hasn't been moved to permanent location yet.
			path = Paths.get("data/new/" + day + "-" + fileIndex + ".txt");
			try {
				return (Files.readAllLines(path));
			}
			catch (IOException e2) {
				throw new IllegalArgumentException("Invalid file: " + path.toAbsolutePath(), e);
			}
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
	 * Gets the sum of a list of integers
	 */
	protected static Integer getSum(List<Integer> list) {
		return (list.stream().mapToInt(Integer::intValue).sum());
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
