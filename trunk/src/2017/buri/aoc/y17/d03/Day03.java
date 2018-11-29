package buri.aoc.y17.d03;

/**
 * You come across an experimental new kind of memory stored on an infinite two-dimensional grid.
 * 
 * Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while
 * spiraling outward. For example, the first few squares are allocated like this:
 * 
 * 17 16 15 14 13
 * 18  5  4  3 12
 * 19  6  1  2 11
 * 20  7  8  9 10
 * 21 22 23---> ...
 * 
 * While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the
 * location of the only access port for this memory system) by programs that can only move up, down, left, or right.
 * They always take the shortest path: the Manhattan Distance (MD) between the location of the data and square 1.
 * 
 * Part Two:
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
public class Day03 {

	/**
	 * Scratch paper observations:
	 * 
	 * Ring                                  MDs
	 *  5   4   3                            2 1 2              
	 *  6  (1)  2                            1   1		
	 *  7   8  [9]                           2 1 2
	 * 
	 *  17   16   15   14   13               4 3 2 3 4
	 *  18                  12               3       3
	 *  19                  11               2       2
	 *  20             (9)  10               3       3
	 *  21   22   23   24  [25]              4 3 2 3 4
	 * 
	 *  37   36   35   34   33   32   31     6 5 4 3 4 5 6
	 *  38                            30     5           5
	 *  39                            29     4           4
	 *  40                            28     3           3
	 *  41                            27     4           4
	 *  42                      (25)  26     5           5
	 *  43   44   45   46   47   48  [49]    6 5 4 3 4 5 6
	 * 
	 * The values in each ring are bounded by perfect squares of odd numbers:
	 * 			ring^2 < value < ((ring+2)^2 + 1) where ring = (1, 3, 5, ...)
	 * The number of values in each ring is:
	 * 			numValuesInRing = (ring*8)
	 * The highest value in the ring is always the next odd perfect square:
	 * 			highestValue = (ring+2)^2, highestValueMD = (ring+1)
	 * The range of MD values in the ring is:
	 * 			minMD = (ring+1)/2, maxMD = (ring+1)
	 * 
	 * With this knowledge, we can treat a ring as a reversed 1-D array:
	 * 			[9 8 7 6 5 4 3 2]
	 * 			ring = 1, numValuesInRing = 8
	 *          highestValue = 9, highestValueMD = 2
	 *          minMD = 1, maxMD = 2 
	 * We can calculate the MDs starting from the end at maxMD, decrementing to minMD, then incrementing to maxMD over 
	 * and over until we reach the beginning of the array:
	 * 			[9 8 7 6 5 4 3 2]
	 * 			[2 1 2 1 2 1 2 1]
	 * 
	 * So to solve for any arbitrary value, iteratively try odd ring values until we get to the right ring. Then do 
	 * the above array calculations. 
	 */

	/* Set arbitrary limits to avoid overflowing Integer space */
	public static final int MAX_RING_VALUE = 633;
	public static final int MAX_VALUE = 400689;
	public static final int MAX_GRID_LENGTH = MAX_RING_VALUE + 2;
	public static final int CENTER_GRID_COORD = MAX_GRID_LENGTH / 2;
	
	/**
	 * Calculates the Manhattan Distance for a particular value, using the algorithm described above.
	 */
	public static int getManhattanDistance(int value) {
		if (value == 1) {
			return (0);
		}

		final int ring = getRingForValue(value);
		final int numValuesInRing = ring * 8;
		final int highestValue = getHighestValueForRing(ring);
		final int minMD = (ring + 1) / 2;
		final int maxMD = (ring + 1);

		final int[] ringValues = new int[numValuesInRing];
		final int[] distances = new int[numValuesInRing];
		// Populate the ring with values from highest to lowest.
		for (int i = 0; i < ringValues.length; i++) {
			ringValues[i] = highestValue - i;
		}

		// Populate the distances by incrementing/decrementing.
		int nextMD = maxMD;
		boolean isIncrementing = false;
		for (int i = 0; i < ringValues.length; i++) {
			distances[i] = nextMD;
			if (nextMD == maxMD) {
				isIncrementing = false;
			}
			else if (nextMD == minMD) {
				isIncrementing = true;
			}
			nextMD = (isIncrementing ? nextMD + 1 : nextMD - 1);
		}

		// Now look up distance for specific value.
		for (int i = 0; i < ringValues.length; i++) {
			if (ringValues[i] == value) {
				return (distances[i]);
			}
		}
		throw new RuntimeException("Could not find value " + value + " in selected ring " + ring + ".");
	}

	/**
	 * Builds the grid with intense brute force until the limitValue is found.
	 * 
	 * Spiral instructions:
	 * ring = 1: U 1, L   2, D   2, R   3
	 * ring = 3: U 3, L   4, D   4, R   5
	 * ring = 5: U 5, L   6, D   6, R   7
	 * ring = a: U a, L a+1, D a+1, R a+2
	 */
	public static int populateGrid(int value) {
		SpiralGrid grid = new SpiralGrid(MAX_GRID_LENGTH);

		// Start filling grid from center.
		int currentRing = 1;
		GridPosition position = new GridPosition(CENTER_GRID_COORD, CENTER_GRID_COORD);
		grid.set(position, 1);
		position.moveRight();
		
		// Iterate around the spiral, filling in values.
		while (currentRing <= MAX_RING_VALUE) {
			// Fill values going up.
			for (int ups = 0; ups < currentRing; ups++) {
				int sum = grid.getSurroundingValues(position);
				if (sum > value) {
					return (sum);
				}
				grid.set(position, sum);
				position.moveUp();
			}
			// Fill values going left.
			for (int lefts = 0; lefts < currentRing + 1; lefts++) {
				int sum = grid.getSurroundingValues(position);
				if (sum > value) {
					return (sum);
				}
				grid.set(position, sum);
				position.moveLeft();
			}
			// Fill values going down.
			for (int downs = 0; downs < currentRing + 1; downs++) {
				int sum = grid.getSurroundingValues(position);
				if (sum > value) {
					return (sum);
				}
				grid.set(position, sum);
				position.moveDown();
			}
			// Fill values going right.
			for (int rights = 0; rights < currentRing + 2; rights++) {
				int sum = grid.getSurroundingValues(position);
				if (sum > value) {
					return (sum);
				}
				grid.set(position, sum);
				position.moveRight();
			}			
			currentRing = currentRing + 2;
		}
		throw new IllegalArgumentException("Limit value never found.");
	}
	
	/**
	 * Bound-checks a value to avoid Integer overflows. (bounded by 1 and (MAX_RING_VALUE+2)^2)
	 */
	private static void assertValidValue(int value) {
		if (value < 1 || value > MAX_VALUE) {
			throw new IllegalArgumentException("Value must be between 0 and " + MAX_VALUE);
		}
	}

	/**
	 * Returns the highest possible value in a ring.
	 */
	private static int getHighestValueForRing(int ring) {
		return ((int) Math.pow(ring + 2, 2.0));
	}

	/**
	 * Calculates the ring for some value.
	 */
	private static int getRingForValue(int value) {
		assertValidValue(value);
		for (int possibleRing = 1; possibleRing < MAX_RING_VALUE; possibleRing = possibleRing + 2) {
			if (value <= getHighestValueForRing(possibleRing)) {
				return (possibleRing);
			}
		}
		throw new RuntimeException("Value " + value + " had no possible ring.");
	}
}
