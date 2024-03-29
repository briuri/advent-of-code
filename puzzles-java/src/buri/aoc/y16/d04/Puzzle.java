package buri.aoc.y16.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import java.util.List;

/**
 * Day 4: Security Through Obscurity
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1514L, 1, false);
		assertRun(137896L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(501L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the sum of the sector IDs of the real rooms?
	 *
	 * Part 2:
	 * What is the sector ID of the room where North Pole objects are stored?
	 */
	protected long runLong(Part part, List<String> input) {
		if (part == Part.ONE) {
			int sum = 0;
			for (String room : input) {
				if (isRealRoom(room)) {
					sum += getSectorId(room);
				}
			}
			return (sum);
		}

		// Part TWO
		for (String room : input) {
			if (isRealRoom(room) && decryptName(room).equals("northpole-object-storage")) {
				return (getSectorId(room));
			}
		}
		throw new RuntimeException("Could not find room.");
	}

	/**
	 * Each room consists of an encrypted name (lowercase letters separated by dashes) followed by a dash, a sector ID,
	 * and a checksum in square brackets.
	 *
	 * A room is real (not a decoy) if the checksum is the five most common letters in the encrypted name, in order,
	 * with ties broken by alphabetization.
	 */
	private static boolean isRealRoom(String room) {
		String name = room.substring(0, room.lastIndexOf('-')).replaceAll("-", "");
		CharFrequency frequency = new CharFrequency(name);
		String checksum = room.substring(room.indexOf('[') + 1, room.indexOf(']'));
		return (frequency.getHighestFrequencyChars(5).equals(checksum));
	}

	/**
	 * Returns the sector ID of a room
	 */
	private static int getSectorId(String room) {
		int start = room.lastIndexOf('-') + 1;
		int end = room.indexOf('[');
		return Integer.parseInt(room.substring(start, end));
	}

	/**
	 * Rotates the name by its sector ID.
	 *
	 * To decrypt a room name, rotate each letter forward through the alphabet a number of times equal to the room's
	 * sector ID. A becomes B, B becomes C, Z becomes A, and so on. Dashes become spaces.
	 */
	private static String decryptName(String room) {
		String name = room.substring(0, room.lastIndexOf('-'));
		int sectorId = getSectorId(room);
		StringBuilder builder = new StringBuilder();
		for (Character value : name.toCharArray()) {
			if (value != '-') {
				// a - z = 97 - 122
				int rotation = sectorId % 26;
				int next = (((int) value) + rotation);
				if (next > 'z') {
					next -= 26;
				}
				value = (char) next;
			}
			builder.append(value);
		}
		return (builder.toString());
	}
}