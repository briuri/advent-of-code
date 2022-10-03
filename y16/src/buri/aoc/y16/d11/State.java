package buri.aoc.y16.d11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.data.Permutations;

/**
 * Data model for a single facility state.
 * 
 * 1|11|12|12|33|33
 * 
 * Elevator on first floor.
 * Each pair after that shows the floor of a generator, and the floor of its matching chip.
 * Pairs are given an ID based on their position: 0[11], 1[12], 2[12], 3[33], 4[33]
 * Pairs are sorted to reduce the number of possible unique states.
 * 
 * @author Brian Uri!
 */
public class State {

	private String _state;
	private int _pairs;
	private int _currentFloor;

	/**
	 * Constructor
	 */
	public State(String state) {
		_pairs = (state.length() - 1) / 3;

		// Sort pairs to reduce the number of unique states reached.
		List<String> pairs = new ArrayList<>();
		for (int i = 0; i < getPairs(); i++) {
			pairs.add(state.substring(toGeneratorIndex(i), toChipIndex(i) + 1));
		}
		Collections.sort(pairs);

		StringBuilder builder = new StringBuilder();
		builder.append(state.charAt(0));
		for (String pair : pairs) {
			builder.append('|').append(pair);
		}
		_state = builder.toString();
		_currentFloor = Character.getNumericValue(getState().charAt(0));
	}

	/**
	 * Determines what other states we can reach from where we are.
	 */
	public List<State> getNextStates() {
		List<Integer> generatorsHere = getGeneratorsOnFloor(getCurrentFloor());
		List<Integer> chipsHere = getChipsOnFloor(getCurrentFloor());

		List<State> states = new ArrayList<>();
		// Elevator can go up or down 1 floor.
		for (Integer nextFloor : getNextElevatorFloors()) {
			Character nextFloorChar = Character.forDigit(nextFloor, 10);
			List<Integer> generatorsNext = getGeneratorsOnFloor(nextFloor);
			List<Integer> chipsNext = getChipsOnFloor(nextFloor);

			// Elevator can move matching pair (generator+chip) at any time.
			for (Integer generatorId : generatorsHere) {
				if (chipsHere.contains(generatorId)) {
					StringBuilder builder = new StringBuilder(getState());
					builder.setCharAt(0, nextFloorChar);
					builder.setCharAt(toGeneratorIndex(generatorId), nextFloorChar);
					builder.setCharAt(toChipIndex(generatorId), nextFloorChar);
					states.add(new State(builder.toString()));
				}
			}

			// Elevator can move 2 generators if they won't fry things on next floor.
			for (List<Integer> generatorIds : Permutations.getPairPermutations(generatorsHere)) {
				if (areGeneratorsAllowedNear(generatorIds, generatorsNext, chipsNext)) {
					StringBuilder builder = new StringBuilder(getState());
					builder.setCharAt(0, nextFloorChar);
					builder.setCharAt(toGeneratorIndex(generatorIds.get(0)), nextFloorChar);
					builder.setCharAt(toGeneratorIndex(generatorIds.get(1)), nextFloorChar);
					states.add(new State(builder.toString()));
				}
			}

			// Elevator can move 2 chips if they won't fry on next floor.
			for (List<Integer> chipIds : Permutations.getPairPermutations(chipsHere)) {
				if (areChipsAllowedNear(chipIds, generatorsNext, chipsNext)) {
					StringBuilder builder = new StringBuilder(getState());
					builder.setCharAt(0, nextFloorChar);
					builder.setCharAt(toChipIndex(chipIds.get(0)), nextFloorChar);
					builder.setCharAt(toChipIndex(chipIds.get(1)), nextFloorChar);
					states.add(new State(builder.toString()));
				}
			}

			// Elevator can move 1 generator if it won't fry things on next floor.
			for (Integer generatorId : generatorsHere) {
				if (isGeneratorAllowedNear(generatorId, generatorsNext, chipsNext)) {
					StringBuilder builder = new StringBuilder(getState());
					builder.setCharAt(0, nextFloorChar);
					builder.setCharAt(toGeneratorIndex(generatorId), nextFloorChar);
					states.add(new State(builder.toString()));
				}
			}

			// Elevator can move 1 chip if it won't fry on next floor.
			for (Integer chipId : chipsHere) {
				if (isChipAllowedNear(chipId, generatorsNext, chipsNext)) {
					StringBuilder builder = new StringBuilder(getState());
					builder.setCharAt(0, nextFloorChar);
					builder.setCharAt(toChipIndex(chipId), nextFloorChar);
					states.add(new State(builder.toString()));
				}
			}
		}

		return (states);
	}

