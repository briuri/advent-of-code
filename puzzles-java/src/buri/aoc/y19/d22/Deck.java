package buri.aoc.y19.d22;

import java.math.BigInteger;

/**
 * Data model for a super huge space deck. Supercedes original naive Part 1 approach that used arraycopy to manipulate
 * card order.
 *
 * Card order based on factors and offsets in linear equation with mod math: (aq + b) % size
 * Then, all 3 shuffle operations can be flattened onto the deck and we can work backwards from the desired position and
 * "get card at position" can be calculated in one call.
 *
 * @author Brian Uri!
 */
public class Deck {
	private BigInteger _size;
	private BigInteger _a;
	private BigInteger _b;

	/**
	 * Constructor
	 */
	public Deck(long size) {
		_size = toBigInt(size);
		setA(toBigInt(1));
		setB(toBigInt(0));
	}

	/**
	 * Returns the position of a card, or -1 if not found.
	 */
	public long getPositionOfCard(long card) {
		for (long position = 0, size = getSize().longValue(); position < size; position++) {
			if (getCardAtPosition(position).longValue() == card) {
				return (position);
			}
		}
		return (-1);
	}

	/**
	 * Returns the card at some position after the deck has been shuffled some number of times.
	 */
	public long getCardAtPosition(long position, long shuffles) {
		BigInteger q = BigInteger.valueOf(position);
		BigInteger t = BigInteger.valueOf(shuffles);
		BigInteger one = BigInteger.valueOf(1);

		// Powers:
		// 1: a * q + b
		// 2: a * (a * q + b) + b= a^2 * q + a * b + b = a^2 * q + (a + 1) * b
		// 3: a * (a^2 * q + a * b + b) + b = a^3 * q + a^2 * b + a * b + b = a^3 * q + (a^2 + a + 1) * b
		// t: a^t * q + ((a^t - 1) / (a - 1)) * b
		BigInteger x1 = getA().modPow(t, getSize()).multiply(q); 	// a^t * q
		BigInteger x2 = getA().modPow(t, getSize()).subtract(one); 	// (a^t - 1)
		BigInteger x3 = getA().subtract(one).modInverse(getSize()); // 1/(a - 1)
		BigInteger x4 = x2.multiply(x3).multiply(getB()); 			// ((a^t - 1) / (a - 1)) * b
		BigInteger result = x1.add(x4).mod(getSize()); 				// a^t * q + ((a^t - 1) / (a - 1)) * b
		return (result.longValue());
	}

	/**
	 * Cuts the deck at some positive or negative increment.
	 *
	 * b = cardAtIncrement
	 */
	public void cut(int increment) {
		setB(getCardAtPosition(increment));
	}

	/**
	 * Reverses the deck.
	 *
	 * b = lastCard
	 * a = a * -1
	 */
	public void dealIntoNewStack() {
		setB(getCardAtPosition(-1));
		setA(getA().multiply(toBigInt(-1)));
	}

	/**
	 * Deals by increment (increment * position).
	 *
	 * a = a / increment
	 */
	public void dealWithIncrement(int increment) {
		BigInteger modInverse = toBigInt(increment).modInverse(getSize());
		setA(getA().multiply(modInverse));
	}

	/**
	 * Returns the card at this position.
	 *
	 * card = (a * position + b) % size
	 */
	private BigInteger getCardAtPosition(long position) {
		BigInteger card = getA().multiply(toBigInt(position)).add(getB()).mod(getSize());
		return (card);
	}

	/**
	 * Helper method to make big ints from little ints.
	 */
	private static BigInteger toBigInt(long i) {
		return (BigInteger.valueOf(i));
	}

	@Override
	public String toString() {
		if (getSize().longValue() < 11) {
			StringBuffer buffer = new StringBuffer("[");
			for (long position = 0; position < getSize().longValue(); position++) {
				buffer.append(getCardAtPosition(position));
				if (position + 1 < getSize().longValue()) {
					buffer.append(", ");
				}
			}
			buffer.append("]");
			return (buffer.toString());
		}
		return ("Deck(size=" + getSize() + " a=" + getA() + " b=" + getB());
	}

	/**
	 * Accessor for the size
	 */
	public BigInteger getSize() {
		return _size;
	}

	/**
	 * Accessor for the factor in aq + b
	 */
	public BigInteger getA() {
		return _a;
	}

	/**
	 * Accessor for the factor in "aq + b"
	 */
	private void setA(BigInteger a) {
		_a = a;
	}

	/**
	 * Accessor for the offset in "aq + b"
	 */
	public BigInteger getB() {
		return _b;
	}

	/**
	 * Accessor for the offset in "aq + b"
	 */
	private void setB(BigInteger b) {
		_b = b;
	}
}