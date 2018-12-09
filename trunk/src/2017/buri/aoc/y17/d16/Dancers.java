package buri.aoc.y17.d16;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class for the positions of the dancers
 * 
 * @author Brian Uri!
 */
public class Dancers {
	private List<Character> _positions;
	
	/**
	 * Constructor
	 */
	public Dancers(int numDancers) {
		_positions = new ArrayList<>(numDancers);
		for (char dancer = 'a'; dancer <= 'z'; dancer++) {
		    _positions.add(dancer);
		    if (getPositions().size() == numDancers) {
		    	break;
		    }
		}
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
		List<Character> subList = new ArrayList<>(getPositions().subList(getPositions().size() - num,
			getPositions().size()));
		getPositions().removeAll(subList);
		getPositions().addAll(0, subList);
	}
	
	/**
	 * Makes the programs at these positions swap places.
	 */
	private void exchange(int source, int target) {
		Character character = getPositions().get(source);
		getPositions().set(source, getPositions().get(target));
		getPositions().set(target, character);
	}
	
	/**
	 * Makes the programs with these names swap places.
	 */	
	private void partner(Character source, Character target) {
		exchange(getPositions().indexOf(source), getPositions().indexOf(target));
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < getPositions().size(); i++) {
			buffer.append(getPositions().get(i));
		}
		return (buffer.toString());
	}
	
	/**
	 * Accessor for the positions
	 */
	private List<Character> getPositions() {
		return _positions;
	}
}
