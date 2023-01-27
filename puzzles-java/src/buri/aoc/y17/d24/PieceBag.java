package buri.aoc.y17.d24;

import java.util.ArrayList;
import java.util.List;

/**
 * A bag of pieces that can be queried, with pieces removed / added.
 *
 * @author Brian Uri!
 */
public class PieceBag {
	private final List<Piece> _pieces;

	/**
	 * Constructor for the first load.
	 */
	public PieceBag(List<String> input) {
		_pieces = new ArrayList<>();
		for (String line : input) {
			getPieces().add(new Piece(line));
		}
	}

	/**
	 * Returns a list of pieces with this port. Does not remove from the bag.
	 */
	public List<Piece> getAvailablePieces(int port) {
		List<Piece> list = new ArrayList<>();
		for (Piece piece : getPieces()) {
			if (piece.getPortA() == port || piece.getPortB() == port) {
				list.add(piece);
			}
		}
		return (list);
	}

	/**
	 * Adds a list of pieces to the bag.
	 */
	public void add(List<Piece> pieces) {
		getPieces().addAll(pieces);
	}

	/**
	 * Removes a piece from the bag.
	 */
	public void remove(Piece piece) {
		getPieces().remove(piece);
	}

	/**
	 * Returns the size of the bag.
	 */
	public int getSize() {
		return (getPieces().size());
	}

	/**
	 * Accessor for the pieces
	 */
	private List<Piece> getPieces() {
		return _pieces;
	}
}