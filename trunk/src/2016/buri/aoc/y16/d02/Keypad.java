package buri.aoc.y16.d02;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.data.AbstractGrid;

/**
 * Data model for keypad
 * 
 * @author Brian Uri!
 */
public class Keypad extends AbstractGrid {
	
	private Position _current;
	
	/**
	 * Constructor (0 represents edge)
	 */
	public Keypad(Part part) {
		super(7);
		if (part == Part.ONE) {
			getGrid()[0] = new long[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[1] = new long[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[2] = new long[] { 0, 0, 1, 4, 7, 0, 0 };
			getGrid()[3] = new long[] { 0, 0, 2, 5, 8, 0, 0 };
			getGrid()[4] = new long[] { 0, 0, 3, 6, 9, 0, 0 };
			getGrid()[5] = new long[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[6] = new long[] { 0, 0, 0, 0, 0, 0, 0 };
			_current = new Position(3, 3);
		}
		else {
			getGrid()[0] = new long[] { 0, 0, 0, 0, 0, 0, 0 };
			getGrid()[1] = new long[] { 0, 0, 0, 5, 0, 0, 0 };
			getGrid()[2] = new long[] { 0, 0, 2, 6, 10, 0, 0 };
			getGrid()[3] = new long[] { 0, 1, 3, 7, 11, 13, 0 };
			getGrid()[4] = new long[] { 0, 0, 4, 8, 12, 0, 0 };
			getGrid()[5] = new long[] { 0, 0, 0, 9, 0, 0, 0 };
			getGrid()[6] = new long[] { 0, 0, 0, 0, 0, 0, 0 };
			_current = new Position(1, 3);
		}
	}

	/**
	 * Follows the instructions to locate buttons.
	 */
	public String getButtons(List<String> input) {
		StringBuffer buffer = new StringBuffer();
		for (String line : input) {
			for (char direction : line.toCharArray()) {
				int x = getCurrent().getX();
				int y = getCurrent().getY();
				switch (direction) {
					case 'L':
						if (getGrid()[x - 1][y] != 0) {
							getCurrent().setX(x - 1);
						}
						break;
					case 'U':
						if (getGrid()[x][y - 1] != 0) {
							getCurrent().setY(y - 1);
						}
						break;
					case 'R':
						if (getGrid()[x + 1][y] != 0) {
							getCurrent().setX(x + 1);
						}
						break;
					case 'D':
						if (getGrid()[x][y + 1] != 0) {
							getCurrent().setY(y + 1);
						}
						break;
				}
			}
			buffer.append(Integer.toHexString((int) get(getCurrent())).toUpperCase());
		}
		return (buffer.toString());
	}
	
	/**
	 * Accessor for the current
	 */
	public Position getCurrent() {
		return _current;
	}	
}