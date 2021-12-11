package buri.aoc.y20.d20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.Direction;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Day 20: Jurassic Jigsaw
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	private static final int MONSTER_SIZE = 15;
	private static final int MONSTER_WIDTH = 20;
	private static final int MONSTER_HEIGHT = 3;

	/**
	 * Part 1:
	 * What do you get if you multiply together the IDs of the four corner tiles?
	 *
	 * Part 2:
	 * How many # are not part of a sea monster?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Tile> tiles = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).indexOf("Tile") != -1) {
				int id = Integer.valueOf(input.get(i).split(" ")[1].split(":")[0]);
				i++;

				String nextLine = input.get(i);
				CharGrid grid = new CharGrid(new Pair(Tile.TILE_SIZE, Tile.TILE_SIZE));
				int y = 0;
				while (!nextLine.isEmpty()) {
					for (int x = 0; x < Tile.TILE_SIZE; x++) {
						grid.set(x, y, nextLine.charAt(x));
					}
					i++;
					y++;
					nextLine = input.get(i);
				}
				tiles.add(new Tile(id, grid));
			}
		}

		Set<Tile> permutations = new HashSet<>();
		Tile start = null;
		// Save all permutations of each tile, except for the very first one.
		for (Tile tile : tiles) {
			if (start == null) {
				start = tile;
			}
			else {
				permutations.addAll(tile.getPermutations());
			}
		}

		// Assemble the image by starting with the first tile and finding its neighbors.
		Map<Pair<Integer>, Tile> imageBoard = new HashMap<>();
		Pair<Integer> center = new Pair(0, 0);
		imageBoard.put(center, start);
		findNeighbors(imageBoard, permutations, start, center);

		// Calculate the bounds.
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Pair<Integer> point : imageBoard.keySet()) {
			minX = Math.min(minX, point.getX());
			maxX = Math.max(maxX, point.getX());
			minY = Math.min(minY, point.getY());
			maxY = Math.max(maxY, point.getY());
		}

		if (part == Part.ONE) {
			// Multiply the corner values.
			long product = 1L;
			product *= imageBoard.get(new Pair(minX, minY)).getId();
			product *= imageBoard.get(new Pair(maxX, minY)).getId();
			product *= imageBoard.get(new Pair(minX, maxY)).getId();
			product *= imageBoard.get(new Pair(maxX, maxY)).getId();
			return (product);
		}

		// Create a new tile containing all of the cropped ones.
		Tile stitchedTile = new Tile(imageBoard, new Pair(minX, minY), maxX - minX + 1);

		// Only one of the orientations will have monsters in it.
		for (Tile permutation : stitchedTile.getPermutations()) {
			int monsterCount = countMonsters(permutation);
			if (monsterCount > 0) {
				return (permutation.getGrid().count('#') - (monsterCount * MONSTER_SIZE));
			}
		}
		throw new RuntimeException("Found no monsters.");
	}

	/**
	 * Checks every permutation of tiles to find nearby neighbors and fills in those slots. Then recursively
	 * (depth-first) checks neighbors.
	 */
	protected static void findNeighbors(Map<Pair<Integer>, Tile> image, Set<Tile> allTiles, Tile start, Pair<Integer> center) {
		for (Tile test : allTiles) {
			if (test.getId() == start.getId()) {
				continue;
			}
			Pair<Integer> up = new Pair<>(center.getX(), center.getY() - 1);
			Pair<Integer> right = new Pair<>(center.getX() + 1, center.getY());
			Pair<Integer> down = new Pair<>(center.getX(), center.getY() + 1);
			Pair<Integer> left = new Pair<>(center.getX() - 1, center.getY());

			if (!image.containsKey(up) && start.getBorder(Direction.UP).equals(test.getBorder(Direction.DOWN))) {
				image.put(up, test);
				findNeighbors(image, allTiles, test, up);
			}
			if (!image.containsKey(right) && start.getBorder(Direction.RIGHT).equals(test.getBorder(Direction.LEFT))) {
				image.put(right, test);
				findNeighbors(image, allTiles, test, right);
			}
			if (!image.containsKey(down) && start.getBorder(Direction.DOWN).equals(test.getBorder(Direction.UP))) {
				image.put(down, test);
				findNeighbors(image, allTiles, test, down);
			}
			if (!image.containsKey(left) && start.getBorder(Direction.LEFT).equals(test.getBorder(Direction.RIGHT))) {
				image.put(left, test);
				findNeighbors(image, allTiles, test, left);
			}
		}
	}

	/**
	 * Counts sea monsters in the tile. A sea monster is a 20x3 mask of #s:
	 *
	 *  01234567890123456789
	 * 0                  #
	 * 1#    ##    ##    ###
	 * 2 #  #  #  #  #  #
	 */
	protected static int countMonsters(Tile stitchedTile) {
		CharGrid grid = stitchedTile.getGrid();
		int monsterCount = 0;
		for (int x = 0; x < grid.getWidth() - MONSTER_WIDTH; x++) {
			for (int y = 0; y < grid.getHeight() - MONSTER_HEIGHT; y++) {
				boolean found = (grid.get(x, y + 1) == '#'
					&& grid.get(x + 1, y + 2) == '#'
					&& grid.get(x + 4, y + 2) == '#'
					&& grid.get(x + 5, y + 1) == '#'
					&& grid.get(x + 6, y + 1) == '#'
					&& grid.get(x + 7, y + 2) == '#'
					&& grid.get(x + 10, y + 2) == '#'
					&& grid.get(x + 11, y + 1) == '#'
					&& grid.get(x + 12, y + 1) == '#'
					&& grid.get(x + 13, y + 2) == '#'
					&& grid.get(x + 16, y + 2) == '#'
					&& grid.get(x + 17, y + 1) == '#'
					&& grid.get(x + 18, y) == '#'
					&& grid.get(x + 18, y + 1) == '#'
					&& grid.get(x + 19, y + 1) == '#');
				if (found) {
					monsterCount++;
				}
			}
		}
		return (monsterCount);
	}
}