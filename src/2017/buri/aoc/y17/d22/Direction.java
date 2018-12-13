package buri.aoc.y17.d22;

/**
 * Representation of the way the virus carrier is facing.
 * 
 * @author Brian Uri!
 */
public enum Direction {
	UP('^') {
		@Override
		public Direction reverse() {
			return (DOWN);
		}
		
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
		public Direction reverse() {
			return (UP);
		}
		
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
		public Direction reverse() {
			return (RIGHT);
		}
		
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
		public Direction reverse() {
			return (LEFT);
		}
		
		@Override
		public Direction turnLeft() {
			return (UP);
		}

		@Override
		public Direction turnRight() {
			return (DOWN);
		}
	};

	public abstract Direction reverse();
	public abstract Direction turnLeft();
	public abstract Direction turnRight();

	private char _icon;

	/**
	 * Constructor
	 */
	private Direction(char icon) {
		_icon = icon;
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
