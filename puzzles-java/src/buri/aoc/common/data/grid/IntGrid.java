package buri.aoc.common.data.grid;

import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Quad;
import buri.aoc.common.PuzzleMath;

import java.util.Map;
import java.util.Set;

/**
 * Base class for grids storing ints.
 *
 * @author Brian Uri!
 */
public class IntGrid extends AbstractGrid<Integer> {
	private Integer[][] _grid;

	/**
	 * Creates a new grid with the specified width/length.
	 */
	public IntGrid(Pair size) {
		super(size);
		_grid = new Integer[getWidth()][getHeight()];
	}

	/**
	 * Renders a sparse set of points as a visual grid.
	 */
	public static <T> void showAsGrid(Map<Pair<Integer>, T> grid) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Pair<Integer> point : grid.keySet()) {
			minX = Math.min(minX, point.getX());
			maxX = Math.max(maxX, point.getX());
			minY = Math.min(minY, point.getY());
			maxY = Math.max(maxY, point.getY());
		}
		StringBuilder builder = new StringBuilder();
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x < maxX; x++) {
				T value = grid.get(new Pair<>(x, y));
				builder.append(value == null ? ' ' : value);
			}
			builder.append("\n");
		}
		builder.append("\n");
		System.out.println(builder);
	}

	/**
	 * Renders a sparse set of points as a visual grid.
	 */
	public static void showAsGrid(Set<Pair<Integer>> grid) {
		Quad<Integer> bounds = PuzzleMath.getBounds(grid);
		StringBuilder builder = new StringBuilder();
		for (int y = bounds.getZ(); y <= bounds.getT(); y++) {
			for (int x = bounds.getX(); x <= bounds.getY(); x++) {
				Pair<Integer> point = new Pair<>(x, y);
				if (grid.contains(point)) {
					builder.append('■');
				}
				else {
					builder.append(' ');
				}
			}
			builder.append("\n");
		}
		builder.append("\n");
		System.out.println(builder);
	}

	@Override
	public Integer get(int x, int y) {
		Integer value = super.get(x, y);
		return (value == null ? 0 : value);
	}

	@Override
	protected String toOutput(Integer value) {
		return (String.valueOf(value) + "\t");
	}

	@Override
	protected Integer[][] getGrid() {
		return (_grid);
	}

	@Override
	protected void setGrid(Integer[][] grid) {
		setSize(new Pair(grid.length, grid[0].length));
		_grid = grid;
	}
}