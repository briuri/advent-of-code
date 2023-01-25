package buri.aoc.y17.d06;

import java.util.ArrayList;
import java.util.List;

/**
 * In this area, there are sixteen memory banks; each memory bank can hold any number of blocks.
 *
 * @author Brian Uri!
 */
public class MemoryBanks {
	List<Integer> _banks;

	/**
	 * Constructor
	 *
	 * @param banks the memory banks, with the number of blocks in each slot.
	 */
	public MemoryBanks(List<Integer> banks) {
		_banks = new ArrayList(banks);
	}

	/**
	 * Find the memory bank with the most blocks (ties won by the lowest-numbered memory bank). Removes all of the
	 * blocks from the selected bank, then moves to the next (by index) memory bank and inserts one of the blocks.
	 * It continues doing this until it runs out of blocks; if it reaches the last memory bank, it wraps around to
	 * the first one.
	 *
	 * Returns a string snapshot of block counts for comparison to earlier states.
	 */
	public String redistribute() {
		// Get the number of blocks in the fullest bank.
		Integer blocks = 0;
		for (Integer value : getBanks()) {
			blocks = Math.max(blocks, value);
		}
		// Ties won by the lowest numbered bank.
		int index = getBanks().indexOf(blocks);

		// Remove all blocks from the slot
		getBanks().set(index, 0);

		// Redistribute
		for (int i = 0; i < blocks; i++) {
			// Wrap around
			index = (index + 1 == getBanks().size()) ? 0 : index + 1;
			int oldBlocks = getBanks().get(index);
			getBanks().set(index, oldBlocks + 1);
		}
		return (getBanks().toString());
	}

	/**
	 * Accessor for the banks
	 */
	private List<Integer> getBanks() {
		return (_banks);
	}
}