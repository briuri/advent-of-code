package buri.aoc.y21.d19;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.data.tuple.Triple;

/**
 * Data model for a scanner.
 *
 * @author Brian Uri!
 */
public class Scanner {
	private String _id;
	private Triple<Integer> _location;
	private List<Triple<Integer>> _beacons;
	List<List<Triple<Integer>>> _permutations = null;

	/**
	 * Constructor
	 */
	public Scanner(String id, List<Triple<Integer>> beacons) {
		_id = id;
		_beacons = beacons;
		_permutations = new ArrayList<>();

		// 8 X-Axis
		List<Triple<Integer>> newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX() * -1, beacon.getY() * -1, beacon.getZ()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX() * -1, beacon.getZ() * -1, beacon.getY() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX() * -1, beacon.getZ(), beacon.getY()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX() * -1, beacon.getY(), beacon.getZ() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX(), beacon.getY() * -1, beacon.getZ() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX(), beacon.getZ() * -1, beacon.getY()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX(), beacon.getZ(), beacon.getY() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getX(), beacon.getY(), beacon.getZ()));
		}
		_permutations.add(newBeacons);

		// 8 Y-Axis
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY() * -1, beacon.getX() * -1, beacon.getZ() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY() * -1, beacon.getZ() * -1, beacon.getX()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY() * -1, beacon.getZ(), beacon.getX() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY() * -1, beacon.getX(), beacon.getZ()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY(), beacon.getX() * -1, beacon.getZ()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY(), beacon.getZ() * -1, beacon.getX() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY(), beacon.getZ(), beacon.getX()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getY(), beacon.getX(), beacon.getZ() * -1));
		}
		_permutations.add(newBeacons);

		// 8 Z-Axis
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ() * -1, beacon.getX() * -1, beacon.getY()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ() * -1, beacon.getY() * -1, beacon.getX() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ() * -1, beacon.getY(), beacon.getX()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ() * -1, beacon.getX(), beacon.getY() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ(), beacon.getX() * -1, beacon.getY() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ(), beacon.getY() * -1, beacon.getX()));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ(), beacon.getY(), beacon.getX() * -1));
		}
		_permutations.add(newBeacons);
		newBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : getBeacons()) {
			newBeacons.add(new Triple<Integer>(beacon.getZ(), beacon.getX(), beacon.getY()));
		}
		_permutations.add(newBeacons);
	}

	/**
	 * Use each point as a test point and try to offset the overlapping scanner to that point, then see how many points
	 * overlap. Returns true if an overlap was found and updates the test scanner to be in the correct reference frame.
	 */
	public boolean findFirstOverlap(Scanner testScanner) {
		for (Triple<Integer> beacon : getBeacons()) {
			for (List<Triple<Integer>> testBeacons : testScanner.getPermutations()) {
				for (Triple<Integer> testBeacon : testBeacons) {
					int diffX = testBeacon.getX() - beacon.getX();
					int diffY = testBeacon.getY() - beacon.getY();
					int diffZ = testBeacon.getZ() - beacon.getZ();
					int overlaps = 0;
					List<Triple<Integer>> offsetTestBeacons = Scanner.offsetAllBeacons(testBeacons, diffX, diffY, diffZ);
					// See which beacons match when the testScanner is offset.
					for (Triple<Integer> offsetTestBeacon : offsetTestBeacons) {
						if (getBeacons().contains(offsetTestBeacon)) {
							overlaps++;
						}
					}
					// If a match is found, update the test scanner to permanently be offset and store exact location.
					if (overlaps >= 12) {
						testScanner.setLocation(new Triple<Integer>(diffX, diffY, diffZ));
						testScanner.setBeacons(offsetTestBeacons);
						return (true);
					}
				}
			}
		}
		return (false);
	}

	/**
	 * Makes a copy of the beacon list with every beacon offset.
	 */
	public static List<Triple<Integer>> offsetAllBeacons(List<Triple<Integer>> beacons, int diffX, int diffY, int diffZ) {
		List<Triple<Integer>> offsetBeacons = new ArrayList<>();
		for (Triple<Integer> beacon : beacons) {
			int newX = beacon.getX() - diffX;
			int newY = beacon.getY() - diffY;
			int newZ = beacon.getZ() - diffZ;
			offsetBeacons.add(new Triple<Integer>(newX, newY, newZ));
		}
		return (offsetBeacons);
	}

	/**
	 * Accessor for the id of this scanner
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Accessor for the beacons
	 */
	public List<Triple<Integer>> getBeacons() {
		return _beacons;
	}

	/**
	 * Accessor for the beacons
	 */
	public void setBeacons(List<Triple<Integer>> beacons) {
		_beacons = beacons;
	}

	/**
	 * Accessor for the permutations
	 */
	private List<List<Triple<Integer>>> getPermutations() {
		return _permutations;
	}

	/**
	 * Accessor for the location
	 */
	public Triple<Integer> getLocation() {
		return _location;
	}

	/**
	 * Accessor for the location
	 */
	public void setLocation(Triple<Integer> location) {
		_location = location;
	}
}