package buri.aoc;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Superclass of all test case classes, for shared helper utilities. First employed in y19.
 * 
 * @author Brian Uri!
 */
public abstract class BaseTest {
		
	/**
	 * Copies text to the system clipboard.
	 */
	protected static void toClipboard(String value) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(value), null);
	}
	
	/**
	 * Copies text to the system clipboard.
	 */
	protected static void toClipboard(int value) {
		toClipboard(String.valueOf(value));
	}
	
	/**
	 * Copies text to the system clipboard.
	 */
	protected static void toClipboard(long value) {
		toClipboard(String.valueOf(value));
	}
}
