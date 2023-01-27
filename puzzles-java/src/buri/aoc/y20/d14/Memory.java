package buri.aoc.y20.d14;

import buri.aoc.common.Part;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class representing the memory.
 *
 * @author Brian Uri!
 */
public class Memory {
	private final Map<Long, Long> _memory;
	private String _mask;

	/**
	 * Constructor
	 */
	public Memory() {
		_memory = new HashMap<>();
	}

	/**
	 * Returns the sum of all saved values.
	 */
	public long getSum() {
		long sum = 0;
		for (Long value : getMemory().values()) {
			sum += value;
		}
		return (sum);
	}

	/**
	 * Sets the value at address, depending on the Part rules.
	 *
	 * In Part One, the value is masked.
	 * In Part Two, the address is masked and expanded using the floating bits (X).
	 */
	public void set(Part part, long address, long value) {
		if (part == Part.ONE) {
			long maskedValue = new BigInteger(mask(part, value), 2).longValue();
			getMemory().put(address, maskedValue);
		}
		else {
			for (String floatingAddress : getPermutations(mask(part, address))) {
				long maskedAddress = new BigInteger(floatingAddress, 2).longValue();
				getMemory().put(maskedAddress, value);
			}
		}
	}

	/**
	 * Masks a value.
	 *
	 * Part One: [0,1] overwrites bit. [X] is ignored.
	 * Part Two: [1,X] overwrites bit. [0] is ignored.
	 */
	protected String mask(Part part, Long value) {
		StringBuilder builder = new StringBuilder(Long.toBinaryString(value));
		while (builder.length() < getMask().length()) {
			builder.insert(0, "0");
		}

		char ignoreBit = (part == Part.ONE ? 'X' : '0');
		for (int i = 0; i < getMask().length(); i++) {
			char maskBit = getMask().charAt(i);
			if (maskBit != ignoreBit) {
				builder.setCharAt(i, maskBit);
			}
		}
		return (builder.toString());

	}

	/**
	 * Converts 1 masked address into many unmasked addresses.
	 */
	protected static List<String> getPermutations(String maskedAddress) {
		List<String> list = new ArrayList<>();
		list.add(maskedAddress);

		while (true) {
			List<String> next = new ArrayList<>();
			for (String address : list) {
				// Each time X is found, add 2 new addresses to the next list.
				int floatingIndex = address.indexOf("X");
				if (floatingIndex != -1) {
					StringBuilder addressCopy = new StringBuilder(address);
					addressCopy.setCharAt(floatingIndex, '0');
					next.add(addressCopy.toString());
					addressCopy.setCharAt(floatingIndex, '1');
					next.add(addressCopy.toString());
				}
			}
			// Quit when no Xs are left.
			if (next.isEmpty()) {
				break;
			}
			// Otherwise, check again on the new list.
			list = next;
		}
		return (list);
	}

	/**
	 * Accessor for the memory
	 */
	private Map<Long, Long> getMemory() {
		return _memory;
	}

	/**
	 * Accessor for the mask
	 */
	public String getMask() {
		return _mask;
	}

	/**
	 * Accessor for the mask
	 */
	public void setMask(String mask) {
		_mask = mask;
	}
}