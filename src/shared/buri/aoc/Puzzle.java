package buri.aoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
	 * Adds project-relative "data" folder to path.
	 */
	protected static Path getInputPath(String inputId) {
		return Paths.get("data/" + inputId + ".txt");
	}

	/**
	 * Load all the bytes from an input file as a string.
	 */
	protected static String getFileAsString(String inputId) {
		try {
			byte[] rawOutput = Files.readAllBytes(getInputPath(inputId));
			return (new String(rawOutput, StandardCharsets.UTF_8.name()).trim());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}

	/**
	 * Converts strings into integers.
	 */
	protected static List<Integer> getStringsAsIntegers(List<String> rawIntegers) {
		List<Integer> integers = new ArrayList<>();
		for (String rawInteger : rawIntegers) {
			integers.add(Integer.valueOf(rawInteger));
		}
		return (integers);
	}
}
