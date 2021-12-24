package buri.aoc.y21.d16;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model for a packet.
 *
 * @author Brian Uri!
 */
public class Packet {
	private long _version;
	private long _type;
	private long _value;
	private long _length;
	private List<Packet> _subpackets = new ArrayList<>();

	private static final int SUM = 0;
	private static final int PRODUCT = 1;
	private static final int MIN = 2;
	private static final int MAX = 3;
	private static final int LITERAL = 4;
	private static final int GREATER = 5;
	private static final int LESS = 6;

	/**
	 * Constructor
	 */
	public Packet(String raw, int start) {
		// Unpack common data.
		int i = start;
		_version = binToDec(raw.substring(i, i + 3));
		i += 3;
		_type = binToDec(raw.substring(i, i + 3));
		i += 3;

		// Unpack value or subpackets.
		if (getType() == LITERAL) {
			StringBuffer literal = new StringBuffer();
			String leading = raw.substring(i, i + 1);
			while (true) {
				literal.append(raw.substring(i + 1, i + 5));
				i += 5;
				if (leading.equals("0")) {
					break;
				}
				leading = raw.substring(i, i + 1);
			}
			_value = binToDec(literal.toString());
		}
		else {
			String bit = raw.substring(i, i + 1);
			int lengthLength = (bit.equals("0") ? 15 : 11);
			i += 1;
			long length = binToDec(raw.substring(i, i + lengthLength));
			i += lengthLength;
			if (lengthLength == 15) {
				int subpacketStart = i;
				while (i < subpacketStart + length) {
					Packet sub = new Packet(raw, i);
					getSubpackets().add(sub);
					i += sub.getLength();
				}
			}
			else {
				for (int j = 0; j < length; j++) {
					Packet sub = new Packet(raw, i);
					getSubpackets().add(sub);
					i += sub.getLength();
				}
			}
		}
		// Keep track of how long the value/subpacket part of this packet was.
		_length = i - start;
	}

	/**
	 * Sums the version of this packet and all its subpackets.
	 */
	public long getVersionSum() {
		long sum = getVersion();
		for (Packet sub : getSubpackets()) {
			sum += sub.getVersionSum();
		}
		return (sum);
	}

	/**
	 * Does the math for this packet and all its subpackets.
	 */
	public long getResult() {
		long value;
		if (getType() == SUM) {
			value = 0;
			for (Packet sub : getSubpackets()) {
				value += sub.getResult();
			}
		}
		else if (getType() == PRODUCT) {
			value = 1;
			for (Packet sub : getSubpackets()) {
				value = value * sub.getResult();
			}
		}
		else if (getType() == MIN) {
			value = Long.MAX_VALUE;
			for (Packet sub : getSubpackets()) {
				value = Math.min(sub.getResult(), value);
			}
		}
		else if (getType() == MAX) {
			value = Long.MIN_VALUE;
			for (Packet sub : getSubpackets()) {
				value = Math.max(sub.getResult(), value);
			}
		}
		else if (getType() == LITERAL) {
			value = getValue();
		}
		else if (getType() == GREATER) {
			value = (getSubpackets().get(0).getResult() > getSubpackets().get(1).getResult() ? 1 : 0);
		}
		else if (getType() == LESS) {
			value = (getSubpackets().get(0).getResult() < getSubpackets().get(1).getResult() ? 1 : 0);
		}
		else {
			// EQUAL
			value = (getSubpackets().get(0).getResult() == getSubpackets().get(1).getResult() ? 1 : 0);
		}
		return (value);
	}

	/**
	 * Helper method to convert binary strings into base 10.
	 */
	public static long binToDec(String binary) {
		return (Long.parseLong(binary, 2));
	}

	/**
	 * Helper method to convert a hex character into binary.
	 */
	public static String hexToBin(Character hex) {
		StringBuffer buffer = new StringBuffer();
		String binary = Integer.toBinaryString(Integer.parseInt(Character.toString(hex), 16));
		for (int i = 0; i < 4 - binary.length(); i++) {
			buffer.append("0");
		}
		buffer.append(binary);
		return (buffer.toString());
	}

	/**
	 * Accessor for the version
	 */
	public long getVersion() {
		return _version;
	}

	/**
	 * Accessor for the type
	 */
	public long getType() {
		return _type;
	}

	/**
	 * Accessor for the value
	 */
	public long getValue() {
		return _value;
	}

	/**
	 * Accessor for the length
	 */
	public long getLength() {
		return _length;
	}

	/**
	 * Accessor for the subpackets
	 */
	public List<Packet> getSubpackets() {
		return _subpackets;
	}
}