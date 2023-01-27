package buri.aoc.common;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Superclass of all puzzles, for shared helper utilities.
 *
 * @author Brian Uri!
 */
public abstract class BasePuzzle {

	/**
	 * Base run method for a puzzle to be overridden.
	 */
	protected long runLong(Part part, List<String> input) {
		throw new UnsupportedOperationException("This method needs to be overridden.");
	}

	/**
	 * Base run method for a puzzle to be overridden.
	 */
	protected String runString(Part part, List<String> input) {
		throw new UnsupportedOperationException("This method needs to be overridden.");
	}

	/**
	 * Helper method to run a puzzle with some input and check the answer.
	 */
	protected void assertRun(Long expected, int fileIndex, boolean toConsole) {
		Part part = getPart().equals("1") ? Part.ONE : Part.TWO;
		Long result = this.runLong(part, getInput(fileIndex));
		if (toConsole) {
			toConsole(result);
		}
		assertEquals(expected, result);
	}

	/**
	 * Helper method to run a puzzle with some input and check the answer.
	 */
	protected void assertRun(String expected, int fileIndex, boolean toConsole) {
		Part part = getPart().equals("1") ? Part.ONE : Part.TWO;
		String result = this.runString(part, getInput(fileIndex));
		if (toConsole) {
			toConsole(result);
		}
		if (expected.contains("■")) {
			assertTrue(result.startsWith(expected));
		}
		else {
			assertEquals(expected, result);
		}
	}

	/**
	 * Returns the input file as a list of strings.
	 */
	public List<String> getInput(int fileIndex) {
		Path path = Paths.get("data/y" + getYear() + "/" + getDay() + "-" + fileIndex + ".txt");
		try {
			return (Files.readAllLines(path));
		}
		// Current puzzle stores data in a holding area for faster saving / browsing.
		catch (IOException e) {
			Path newPath = Paths.get("dataNew/" + getDay() + "-" + fileIndex + ".txt");
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
	 * Builds an identifier for test output, based on the package name (buri.aoc.yXX.dYY) and test name
	 * (testPartXPuzzle).
	 */
	protected String getIdentifier() {
		return ("### Year " + getYear() + " Day " +	getDay() + " Part " + getPart() + " ###");
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

	/**
	 * Extracts the year from a package name.
	 */
	private String getYear() {
		String packageName = this.getClass().getPackage().getName();
		return (packageName.substring(packageName.indexOf("aoc.y") + 5, packageName.indexOf(".d")));
	}

	/**
	 * Extracts the day from a package name.
	 */
	private String getDay() {
		String packageName = this.getClass().getPackage().getName();
		return (packageName.substring(packageName.indexOf(".d") + 2));
	}

	/**
	 * Extracts the part number from a test method name.
	 */
	private String getPart() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		String testName = "";
		for (StackTraceElement stackTraceElement : trace) {
			if (stackTraceElement.getMethodName().contains("testPart")) {
				testName = stackTraceElement.getMethodName();
				break;
			}
		}
		return (testName.substring(testName.indexOf("Part") + 4));
	}
}