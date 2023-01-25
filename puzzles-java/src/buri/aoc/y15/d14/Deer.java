package buri.aoc.y15.d14;

/**
 * @author Brian Uri!
 */
public class Deer {
	private int _speed;
	private int _flyPeriod;
	private int _restPeriod;
	private int _points;

	/**
	 * Constructor
	 */
	public Deer(String input) {
		String[] tokens = input.split(" ");
		_speed = Integer.valueOf(tokens[3]);
		_flyPeriod = Integer.valueOf(tokens[6]);
		_restPeriod = Integer.valueOf(tokens[13]);
		_points = 0;
	}

	/**
	 * Returns the distance traveled after the given number of seconds.
	 */
	public int getDistance(int seconds) {
		// Calculate the distance travelled in any complete cycles.
		int totalCycles = seconds / getTotalPeriod();
		int distance = (totalCycles * getSpeed() * getFlyPeriod());

		// Now take the remainder and calculate the leftover distance.
		int remainderSeconds = seconds % getTotalPeriod();
		distance += (getSpeed() * Math.min(remainderSeconds, getFlyPeriod()));
		return (distance);
	}

	/**
	 * Accessor for the speed
	 */
	private int getSpeed() {
		return _speed;
	}

	/**
	 * Accessor for the flyPeriod
	 */
	private int getFlyPeriod() {
		return _flyPeriod;
	}

	/**
	 * Accessor for the restPeriod
	 */
	private int getRestPeriod() {
		return _restPeriod;
	}

	/**
	 * Accessor for the total cycle of this deer's fly/rest.
	 */
	private int getTotalPeriod() {
		return (getFlyPeriod() + getRestPeriod());
	}

	/**
	 * Accessor for the points
	 */
	public int getPoints() {
		return _points;
	}

	/**
	 * Accessor for the points
	 */
	public void addPoint() {
		_points += 1;
	}
}
