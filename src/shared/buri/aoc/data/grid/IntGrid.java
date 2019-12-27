package buri.aoc.data.grid;

import buri.aoc.data.Pair;

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
