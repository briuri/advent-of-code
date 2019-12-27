package buri.aoc.y17.d16;

/**
 * Data class for the positions of the dancers
 * 
 * @author Brian Uri!
 */
public class Dancers {
	private String _positions;

	/**
	 * Constructor
	 */
	public Dancers(int numDancers) {
		StringBuffer buffer = new StringBuffer();
		for (char dancer = 'a'; dancer <= 'z'; dancer++) {
			buffer.append(dancer);
			if (buffer.length() == numDancers) {
				break;
			}
		}
		setPositions(buffer.toString());
	}

	/**
	 * Performs a single dance move:
	 * Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise.
	 * Exchange, written xA/B, makes the programs at positions A and B swap places.
	 * Partner, written pA/B, makes the programs named A and B swap places.
	 */
	public void perform(String move) {
		String parameters = move.substring(1);
		String[] tokens = parameters.split("/");
		switch (move.charAt(0)) {
			case 's':
				spin(Integer.valueOf(tokens[0]));
				break;
			case 'x':
				exchange(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
				break;
			case 'p':
				partner(tokens[0].charAt(0), tokens[1].charAt(0));
		}
	}

	/**
	 * Make num programs move from end to front, but maintain their order otherwise.
	 */
	private void spin(int num) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getPositions().substring(getPositions().length() - num));
		buffer.append(getPositions().substring(0, getPositions().length() - num));
		setPositions(buffer.toString());
	}

	/**
	 * Makes the programs at these positions swap places.
	 */
	private void exchange(int source, int target) {
		char sourceChar = getPositions().charAt(source);
		char targetChar = getPositions().charAt(target);
		StringBuffer buffer = new StringBuffer(getPositions());
		buffer.setCharAt(source, targetChar);
		buffer.setCharAt(target, sourceChar);
		setPositions(buffer.toString());
	}

	/**
	 * Makes the programs with these names swap places.
	 */
	private void partner(Character source, Character target) {
		exchange(getPositions().indexOf(source), getPositions().indexOf(target));
	}

	@Override
	public String toString() {
		return (getPositions());
	}

	/**
	 * Accessor for the positions
	 */
	private String getPositions() {
		return _positions;
	}

	/**
	 * Accessor for the positions
	 */
	private void setPositions(String positions) {
		_positions = positions;
	}

}
