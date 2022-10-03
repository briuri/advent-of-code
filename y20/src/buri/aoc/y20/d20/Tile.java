package buri.aoc.y20.d20;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import buri.aoc.data.Direction;
import buri.aoc.data.grid.CharGrid;
import buri.aoc.data.tuple.Pair;

/**
 * Data model for a single tile that can be rotated, flipped, and cropped.
 *
 * @author Brian Uri!
 */
public class Tile {
	private int _id;
	private CharGrid _grid;
	private Map<Direction, String> _cache;

	public static final int TILE_SIZE = 10;
	public static final int CROP_SIZE = 8;

	/**
	 * Constructor
	 */
	public Tile(int id, CharGrid grid) {
		_id = id;
		_grid = grid;
		_cache = new HashMap<>();
	}

	/**
	 * Constructor for Part Two
	 *
	 * Crops the tiles and stitches them together into one new tile.
	 */
	public Tile(Map<Pair<Integer>, Tile> imageBoard, Pair<Integer> minCorner, int numTiles) {
		int imageSize = CROP_SIZE * numTiles;
		_grid = new CharGrid(new Pair(imageSize, imageSize));
		for (int x = 0; x < numTiles; x++) {
			for (int y = 0; y < numTiles; y++) {
				Tile crop = imageBoard.get(new Pair(minCorner.getX() + x, minCorner.getY() + y)).crop();
				for (int cropX = 0; cropX < CROP_SIZE; cropX++) {
					for (int cropY = 0; cropY < CROP_SIZE; cropY++) {
						int newX = x * CROP_SIZE + cropX;
						int newY = y * CROP_SIZE + cropY;
						getGrid().set(newX, newY, crop.getGrid().get(cropX, cropY));
					}
				}
			}
		}
	}

	/**
	 * Returns a set of all possible rotations / flips.
	 */
	public Set<Tile> getPermutations() {
		Set<Tile> permutations = new HashSet<>();

		// Original
		Tile temp = this;
		permutations.add(temp);
		for (int i = 0; i < 3; i++) {
			temp = temp.rotate();
			permutations.add(temp);
		}

		// Flipped Horizontally
		temp = this.flip();
		permutations.add(temp);
		for (int i = 0; i < 3; i++) {
			temp = temp.rotate();
			permutations.add(temp);
		}

		// Flipped Vertically covered by previous cases
		// Flipped Horizontally and Vertically covered by previous cases

		return (permutations);
	}

	/**
	 * Crops a tile by removing borders (as a copy). Only intended for original size tiles.
	 */
	public Tile crop() {
		CharGrid newGrid = new CharGrid(new Pair(CROP_SIZE, CROP_SIZE));
		for (int x = 1; x < TILE_SIZE - 1; x++) {
			for (int y = 1; y < TILE_SIZE - 1; y++) {
				newGrid.set(x - 1, y - 1, getGrid().get(x, y));
			}
		}
		return (new Tile(getId(), newGrid));
	}

	/**
	 * Flips the tile horizontally (as a copy). Works on both original and cropped tiles.
	 */
	private Tile flip() {
		int size = getGrid().getWidth();
		CharGrid newGrid = new CharGrid(new Pair(size, size));
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				newGrid.set(size - x - 1, y, getGrid().get(x, y));
			}
		}
		return (new Tile(getId(), newGrid));
	}

	/**
	 * Rotates the tile 90 degrees counter-clockwise (as a copy). Works on both original and cropped tiles.
	 */
	private Tile rotate() {
		int size = getGrid().getWidth();
		CharGrid newGrid = new CharGrid(new Pair(size, size));
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				newGrid.set(x, y, getGrid().get(size - y - 1, x));
			}
		}
		return (new Tile(getId(), newGrid));
	}

	/**
	 * Returns the border on one side.
	 */
	public String getBorder(Direction direction) {
		if (!getCache().containsKey(direction)) {
			int size = getGrid().getWidth();
			StringBuffer buffer = new StringBuffer();
			if (direction == Direction.UP) {
				for (int x = 0; x < size; x++) {
					buffer.append(getGrid().get(x, 0));
				}
			}
			else if (direction == Direction.RIGHT) {
				for (int y = 0; y < size; y++) {
					buffer.append(getGrid().get(size - 1, y));
				}
			}
			else if (direction == Direction.DOWN) {
				for (int x = 0; x < size; x++) {
					buffer.append(getGrid().get(x, size - 1));
				}
			}
			else if (direction == Direction.LEFT) {
				for (int y = 0; y < size; y++) {
					buffer.append(getGrid().get(0, y));
				}
			}
			getCache().put(direction, buffer.toString());
		}
		return (getCache().get(direction));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("Tile " + getId() + ":\n" + getGrid().toString());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (getId() * getGrid().toString().hashCode());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (toString().equals(obj.toString()));
	}

	/**
	 * Accessor for the id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Accessor for the grid
	 */
	public CharGrid getGrid() {
		return _grid;
	}

	/**
	 * Accessor for the cache
	 */
	private Map<Direction, String> getCache() {
		return _cache;
	}
}