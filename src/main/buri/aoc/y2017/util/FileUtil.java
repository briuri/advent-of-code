package buri.aoc.y2017.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.model.Program;

/**
 * Shared utility methods for extracting input from files.
 * 
 * @author Brian Uri!
 */
public class FileUtil {
	
	/**
	 * Input: String of digits on one line
	 * Output: 1 String of digits, with last linebreak trimmed.
	 */
	public static String getDay01() {
		return (getString("01"));
	}	

	/**
	 * Input: Multiple rows of tab-delimited integers.
	 * Output: Rows of integers.
	 */
	public static List<List<Integer>> getDay02() {
		List<List<Integer>> rows = new ArrayList<>();
		try {
			for (String rawRow : Files.readAllLines(getPath("02"))) {
				rows.add(toIntegers(Arrays.asList(rawRow.split("\t"))));
			}
			return (rows);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}
	
	/**
	 * Input: Multiple rows of space-delimited strings.
	 * Output: Rows of strings.
	 */
	public static List<List<String>> getDay04() {
		List<List<String>> rows = new ArrayList<>();
		try {
			for (String rawRow : Files.readAllLines(getPath("04"))) {
				rows.add(Arrays.asList(rawRow.split(" ")));
			}
			return (rows);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}
	
	/**
	 * Input: One number per line.
	 * Output: List of numbers.
	 */
	public static List<Integer> getDay05() {
		try {
			List<String> rawIntegers = Files.readAllLines(getPath("05"));
			return (toIntegers(rawIntegers));
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}

	/**
	 * Input: One row of tab-delimited numbers.
	 * Output: List of numbers.
	 */
	public static List<Integer> getDay06() {
		String[] rawIntegers = getString("06").split("\t");
		return (toIntegers(Arrays.asList(rawIntegers)));
	}

	/**
	 * Input: name (weight) -> comma/space-delimited child names
	 * Output: map of unique names to programs
	 */
	public static Map<String, Program> getDay07() {
		try {
			final String relation = " -> ";
			final String weightSeparator = " ";
			final String childSeparator = ", ";
			
			Map<String, Program> programs = new HashMap<>();
			for (String line : Files.readAllLines(getPath("07"))) {
				String[] relationship = line.split(relation);
				String[] nameWeight = relationship[0].split(weightSeparator);
				String name = nameWeight[0];
				int weight = Integer.valueOf(nameWeight[1].replace("(", "").replace(")", ""));
				List<String> childNames = new ArrayList<>();
				if (relationship.length > 1) {
					for (String child : relationship[1].split(childSeparator)) {
						childNames.add(child);
					}
				}
				Program program = new Program(name, weight, childNames);
				programs.put(name, program);
			}
			// Load all the children based on their names
			for (Program program : programs.values()) {
				program.loadChildren(programs);
			}
			return (programs);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}
	
	/**
	 * Adds project-relative "data" folder to path.
	 */
	private static Path getPath(String day) {
		return Paths.get("data/2017/" + day + ".txt");
	}
	
	/**
	 * Load all the bytes from an input file as a string.
	 */
	private static String getString(String day) {
		try {
			byte[] rawOutput = Files.readAllBytes(getPath(day));
			return (new String(rawOutput, StandardCharsets.UTF_8.name()).trim());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file", e);
		}
	}
		
	/**
	 * Converts strings into integers.
	 */
	private static List<Integer> toIntegers(List<String> rawIntegers) {
		List<Integer> integers = new ArrayList<>();
		for (String rawInteger : rawIntegers) {
			integers.add(Integer.valueOf(rawInteger));
		}
		return (integers);
	}
}
