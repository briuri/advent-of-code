package buri.aoc.y16.d04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day04 extends Puzzle {

	/**
	 * Input: A room code on each line
	 * Output: List of Strings
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
		int sectorId = -1;
		for (String room : input) {
			if (isRealRoom(room)) {
				sectorId = getSectorId(room);
				if (decryptName(room).equals("northpole-object-storage")) {
					return (sectorId);
				}
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
		Map<Character, Integer> frequency = new HashMap<>();
		for (Character value : name.toCharArray()) {
			if (frequency.get(value) == null) {
				frequency.put(value, 0);
			}
			frequency.put(value, frequency.get(value) + 1);
		}
		List<Map.Entry<Character, Integer>> list = new ArrayList<>(frequency.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
			@Override
			public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
				int compare = o2.getValue() - o1.getValue();
				if (compare == 0) {
					compare = o1.getKey().compareTo(o2.getKey());
				}
				return (compare);
			}
		});
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < Math.min(5, list.size()); i++) {
			buffer.append(list.get(i).getKey());
		}
		String checksum = room.substring(room.indexOf('[') + 1, room.indexOf(']'));
		return (buffer.toString().equals(checksum));
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