package buri.aoc.data.grid;

import buri.aoc.data.tuple.Pair;

/**
 * Base class for grids storing characters.
 * 
 * @author Brian Uri!
 */
public class CharGrid extends AbstractGrid<Character> {
	private Character[][] _grid;

	/**
	 * Creates a new grid with the specified width/length.
	 */
	public CharGrid(Pair size) {
		super(size);
		_grid = new Character[getWidth()][getHeight()];
	}

	@Override
	public Character get(int x, int y) {
		Character value = super.get(x, y);
		return (value == null ? ' ' : value);
	}

	@Override
	protected Character[][] getGrid() {
		return (_grid);
	}

	@Override
	protected void setGrid(Character[][] grid) {
		setSize(new Pair(grid.length, grid[0].length));
		_grid = grid;
	}
}