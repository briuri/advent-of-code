package buri.aoc.y17.d21;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Data model for a single rule.
 * 
 * ../.# => ##./#../...
 * .#./..#/### => #..#/..../..../#..#
 * 
 * @author Brian Uri!
 */
public class Rule {

	private Set<Pattern> _patterns = new HashSet<>();
	private Pattern _result;

	/**
	 * Constructor
	 */
	public Rule(String rule) {
		String tokens[] = rule.split(" => ");
		_result = new Pattern(Arrays.asList(tokens[1].split("/")));
		Pattern base = new Pattern(Arrays.asList(tokens[0].split("/")));
		Pattern base90 = base.rotate();
		Pattern base180 = base90.rotate();
		Pattern base270 = base180.rotate();
		Pattern flip = base.flip();
		Pattern flip90 = flip.rotate();
		Pattern flip180 = flip90.rotate();
		Pattern flip270 = flip180.rotate();
		getPatterns().add(base);
		getPatterns().add(base90);
		getPatterns().add(base180);
		getPatterns().add(base270);
		getPatterns().add(flip);
		getPatterns().add(flip90);
		getPatterns().add(flip180);
		getPatterns().add(flip270);
	}

	/**
	 * Returns true if the test pattern matches one of the 8 patterns in this rule.
	 */
	public boolean matches(Pattern test) {
		for (Pattern pattern : getPatterns()) {
			if (pattern.toString().equals(test.toString())) {
				return (true);
			}
		}
		return (false);
	}

	/**
	 * Returns 2 or 3, depending on what type of grid the patterns match to.
	 */
	public int getPatternSize() {
		return (getResult().getWidth() - 1);
	}

	/**
	 * Accessor for the patterns
	 */
	private Set<Pattern> getPatterns() {
		return _patterns;
	}

	/**
	 * Accessor for the result
	 */
	public Pattern getResult() {
		return _result;
	}
}
