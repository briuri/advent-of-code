package buri.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	 * Converts strings into integers.
	 */
	protected static List<Integer> convertStringsToInts(List<String> rawIntegers) {
		List<Integer> integers = new ArrayList<>();
		for (String rawInteger : rawIntegers) {
			integers.add(Integer.valueOf(rawInteger));
		}
		return (integers);
	}
}
