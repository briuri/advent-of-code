package buri.aoc.y2017;

/**
 * As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the
 * same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.
 *
 * So, the first few squares' values are chosen as follows:
 * 
 * Square 1 starts with the value 1.
 * Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
 * Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
 * Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
 * Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
 * 
 * Once a square is written, its value does not change. Therefore, the first few squares would receive the following
 * values:
 * 147 142 133 122  59
 * 304   5   4   2  57
 * 330  10   1   1  54
 * 351  11  23  25  26
 * 362 747 806 880 931
 * 
 * What is the first value written that is larger than your puzzle input?
 * 
 * @author Brian Uri!
 */
public class Day03Part2 {
	
	/* Set arbitrary limits to avoid overflowing Integer space */
	public static final int MAX_RING_VALUE = 633; // MAX_X_VALUE in Part 1, renamed to avoid confusion with coordinate space
	public static final int MAX_GRID_LENGTH = MAX_RING_VALUE + 2;
	public static final int CENTER_GRID_COORD = MAX_GRID_LENGTH / 2;
	
	/**
	 * Private to avoid instantiation.
	 */
	private Day03Part2() {}
	
	/**
	 * Builds the grid with intense brute force until the limitValue is found.
	 * 
	 * Spiral instructions:
	 * ring = 1: U 1, L 2, D 2, R 3
	 * ring = 3: U 3, L 4, D 4, R 5
	 * ring = 5: U 5, L 6, D 6, R 7
	 * 
	 * @param value the puzzle input
	 * @return the first value greater than the input value.
	 */
	public static int generateGrid(int value) {
		final int[][] grid = new int[MAX_GRID_LENGTH][MAX_GRID_LENGTH];

		// Start filling grid from center.
		int currentRing = 1;
		Point coordinates = new Point(CENTER_GRID_COORD, CENTER_GRID_COORD);
		grid[coordinates.getX()][coordinates.getY()] = 1;
		coordinates.moveRight();
		
		// Iterate around the spiral, filling in values.
		while (currentRing <= MAX_RING_VALUE) {
			// Fill values going up.
			for (int ups = 0; ups < currentRing; ups++) {
				int sum = getSurroundingValues(grid, coordinates);
				if (sum > value) {
					return (sum);
				}
				grid[coordinates.getX()][coordinates.getY()] = sum;
				coordinates.moveUp();
			}
			// Fill values going left.
			for (int lefts = 0; lefts < currentRing + 1; lefts++) {
				int sum = getSurroundingValues(grid, coordinates);
				if (sum > value) {
					return (sum);
				}
				grid[coordinates.getX()][coordinates.getY()] = sum;
				coordinates.moveLeft();
			}
			// Fill values going down.
			for (int downs = 0; downs < currentRing + 1; downs++) {
				int sum = getSurroundingValues(grid, coordinates);
				if (sum > value) {
					return (sum);
				}
				grid[coordinates.getX()][coordinates.getY()] = sum;
				coordinates.moveDown();
			}
			// Fill values going right.
			for (int rights = 0; rights < currentRing + 2; rights++) {
				int sum = getSurroundingValues(grid, coordinates);
				if (sum > value) {
					return (sum);
				}
				grid[coordinates.getX()][coordinates.getY()] = sum;
				coordinates.moveRight();
			}			
			currentRing = currentRing + 2;
		}
		throw new IllegalArgumentException("Limit value never found.");
	}

	/**
	 * Prints the grid for visual debugging.
	 */
	public static void printGrid(int[][] grid, Point coordinates) {
		System.out.println(coordinates);
		for (int i = 0; i < grid.length; i++) {
			System.out.print("\t");
			for (int j = 0; j < grid[i].length; j++) {
				String value = String.valueOf(grid[i][j]);
				System.out.print(String.format("%1$-5s", value));
			}
			System.out.println();
		}
	}
	
	/**
	 * Gets the sum of all values in adjacent and diagonal positions from the grid.
	 * 
	 * @param grid the grid to inspect
	 * @param coordinates the center point for this calculation
	 * @return all surrounding values summed
	 */
	private static int getSurroundingValues(int[][] grid, Point coordinates) {
		int sum = 0;
		if (coordinates.getY() > 0) {
			// Upper Left
			if (coordinates.getX() > 0) {
				sum += grid[coordinates.getX() - 1][coordinates.getY() - 1];
			}
			// Left
			sum += grid[coordinates.getX()][coordinates.getY() - 1];
			// Lower Left
			if (coordinates.getX() < grid.length - 1) {
				sum += grid[coordinates.getX() + 1][coordinates.getY() - 1];
			}
		}
		if (coordinates.getX() > 0) {
			// Up
			sum += grid[coordinates.getX() - 1][coordinates.getY()];
		}
		if (coordinates.getX() < grid.length - 1) {
			// Down
			sum += grid[coordinates.getX() + 1][coordinates.getY()];
		}
		if (coordinates.getY() < grid.length - 1) {
			// Upper Right
			if (coordinates.getX() > 0) {
				sum += grid[coordinates.getX() - 1][coordinates.getY() + 1];
			}
			// Right
			sum += grid[coordinates.getX()][coordinates.getY() + 1];
			// Lower Right
			if (coordinates.getX() < grid.length - 1) {
				sum += grid[coordinates.getX() + 1][coordinates.getY() + 1];
			}
		}
		return (sum);
	}
	
	/**
	 * Mutable Integer-based ordered pair class, with commands to alter it by moving up, down, left, and right.
	 * 
	 * Based on Java's array indexing:
	 * 		(0,0) is the upper left corner of a grid.
	 * 		(x,0) is lower left corner of a grid.
	 * 		(0,y) is upper right corner of grid.
	 * 		(x,y) is lower right corner of grid.
	 * 
	 * @author Brian Uri!
	 */
	private static class Point {
		private int _x;
		private int _y;
		
		/**
		 * Constructor
		 * 
		 * @param x the starting X value
		 * @param y the starting Y value
		 */
		public Point(int x, int y) {
			_x = x;
			_y = y;
		}

		/**
		 * Moves one slot to the left.
		 */
		public void moveLeft() {
			_y = _y - 1;
		}
		
		/**
		 * Moves one slot to the right.
		 */
		public void moveRight() {
			_y = _y + 1;
		}
		
		/**
		 * Moves one slot down.
		 */
		public void moveDown() {
			_x = _x + 1;
		}
		
		/**
		 * Moves one slot up.
		 */
		public void moveUp() {
			_x = _x - 1;
		}
		
		@Override
		public String toString() {
			return ("(" + getX() + "," + getY() + ")");
		}
		
		/**
		 * Accessor for X
		 */
		public int getX() {
			return _x;
		}

		/**
		 * Accessor for Y
		 */
		public int getY() {
			return _y;
		}		
	}
}