	/**
	 * Returns the valid elevator floors, based on this state's current floor.
	 */
	private List<Integer> getNextElevatorFloors() {
		List<Integer> floors = new ArrayList<>();
		if (getCurrentFloor() <= 3) {
			floors.add(getCurrentFloor() + 1);
		}
		if (getCurrentFloor() >= 2) {
			floors.add(getCurrentFloor() - 1);
		}
		return (floors);
	}

	/**
	 * Returns the IDs of all generators on some floor.
	 */
	private List<Integer> getGeneratorsOnFloor(int floor) {
		List<Integer> generators = new ArrayList<>();
		for (int i = 0; i < getPairs(); i++) {
			if (Character.getNumericValue(getState().charAt(toGeneratorIndex(i))) == floor) {
				generators.add(i);
			}
		}
		return (generators);
	}

	/**
	 * Returns the IDs of all microchips on some floor.
	 */
	private List<Integer> getChipsOnFloor(int floor) {
		List<Integer> microchips = new ArrayList<>();
		for (int i = 0; i < getPairs(); i++) {
			if (Character.getNumericValue(getState().charAt(toChipIndex(i))) == floor) {
				microchips.add(i);
			}
		}
		return (microchips);
	}

	/**
	 * Checks if a generator is allowed near chips.
	 */
	private static boolean isGeneratorAllowedNear(int generatorId, List<Integer> generatorsNext,
		List<Integer> chipsNext) {
		List<Integer> generatorIds = new ArrayList<>();
		generatorIds.add(generatorId);
		return (areGeneratorsAllowedNear(generatorIds, generatorsNext, chipsNext));
	}

	/**
	 * Checks if a chip is allowed near generators.
	 */
	private static boolean isChipAllowedNear(int chipId, List<Integer> generatorsNext, List<Integer> chipsNext) {
		List<Integer> chipIds = new ArrayList<>();
		chipIds.add(chipId);
		return (areChipsAllowedNear(chipIds, generatorsNext, chipsNext));
	}

	/**
	 * Checks if generators are allowed near chips.
	 * 
	 * Returns true when each chip has a matching generator to protect it. Returns false otherwise.
	 */
	private static boolean areGeneratorsAllowedNear(List<Integer> generatorIds, List<Integer> generatorsNext,
		List<Integer> chipsNext) {
		boolean allowed = true;
		for (Integer chipId : chipsNext) {
			allowed = allowed && (generatorIds.contains(chipId) || generatorsNext.contains(chipId));
		}
		return (allowed);
	}

	/**
	 * Checks if chips are allowed near generators.
	 * 
	 * Returns true when each new chip has a matching generator to protect it, or there are no generators. Returns false
	 * otherwise.
	 */
	private static boolean areChipsAllowedNear(List<Integer> chipIds, List<Integer> generatorsNext,
		List<Integer> chipsNext) {
		boolean allowed = true;
		for (Integer id : chipIds) {
			allowed = allowed && generatorsNext.contains(id);
		}
		allowed = allowed || generatorsNext.isEmpty();
		return (allowed);
	}

	/**
	 * Converts a pair ID into its location in the state string.
	 */
	private static int toGeneratorIndex(int id) {
		return (2 + (id * 3));
	}

	/**
	 * Converts a pair ID into its location in the state string.
	 */
	private static int toChipIndex(int id) {
		return (3 + (id * 3));
	}

	@Override
	public String toString() {
		return (getState());
	}

	@Override
	public boolean equals(Object obj) {
		State s = (State) obj;
		return (getState().equals(s.getState()));
	}

	@Override
	public int hashCode() {
		return (getState().hashCode());
	}

	/**
	 * Accessor for the state
	 */
	private String getState() {
		return (_state);
	}

	/**
	 * Accessor for the total number of generator/chip pairs
	 */
	public int getPairs() {
		return _pairs;
	}

	/**
	 * Accessor for the current floor where the elevator is.
	 */
	private int getCurrentFloor() {
		return (_currentFloor);
	}
}