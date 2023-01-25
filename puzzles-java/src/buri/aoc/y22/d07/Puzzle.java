package buri.aoc.y22.d07;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Day 07: No Space Left on Device
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(95437L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1644735L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(24933642L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1300850L, result);
	}

	/**
	 * Part 1:
	 * What is the sum of the total sizes of those directories?
	 *
	 * Part 2:
	 * What is the total size of that directory?
	 */
	public static long getResult(Part part, List<String> input) {
		File rootDirectory = new File("/");
		File currentDirectory = rootDirectory;
		Set<File> files = new HashSet<>();

		int i = 0;
		while (i < input.size()) {
			String[] tokens = input.get(i).split(" ");
			if (tokens[0].equals("$")) {
				if (tokens[1].equals("cd")) {
					if (tokens[2].equals("/")) {
						currentDirectory = rootDirectory;
						files.add(rootDirectory);
					}
					else if (tokens[2].equals("..")) {
						currentDirectory = currentDirectory.getParent();
					}
					else {
						File file = new File(tokens[2]);
						currentDirectory.addFile(file);
						currentDirectory = file;
						files.add(file);
					}
				}
				else if (tokens[1].equals("ls")) {
					// Ignore nested directories. If we care about them, we will cd into them later.
					while (i + 1 < input.size() && !input.get(i + 1).startsWith("$")) {
						String[] listedFile = input.get(i + 1).split(" ");
						if (!listedFile[0].equals("dir")) {
							File file = new File(listedFile[1], Long.parseLong(listedFile[0]));
							currentDirectory.addFile(file);
							files.add(file);
						}
						i++;
					}
				}
				i++;
			}
		}

		// Create a list of all directory sizes from smallest to largest.
		List<Long> directorySizes = new ArrayList<>();
		for (File file : files) {
			if (file.isDirectory()) {
				directorySizes.add(file.getSize());
			}
		}
		Collections.sort(directorySizes);

		if (part == Part.ONE) {
			long sum = 0;
			for (Long size : directorySizes) {
				if (size <= 100000) {
					sum += size;
				}
			}
			return (sum);
		}

		long unusedSpace = 70000000 - rootDirectory.getSize();
		long unusedSpaceNeeded = 30000000;
		// Since list is sorted, first directory that's big enough is the smallest one.
		for (Long size : directorySizes) {
			if (unusedSpace + size >= unusedSpaceNeeded) {
				return (size);
			}
		}
		throw new RuntimeException("Couldn't find a directory big enough to free up space.");
	}
}