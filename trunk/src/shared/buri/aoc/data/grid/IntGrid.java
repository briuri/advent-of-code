package buri.aoc.data.grid;

/**
 * Base class for grids storing ints.
 * 
 * @author Brian Uri!
 */
public class IntGrid extends AbstractGrid<Integer> {
	private Integer[][] _grid;

	/**
	 * Creates a new square grid with the specified width/length.
	 */
	public IntGrid(int size) {
		super(size);
		_grid = new Integer[getSize()][getSize()];
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
		setSize(grid.length);
		_grid = grid;
	}
}
