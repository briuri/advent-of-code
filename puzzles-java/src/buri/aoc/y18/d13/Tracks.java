package buri.aoc.y18.d13;

import buri.aoc.common.data.Direction;
import buri.aoc.common.data.grid.CharGrid;
import buri.aoc.common.data.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tracks consist of straight paths (| and -), curves (/ and \), and intersections (+). Curves connect exactly two
 * perpendicular pieces of track; for example, this is a closed loop:
 *
 * Several carts are also on the tracks. Carts always face either up (^), down (v), left (<), or right (>). (On your
 * initial map, the track under each cart is a straight path matching the direction the cart is facing.)
 *
 * @author Brian Uri!
 */
public class Tracks extends CharGrid {
	private final List<Cart> _carts = new ArrayList<>();
	private String _firstCollision = null;
	private int _iteration = 0;

	/**
	 * Constructor
	 */
	public Tracks(List<String> input) {
		super(new Pair<>(input.get(0).length(), input.size()));
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				char icon = line.charAt(x);
				set(x, y, icon);

				// Create carts and reveal the tracks underneath them.
				if (Direction.getDirectionFor(icon) != null) {
					getCarts().add(new Cart(new Pair<>(x, y), icon));
					switch (icon) {
						case '<':
						case '>':
							set(x, y, '-');
							break;
						case '^':
						case 'v':
							set(x, y, '|');
							break;
					}
				}
			}
		}
	}

	/**
	 * Carts all move at the same speed; they take turns moving a single step at a time. They do this based on their
	 * current location: carts on the top row move first (acting from left to right), then carts on the second row move
	 * (again from left to right), then carts on the third row, and so on. Once each cart has moved one step, the
	 * process repeats; each of these loops is called a tick.
	 */
	public void move() {
		Collections.sort(getCarts());

		List<Cart> collisions = new ArrayList<>();
		for (Cart cart : getCarts()) {
			if (collisions.contains(cart)) {
				continue;
			}
			cart.move();
			char trackUnderCart = get(cart.getPosition());
			cart.turn(trackUnderCart);

			/*
			  Collisions must be checked immediately. I originally failed by only checking after every cart had moved.
			  In a case where 3 carts arrive at the same spot, the correct way would leave 1 standing, while the wrong
			  way would remove them all.
			 */
			for (Cart otherCart : getCarts()) {
				if (cart != otherCart && cart.getPosition().equals(otherCart.getPosition())) {
					collisions.add(cart);
					collisions.add(otherCart);
					if (getFirstCollision() == null) {
						setFirstCollision(cart.getPosition().toString());
					}
				}
			}
		}

		getCarts().removeAll(collisions);
		setIteration(getIteration() + 1);
	}

	/**
	 * Returns the position of the last cart.
	 */
	public Pair getLastCartPosition() {
		return (getCarts().get(0).getPosition());
	}

	/**
	 * Accessor for the string position when the first collision occurs
	 */
	public String getFirstCollision() {
		return _firstCollision;
	}

	/**
	 * Accessor for the string position when the first collision occurs
	 */
	private void setFirstCollision(String firstCollision) {
		_firstCollision = firstCollision;
	}

	/**
	 * Accessor for the carts
	 */
	public List<Cart> getCarts() {
		return _carts;
	}

	/**
	 * Accessor for the iteration
	 */
	private int getIteration() {
		return _iteration;
	}

	/**
	 * Accessor for the iteration
	 */
	private void setIteration(int iteration) {
		_iteration = iteration;
	}
}