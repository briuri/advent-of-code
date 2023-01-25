package buri.aoc.y19.d14;

/**
 * Data model for a single entry in a reaction.
 *
 * @author Brian Uri!
 */
public class Chemical {
	private String _name;
	private int _amount;

	/**
	 * Constructor
	 */
	public Chemical(String input) {
		String[] tokens = input.split(" ");
		_name = tokens[1];
		_amount = Integer.valueOf(tokens[0]);
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