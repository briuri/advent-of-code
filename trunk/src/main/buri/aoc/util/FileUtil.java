package buri.aoc.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Miscellaneous file utility methods.
 * 
 * @author Brian Uri!
 */
public class FileUtil {

	/**
	 * Loads the file at the provided path and returns its contents as a string.
	 * 
	 * @throws IOException on file I/O issues
	 */
	public static String getDay1NumberString(String filePath) throws IOException {
		return (new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8.name()).trim());
	}
	
	/**
	 * Loads the file at the provided path and returns its contents as a List of integers.
	 * 
	 * @throws IOException on file I/O issues
	 */
	public static List<List<Integer>> getDay2Spreadsheet(String filePath) throws IOException {
		List<List<Integer>> rows = new ArrayList<>();
		for (String rawRow : Files.readAllLines(Paths.get(filePath))) {
			List<Integer> intTokens = new ArrayList<>();
			for (String token : Arrays.asList(rawRow.split("\t"))) {
				try {
					intTokens.add(Integer.valueOf(token));
				} catch (NumberFormatException e) {
					throw new IOException(e.getMessage(), e);
				}
			}
			rows.add(intTokens);
		}
		return (rows);
	}

	/**
	 * Loads the file at the provided path and returns its contents as a List of Strings.
	 * 
	 * @throws IOException on file I/O issues
	 */
	public static List<List<String>> getDay4Passphrases(String filePath) throws IOException {
		List<List<String>> rows = new ArrayList<>();
		for (String rawRow : Files.readAllLines(Paths.get(filePath))) {
			List<String> tokens = new ArrayList<>();
			for (String token : Arrays.asList(rawRow.split(" "))) {
				tokens.add(token);
			}
			rows.add(tokens);
		}
		return (rows);
	}
}