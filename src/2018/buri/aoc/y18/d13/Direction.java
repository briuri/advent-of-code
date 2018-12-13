package buri.aoc.y18.d13;

/**
 * Representation of the way a cart is facing (up, down, left, right) with commands to rotate.
 * 
 * @author Brian Uri!
 */
public enum Direction {
	UP('^') {	
		@Override
        public Direction turnLeft() {
            return (LEFT);
        }
		
		@Override
        public Direction turnRight() {
            return (RIGHT);
        }
	},
	DOWN('v') {		
		@Override
        public Direction turnLeft() {
            return (RIGHT);
        }
		
		@Override
        public Direction turnRight() {
            return (LEFT);
        }
	},
	LEFT('<') {
		@Override
        public Direction turnLeft() {
            return (DOWN);
        }
		
		@Override
        public Direction turnRight() {
            return (UP);
        }
	},
	RIGHT('>') {
		@Override
        public Direction turnLeft() {
            return (UP);
        }
		
		@Override
        public Direction turnRight() {
            return (DOWN);
        }
	};
	
	public abstract Direction turnLeft();
	public abstract Direction turnRight();
	
	private char _icon;
	
	/**
	 * Constructor
	 */
	private Direction(char icon) {
		_icon = icon;
	}
	
	/**
	 * Looks up a direction based on the icon.
	 */
	public static Direction getDirectionFor(char icon) {
		for (Direction direction : Direction.values()) {
			if (icon == direction.getIcon()) {
				return (direction);
			}
		}
		return (null);
	}
	
	@Override
	public String toString() {
		return (String.valueOf(getIcon()));
	}

	/**
	 * Accessor for the icon
	 */
	public char getIcon() {
		return _icon;
	}
}
