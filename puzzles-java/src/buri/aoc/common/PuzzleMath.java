package buri.aoc.common;

import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Quad;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods for common math concerns.
 */
public class PuzzleMath {

	/**
	 * Converts strings into integers.
	 */
	public static List<Integer> toInts(List<String> rawIntegers) {
		return (rawIntegers.stream().map(Integer::valueOf).collect(Collectors.toList()));
	}

	/**
	 * Converts strings into longs.
	 */
	public static List<Long> toLongs(List<String> rawLongs) {
		return (rawLongs.stream().map(Long::valueOf).collect(Collectors.toList()));
	}

	/**
	 * Converts a long into a BigInteger
	 */
	public static BigInteger toBigInt(long value) {
		return (new BigInteger(String.valueOf(value)));
	}

	/**
	 * Gets the sum of a list of integers
	 */
	public static int getIntSum(List<Integer> list) {
		return (list.stream().mapToInt(Integer::intValue).sum());
	}

	/**
	 * Calculates the least common multiple of two numbers.
	 */
	public static long getLCM(long a, long b) {
		return (a / getGCD(a, b) * b);
	}

	/**
	 * Recursively finds the greatest common denominator of two numbers.
	 */
	public static long getGCD(long a, long b) {
		if (a == 0) {
			return (b);
		}
		return (getGCD(b % a, a));
	}

	/**
	 * Gets the entry with the minimum value from a Map
	 */
	public static <S, T extends Comparable> Map.Entry<S, T> getMin(Map<S, T> map) {
		Map.Entry<S, T> minEntry = null;
		for (Map.Entry<S, T> entry : map.entrySet()) {
			if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
				minEntry = entry;
			}
		}
		return (minEntry);
	}

	/**
	 * Gets the entry with the maximum value from a Map
	 */
	public static <S, T extends Comparable> Map.Entry<S, T> getMax(Map<S, T> map) {
		Map.Entry<S, T> maxEntry = null;
		for (Map.Entry<S, T> entry : map.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return (maxEntry);
	}

	/**
	 * Finds the bounds of a rectangle.
	 */
	public static Quad<Integer> getBounds(Set<Pair<Integer>> pairs) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Pair<Integer> pair : pairs) {
			minX = Math.min(minX, pair.getX());
			maxX = Math.max(maxX, pair.getX());
			minY = Math.min(minY, pair.getY());
			maxY = Math.max(maxY, pair.getY());
		}
		return (new Quad<>(minX, maxX, minY, maxY));
	}
}