package buri.aoc.y20.d17;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Quad;

/**
 * Day 17: Conway Cubes
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * How many cubes are left in the active state after the sixth cycle?
	 *
	 * Part 2:
	 * How many cubes are left in the active state after the sixth cycle?
	 */
	public static int getResult(Part part, List<String> input) {
		Set<Quad> activeCubes = new HashSet<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(0).length(); x++) {
				Quad quad = new Quad(x, y, 0, 0);
				if (input.get(y).charAt(x) == '#') {
					activeCubes.add(quad);
				}
			}
		}

		for (int i = 0; i < 6; i++) {
			// Determine bounds for processing.
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;
			int minZ = Integer.MAX_VALUE;
			int maxZ = Integer.MIN_VALUE;
			int minW = Integer.MAX_VALUE;
			int maxW = Integer.MIN_VALUE;
			for (Quad<Integer> cube : activeCubes) {
				minX = Math.min(minX, cube.getX());
				maxX = Math.max(maxX, cube.getX());
				minY = Math.min(minY, cube.getY());
				maxY = Math.max(maxY, cube.getY());
				minZ = Math.min(minZ, cube.getZ());
				maxZ = Math.max(maxZ, cube.getZ());
				minW = Math.min(minW, cube.getT());
				maxW = Math.max(maxW, cube.getT());
			}

			Set<Quad> nextActive = new HashSet<>();
			// Check all nearby points, not just the ones already registered in the grid.
			for (int x = minX - 1; x < maxX + 2; x++) {
				for (int y = minY - 1; y < maxY + 2; y++) {
					for (int z = minZ - 1; z < maxZ + 2; z++) {
						for (int w = minW - 1; w < maxW + 2; w++) {
							// Don't explore w dimension in part one.
							int wValue = (part == Part.ONE ? 0 : w);
							Quad<Integer> cube = new Quad(x, y, z, wValue);

							// Count active neighbors
							Set<Quad> activeNeighbors = new HashSet<>();
							for (int dx = -1; dx < 2; dx++) {
								for (int dy = -1; dy < 2; dy++) {
									for (int dz = -1; dz < 2; dz++) {
										for (int dw = -1; dw < 2; dw++) {
											// Don't explore w dimension in part one.
											int wValueNeighbor = (part == Part.ONE ? 0 : w + dw);
											Quad<Integer> neighbor = new Quad(x + dx, y + dy, z + dz, wValueNeighbor);
											// Skip self.
											if (neighbor.equals(cube)) {
												continue;
											}
											if (activeCubes.contains(neighbor)) {
												activeNeighbors.add(neighbor);
											}
										}
									}
								}
							}
							// Active stays active with 2 - 3 neighbors. Inactive becomes active with 3 neighbors.
							boolean isCubeActive = activeCubes.contains(cube);
							if ((isCubeActive && activeNeighbors.size() == 2 || activeNeighbors.size() == 3)
								|| (!isCubeActive && activeNeighbors.size() == 3)) {
								nextActive.add(cube);
							}
						}
					}
				}
			}
			activeCubes = nextActive;
		}
		return (activeCubes.size());
	}
}