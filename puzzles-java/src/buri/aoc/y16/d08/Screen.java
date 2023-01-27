package buri.aoc.y16.d08;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author Brian Uri!
 */
public class Screen {
	private final int[][] _grid;

	/**
	 * Constructor
	 */
	public Screen() {
		_grid = new int[50][6];
		for (int y = 0; y < getGrid()[0].length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				getGrid()[x][y] = 0;
			}
		}
	}

	/**
	 * Runs the commands and returns the number of lit pixels.
	 */
	public int run(List<String> input) {
		for (String line : input) {
			if (line.startsWith("rect")) {
				runRect(line);
			}
			if (line.startsWith("rotate row")) {
				runRotateRow(line);
			}
			if (line.startsWith("rotate column")) {
				runRotateColumn(line);
			}
		}
		int litPixels = 0;
		for (int y = 0; y < getGrid()[0].length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				litPixels += getGrid()[x][y];
			}
		}
		return (litPixels);
	}

	/**
	 * rect AxB
	 *
	 * Turns on all the pixels in a rectangle at the top-left of the screen which is A wide and B tall.
	 */
	private void runRect(String line) {
		String[] dimensions = line.split(" ")[1].split("x");
		int width = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getGrid()[x][y] = 1;
			}
		}
	}

	/**
	 * rotate row y=A by B
	 *
	 * Shifts all the pixels in row A (0 is the top row) right by B pixels. Pixels that would fall off the right end
	 * appear at the left end of the row.
	 */
	private void runRotateRow(String line) {
		String[] parameters = line.split("=")[1].split(" by ");
		int row = Integer.parseInt(parameters[0]);
		int pixels = Integer.parseInt(parameters[1]);
		Deque<Integer> circle = new ArrayDeque<>();
		for (int x = 0; x < getGrid().length; x++) {
			circle.add(getGrid()[x][row]);
		}
		for (int i = 0; i < pixels; i++) {
			circle.addFirst(circle.removeLast());
		}
		for (int x = 0; x < getGrid().length; x++) {
			getGrid()[x][row] = circle.removeFirst();
		}
	}

	/**
	 * rotate column x=A by B
	 * shifts all the pixels in column A (0 is the left column) down by B pixels. Pixels that would fall off the
	 * bottom appear at the top of the column.
	 */
	private void runRotateColumn(String line) {
		String[] parameters = line.split("=")[1].split(" by ");
		int column = Integer.parseInt(parameters[0]);
		int pixels = Integer.parseInt(parameters[1]);
		Deque<Integer> circle = new ArrayDeque<>();
		for (int y = 0; y < getGrid()[0].length; y++) {
			circle.add(getGrid()[column][y]);
		}
		for (int i = 0; i < pixels; i++) {
			circle.addFirst(circle.removeLast());
		}
		for (int y = 0; y < getGrid()[0].length; y++) {
			getGrid()[column][y] = circle.removeFirst();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < getGrid()[0].length; y++) {
			for (int x = 0; x < getGrid().length; x++) {
				builder.append(toOutput(getGrid()[x][y]));
			}
			builder.append("\n");
		}
		builder.append("\n");
		return (builder.toString());
	}

	/**
	 * Converts ints into displayable characters.
	 */
	private char toOutput(int value) {
		return (value == 0 ? ' ' : '■');
	}

	/**
	 * Accessor for the grid
	 */
	private int[][] getGrid() {
		return _grid;
	}
}