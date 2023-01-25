package buri.aoc.y16.d02;

import java.util.List;

import buri.aoc.common.Part;
import buri.aoc.common.data.grid.IntGrid;
import buri.aoc.common.data.tuple.Pair;

/**
 * Data model for keypad.
 * 
 * @author Brian Uri!
 */
public class Keypad extends IntGrid {

	private Pair<Integer> _current;

	/**
	 * Constructor
	 * 
	 * Models both layouts of keypad in a 7x7 grid (0 represents an edge). Both layouts start at the "5" button.
	 */
	public Keypad(Part part) {
		super(new Pair(7, 7));
		if (part == Part.ONE) {
			getGrid()[0] = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[1] = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[2] = new Integer[] { 0, 0, 1, 4, 7, 0, 0 };
			getGrid()[3] = new Integer[] { 0, 0, 2, 5, 8, 0, 0 };
			getGrid()[4] = new Integer[] { 0, 0, 3, 6, 9, 0, 0 };
			getGrid()[5] = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[6] = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
			_current = new Pair(3, 3);
		}
		else {
			getGrid()[0] = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[1] = new Integer[] { 0, 0, 0, 5, 0, 0, 0 };
			getGrid()[2] = new Integer[] { 0, 0, 2, 6, 10, 0, 0 };
			getGrid()[3] = new Integer[] { 0, 1, 3, 7, 11, 13, 0 };
			getGrid()[4] = new Integer[] { 0, 0, 4, 8, 12, 0, 0 };
			getGrid()[5] = new Integer[] { 0, 0, 0, 9, 0, 0, 0 };
			getGrid()[6] = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
			_current = new Pair(1, 3);
		}
	}

	/**
	 * From the previous button, follow the instructions to reach the next button. Ignore any move that doesn't lead to
	 * a button.
	 */
	public String getButtons(List<String> input) {
		StringBuffer buffer = new StringBuffer();
		for (String line : input) {
			for (char direction : line.toCharArray()) {
				int x = getCurrent().getX();
				int y = getCurrent().getY();
				switch (direction) {
					case 'U':
						if (get(x, y - 1) != 0) {
							getCurrent().setY(y - 1);
						}
						break;
					case 'R':
						if (get(x + 1, y) != 0) {
							getCurrent().setX(x + 1);
						}
						break;
					case 'D':
						if (get(x, y + 1) != 0) {
							getCurrent().setY(y + 1);
						}
						break;
					default: // 'L'
						if (get(x - 1, y) != 0) {
							getCurrent().setX(x - 1);
						}
				}
			}
			String hexString = Integer.toHexString(get(getCurrent()));
			buffer.append(hexString.toUpperCase());
		}
		return (buffer.toString());
	}

	/**
	 * Accessor for the current
	 */
	private Pair<Integer> getCurrent() {
		return _current;
	}
}