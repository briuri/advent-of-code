package buri.aoc.y21.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Day 22: Reactor Reboot
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("39", 1, false);
		assertRun("590784", 2, false);
		assertRun("474140", 3, false);
		assertRun("582644", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("2758514936282235", 3, false);
		assertRun("1263804707062415", 0, true);
	}

	/**
	 * Part 1:
	 * How many cubes are on?
	 *
	 * Part 2:
	 * Afterward, considering all cubes, how many cubes are on?
	 */
	protected String runString(Part part, List<String> input) {
		List<Cuboid> allCuboids = new ArrayList<>();
		for (String line : input) {
			Cuboid cuboid = new Cuboid(line);
			allCuboids.add(cuboid);
		}

		Set<Cuboid> onCuboids = new HashSet<>();
		for (Cuboid cuboid : allCuboids) {
			// Ignore some for Part 1.
			if (part == Part.ONE && !cuboid.inInitArea()) {
				continue;
			}

			// Check if this cuboid overlaps with existing ones.
			Set<Cuboid> newOnCuboids = new HashSet<>();
			for (Iterator<Cuboid> iterator = onCuboids.iterator(); iterator.hasNext();) {
				Cuboid onCuboid = iterator.next();
				if (cuboid.equals(onCuboid)) {
					continue;
				}
				Cuboid intersection = cuboid.getIntersection(onCuboid);
				if (intersection != null) {
					// Chop up the existing cuboid.
					iterator.remove();
					newOnCuboids.addAll(onCuboid.remove(intersection));
				}
			}
			// After all existing cuboids have been chopped up, fit this whole cuboid in.
			if (cuboid.isOn()) {
				onCuboids.add(cuboid);
			}
			onCuboids.addAll(newOnCuboids);
		}
		BigInteger total = new BigInteger("0");
		for (Cuboid cuboid : onCuboids) {
			total = total.add(cuboid.getVolume());
		}
		return (total.toString());
	}
}