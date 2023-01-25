package buri.aoc.common;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Superclass of all test case classes, for shared helper utilities.
 *
 * @author Brian Uri!
 */
public abstract class BaseTest {

	/**
	 * Builds an identifier for test output, based on the package name (buri.aoc.yXX.dYY) and test name
	 * (testPartXPuzzle).
	 */
	private String getIdentifier() {
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