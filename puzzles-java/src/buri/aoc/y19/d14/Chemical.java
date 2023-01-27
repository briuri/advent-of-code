package buri.aoc.y19.d14;

/**
 * Data model for a single entry in a reaction.
 *
 * @author Brian Uri!
 */
public class Chemical {
	private final String _name;
	private final int _amount;

	/**
	 * Constructor
	 */
	public Chemical(String input) {
		String[] tokens = input.split(" ");
		_name = tokens[1];
		_amount = Integer.parseInt(tokens[0]);
	}

	@Override
	public String toString() {
		return (getAmount() + " " + getName());
	}

	/**
	 * Accessor for the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the amount
	 */
	public int getAmount() {
		return _amount;
	}
}