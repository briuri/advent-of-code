package buri.aoc.data.grid;

/**
 * Base class for grids storing characters.
 * 
 * @author Brian Uri!
 */
public class CharGrid extends AbstractGrid<Character> {
	private Character[][] _grid;

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public CharGrid(int size) {
		super(size);
		_grid = new Character[getSize()][getSize()];
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
		setSize(grid.length);
		_grid = grid;
	}
}