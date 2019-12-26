package buri.aoc.y16.d04;

import java.util.List;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;
import buri.aoc.data.CharFrequency;

/**
 * Day 4: Security Through Obscurity
 * 
 * @author Brian Uri!
 */
public class Day04 extends BasePuzzle {

	/**
	 * Returns input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile("2016/04", fileIndex));
	}

	/**
	 * Part 1:
	 * What is the sum of the sector IDs of the real rooms?
	 * 
	 * Part 2:
	 * What is the sector ID of the room where North Pole objects are stored?
	 */
	public static int getResult(Part part, List<String> input) {
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
		return Integer.valueOf(room.substring(start, end));
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
		StringBuffer buffer = new StringBuffer();
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
			buffer.append(value);
		}
		return (buffer.toString());
	}
}