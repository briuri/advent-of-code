package buri.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Superclass of all puzzles, for shared helper utilities.
 * 
 * @author Brian Uri!
 */
public abstract class Puzzle {
		
	/**
	 * Loads a file as a list of string lines.
	 */
	protected static List<String> readFile(String puzzleId, int fileIndex) {
		Path path = Paths.get("data/" + puzzleId + "-" + fileIndex + ".txt");
		try {
			return (Files.readAllLines(path));
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file: " + path.toAbsolutePath(), e);
		}
	}

	/**
	 * Gets the sum of a list of integers
	 */
	protected static Integer getSum(List<Integer> list) {
		return (list.stream().mapToInt(Integer::intValue).sum());
	}
	
	/**
	 * Converts strings into integers.
	 */
	public static List<Integer> convertStringsToInts(List<String> rawIntegers) {
		return (rawIntegers.stream().map(Integer::valueOf).collect(Collectors.toList()));
	}
}
