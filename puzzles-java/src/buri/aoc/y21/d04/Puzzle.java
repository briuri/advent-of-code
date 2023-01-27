package buri.aoc.y21.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Day 04: Giant Squid
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(4512L, 1, false);
		assertRun(11774L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(1924L, 1, false);
		assertRun(4495L, 0, true);
	}

	/**
	 * Part 1:
	 * What will your final score be if you choose that board?
	 *
	 * Part 2:
	 * Once it wins, what would its final score be?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> drawnNumbers = new ArrayList<>();
		List<Board> boards = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			if (i == 0) {
				drawnNumbers = PuzzleMath.toInts(Arrays.asList(input.get(0).split(",")));
			}
			// Skip board separator lines
			else if (input.get(i).length() != 0) {
				List<Integer> rawBoard = new ArrayList<>();
				for (int x = i; x < i + Board.SIZE; x++) {
					for (String value : input.get(x).split(" ")) {
						if (value.trim().length() > 0) {
							rawBoard.add(Integer.valueOf(value.trim()));
						}
					}
				}
				boards.add(new Board(rawBoard));
				i += Board.SIZE;
			}
		}
		for (Integer number : drawnNumbers) {
			for (Iterator<Board> iter = boards.iterator(); iter.hasNext();) {
				Board board = iter.next();
				board.mark(number);
				if (board.isWin()) {
					iter.remove();
					if (part == Part.ONE || (part == Part.TWO && boards.size() == 0)) {
						return ((long) board.getUnmarkedSum() * number);
					}
				}
			}
		}
		throw new RuntimeException("Never found the last winning board.");
	}
}