package buri.aoc.y2017;

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
 * They always take the shortest path: the Manhattan Distance [MD] between the location of the data and square 1.
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
	 * 			x^2 < value < ((x+2)^2 + 1) where x = (1, 3, 5, ...)
	 * The number of values in each ring is:
	 * 			numValuesInRing = (x*8)
	 * The highest value in the ring is always the next odd perfect square:
	 * 			highestValue = (x+2)^2		highestValueMD = (x+1)
	 * The range of MD values in the ring is:
	 * 			minMD = (x+1)/2             maxMD = (x+1)
	 * 
	 * With this knowledge, we can treat a ring as a reversed 1-D array:
	 * 			[9 8 7 6 5 4 3 2]
	 * 			x = 1, numValuesInRing = 8
	 *          highestValue = 9, highestValueMD = 2
	 *          minMD = 1, maxMD = 2 
	 * We can calculate the MDs starting from the end at maxMD, decrementing to minMD, then incrementing to maxMD over 
	 * and over until we reach the beginning of the array:
	 * 			[9 8 7 6 5 4 3 2]
	 * 			[2 1 2 1 2 1 2 1]
	 * 
	 * So to solve for any arbitrary value, iteratively try odd x values until we get to the right ring. Then do the 
	 * above array calculations. 
	 */
	
	/* Set arbitrary limits to avoid overflowing Integer space */
	public static final int MAX_X_VALUE = 633;
	public static final int MAX_VALUE = 400689;
	
	/**
	 * Private to avoid instantiation.
	 */
	private Day03() {}
	
	/**
	 * Calculates the Manhattan Distance for a particular value, using the algorithm described above.
	 * 
	 * @param value the value in grid memory
	 * @return the MD for this value
	 */
	public static int getManhattanDistance(int value) {
		if (value == 1) {
			return (0);
		}
		
		final int x = getXForValue(value);
		final int numValuesInRing = x * 8;
		final int highestValue = getHighestValueForX(x);
		final int minMD = (x + 1) / 2;
		final int maxMD = (x + 1);
		
		final int[] ring = new int[numValuesInRing];
		final int[] distances = new int[numValuesInRing];
		// Populate the ring with values from highest to lowest.
		for (int i = 0; i < ring.length; i++) {
			ring[i] = highestValue - i;
		}
		
		// Populate the distances and incrementing/decrementing.
		int nextMD = maxMD;
		boolean isIncrementing = false;
		for (int i = 0; i < ring.length; i++) {
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
		for (int i = 0; i < ring.length; i++) {
			if (ring[i] == value) {
				return (distances[i]);
			}
		}
		throw new RuntimeException("Could not find value " + value + " in selected ring.");
	}
	
	/**
	 * Bound-checks a value to avoid Integer overflows. (bounded by 1 and (MAX_X_VALUE+2)^2)
	 * 
	 * @param value the value in grid memory
	 */
	protected static void assertValidValue(int value) {
		if (value < 1 || value > MAX_VALUE) {
			throw new IllegalArgumentException("Value must be between 0 and " + MAX_VALUE);
		}
	}
		
	/**
	 * @param x the x ring value
	 * @return (x+2)^2
	 */
	protected static int getHighestValueForX(int x) {
		return ((int) Math.pow(x + 2, 2.0));
	}
	
	/**
	 * Calculates the x ring value for some value. Could use the square root of the value to get in the ballpark, but
	 * it's more readable to just check iteratively.
	 * 
	 * @param value the value in grid memory
	 */
	protected static int getXForValue(int value) {
		assertValidValue(value);
		for (int possibleX = 1; possibleX < MAX_X_VALUE; possibleX = possibleX + 2) {
			if (value <= getHighestValueForX(possibleX)) {
				return (possibleX);
			}
		}
		throw new RuntimeException("Value " + value + " had no possible x.");
	}
}
