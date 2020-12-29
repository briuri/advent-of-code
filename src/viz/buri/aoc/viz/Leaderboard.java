package buri.aoc.viz;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import buri.aoc.BaseLeaderboard;

/**
 * Alternate visualization of Novetta's private leaderboard showing the Fastest Solve Times for each puzzle. Generated
 * from the API JSON.
 *
 * @author Brian Uri!
 */
public class Leaderboard extends BaseLeaderboard {
	private static final String CURRENT_YEAR = "2020";

	private static final int TOP_DAILY = 25;
	private static final int TOP_MEDIANS = 25;

	/**
	 * Generate the Fastest Solve Times pages via a JUnit test in Eclipse.
	 *
	 * NOTE: Inactive accounts are purged yearly from Novetta's leaderboard to avoid the 200-player cap. Redownloading
	 * JSON from older years and regenerating their pages will result in missing scores.
	 */
	@Test
	public void generatePages() {
		visualizeYear("2020");
//		visualizeYear("2019");
//		visualizeYear("2018");
//		visualizeYear("2017");
//		visualizeYear("2016");
	}

	/**
	 * Generates the page for a specific year
	 */
	private void visualizeYear(String year) {
		final Map<String, Object> leaderboardJson = readLeaderboards(year);
		final List<List<PuzzleTime>> puzzleTimes = getPuzzleTimes(year, leaderboardJson);
		final List<MedianTimes> medianTimes = getMedianTimes(year, puzzleTimes, getStars(year, leaderboardJson));

		insertHeader(year);
		insertTopOverall(year, medianTimes);
		insertTopDivisionsChart(year, medianTimes);
		insertTotalSolvesChart(year, puzzleTimes);
		insertTopDaily(year, puzzleTimes);
		insertFooter(year);
		writePage(year, !year.equals(CURRENT_YEAR));
	}

	/**
	 * Looks up the alternate name of the player, if available, and also obfuscates name to deter robots.
	 */
	private String maskName(String year, String name) {
		StringBuffer buffer = new StringBuffer(getNovettas().get(year).getAlternateNameFor(name));
		buffer.insert(buffer.indexOf(" ") + 2, ANTI_INDEX);
		buffer.insert(1, ANTI_INDEX);
		return (buffer.toString());
	}

	/**
	 * Adds the HTML page header
	 */
	private void insertHeader(String year) {
		StringBuffer page = getPage();
		page.append("<html>\n<head>\n");
		page.append("<meta charset=\"UTF-8\">");
		page.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n");
		page.append("<script type=\"text/javascript\" src=\"plotly-1.58.1.min.js\"></script>\n");
		page.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"aoc.css\" />\n");
		page.append("<title>Novetta Advent of Code - Fastest Solve Times");
		page.append(" (").append(year).append(")").append("</title>\n</head>\n\n<body>\n");
		page.append("<h1>Novetta AoC - Fastest Solve Times").append(" (").append(year).append(")</h1>\n\n");
		page.append("<div class=\"navBar\">");
		if (year.equals(CURRENT_YEAR)) {
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/self\">");
			page.append("Your Times&rArr;</a> ");
			page.append(" | L.Board ");
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/private/view/105906\">");
			page.append("1&rArr;</a> ");
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/private/view/368083\">");
			page.append("2&rArr;</a> ");
			page.append(" | ");
			page.append("<a href=\"https://novetta.slack.com/archives/advent-of-code\">Slack&rArr;</a>");
			page.append(" | ");
			page.append("<a href=\"https://sites.google.com/novetta.com/novettanet/lifeatnovetta/advent-of-code?authuser=1\">NN&rArr;</a><br />");
		}
		page.append(year.equals("2020") ? year : "<a href=\"index.html\">2020</a>");
		page.append(" | ");
		page.append(year.equals("2019") ? year : "<a href=\"index-2019.html\">2019</a>");
		page.append(" | ");
		page.append(year.equals("2018") ? year : "<a href=\"index-2018.html\">2018</a>");
		page.append(" | ");
		page.append(year.equals("2017") ? year : "<a href=\"index-2017.html\">2017</a>");
		page.append(" | ");
		page.append(year.equals("2016") ? year : "<a href=\"index-2016.html\">2016</a>");
		page.append("</div>\n\n");
	}

	/**
	 * Adds the Top X Overall section.
	 */
	private void insertTopOverall(String year, List<MedianTimes> medianTimes) {
		int numMedians = Math.min(TOP_MEDIANS, medianTimes.size());
		if (numMedians == 0) {
			return;
		}
		StringBuffer page = getPage();
		page.append("<script type=\"text/javascript\">\n");
		page.append("function expand(place) {\n");
		page.append("\toldDisplay = document.getElementById('details' + place).style.display;\n");
		page.append("\tdocument.getElementById('details' + place).style.display =\n");
		page.append("\t\t(oldDisplay == 'block' ? 'none' : 'block');\n");
		page.append("\tdocument.getElementById('median' + place).style.color =\n");
		page.append("\t\t(oldDisplay == 'block' ? '#ffffff' : '#888800');\n");
		page.append("}\n");
		page.append("</script>\n");
		page.append("\n<h2>Top ").append(numMedians).append(" Overall</h2>\n");
		page.append("<p class=\"tiny\">(as of ").append(readLastModified(year)).append(")</p>\n");
		page.append("<p>Rank is based on number of stars earned, with ties broken by the fastest median solve time.\n");
		page.append("Click median time to show/hide all times.</p>\n");
		page.append("<ol>\n");

		boolean isNextTie = false;
		Novetta novetta = getNovettas().get(year);
		for (int i = 0; i < numMedians; i++) {
			MedianTimes player = medianTimes.get(i);
			String medianTime = PuzzleTime.formatTime(player.getMedianTime());
			page.append(isNextTie ? "\t" : "\t<li class=\"median\">");
			if (medianTime.length() == 8) {
				page.append("&nbsp;");
			}
			page.append("<span class=\"median medianLink\" id=\"median").append(i).append(
				"\" title=\"Show/Hide All Times\"");
			page.append(" onClick=\"expand(").append(i).append(")\">");
			page.append(medianTime);
			page.append("</span>&nbsp;&nbsp;").append(maskName(year, player.getName()));
			page.append(novetta.getDivisionFor(player.getName(), true)).append("<br />\n");
			for (int j = 0; j < 12; j++) {
				page.append("&nbsp;");
			}
			page.append(player.getStars()).append("<span class=\"emoji\" title=\"Stars\">&#x2B50;</span> ");
			if (player.hasMedals()) {
				page.append(player.getFirst()).append("<span class=\"emoji\" title=\"1st Place\">&#x1F947;</span> ");
				page.append(player.getSecond()).append("<span class=\"emoji\" title=\"2nd Place\">&#x1F948;</span> ");
				page.append(player.getThird()).append("<span class=\"emoji\" title=\"3rd Place\">&#x1F949;</span> ");
			}
			int globalCount = novetta.getGlobalCountFor(player.getName());
			if (globalCount > 0) {
				page.append(globalCount).append("<span class=\"emoji\" title=\"Global Leaderboard\">&#x1F30E;</span> ");
			}
			page.append("\n<div class=\"details\" id=\"details").append(i).append("\">\n");
			int totalTimes = player.getTimes().size();
			for (int j = 0; j < totalTimes; j++) {
				String time = PuzzleTime.formatTime(player.getTimes().get(j));
				page.append("\t");
				if (time.length() == 8) {
					page.append("&nbsp;");
				}

				// Averaged median
				if (totalTimes % 2 == 0 && (j == totalTimes / 2 - 1)) {
					page.append("<span class=\"median\">").append(time).append("</span>");
					page.append("&nbsp;&nbsp;average is");
				}
				// Single median
				else if (j == totalTimes / 2) {
					page.append("<span class=\"median\">").append(time).append("</span>");
					page.append("&nbsp;&nbsp;current median");
				}
				// 13th median (superceded by current median once all 50 stars are earned).
				else if (j == 12) {
					page.append("<span class=\"median\">").append(time).append("</span>");
					page.append("&nbsp;&nbsp;13th fastest median");
				}
				else {
					page.append(time);
				}
				page.append("<br />\n");
			}
			page.append("</div>\n");
			isNextTie = (i + 1 < numMedians && player.getMedianTime().equals(medianTimes.get(i + 1).getMedianTime()));
			page.append(isNextTie ? "<br />\n" : "</li>\n");
		}
		page.append("</ol>\n");
	}

	/**
	 * Adds the Top X Overall by Division section.
	 */
	private void insertTopDivisionsChart(String year, List<MedianTimes> medianTimes) {
		int numMedians = Math.min(TOP_MEDIANS, medianTimes.size());
		if (numMedians == 0) {
			return;
		}
		Map<String, Integer> counts = new TreeMap<>();
		for (String division : getNovettas().get(year).getAllDivisions()) {
			counts.put(division, 0);
		}
		for (int i = 0; i < numMedians; i++) {
			MedianTimes player = medianTimes.get(i);
			String division = getNovettas().get(year).getDivisionFor(player.getName(), false);
			if (division.length() > 0) {
				counts.put(division, counts.get(division) + 1);
			}
		}
		int max = 0;
		for (int i : counts.values()) {
			max = Math.max(max, i);
		}

		StringBuffer page = getPage();
		page.append("\n<a name=\"division\"></a><h2>Top ").append(numMedians).append(" Overall by Division</h2>\n");
		page.append("<p class=\"tiny\">(as of ").append(readLastModified(year)).append(")</p>\n");
		page.append("<div id=\"chartDivisions\"></div>\n");
		page.append("<script type=\"text/javascript\">\n");
		page.append("var xValues = [\n\t");
		for (Iterator<String> iter = counts.keySet().iterator(); iter.hasNext(); ) {
			page.append("'").append(iter.next()).append("'");
			if (iter.hasNext()) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("var yValues = [\n\t");
		for (Iterator<String> iter = counts.keySet().iterator(); iter.hasNext(); ) {
			page.append(counts.get(iter.next()));
			if (iter.hasNext()) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("var dataDivisions = [{\n");
		page.append("\tx: xValues,\n");
		page.append("\ty: yValues,\n");
		page.append("\tmarker: { color: '#006eb7' },\n");
		page.append("\ttext: yValues.map(String),\n");
		page.append("\ttextposition: 'outside',\n");
		page.append("\ttype: 'bar'\n");
		page.append("}];\n");
		page.append("var layout = {\n");
		page.append("\tfont: { family: 'monospace', color: '#cccccc' },\n");
		page.append("\tpaper_bgcolor: '#0f0f23',\n");
		page.append("\tplot_bgcolor: '#0f0f23',\n");
		page.append("\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n");
		page.append("\tyaxis: {range: [0, ").append(max + 2).append("]}\n");
		page.append("};\n");
		page.append("var options = {displayModeBar: false, responsive: true, staticPlot: true}\n");
		page.append("Plotly.newPlot('chartDivisions', dataDivisions, layout, options);\n");
		page.append("</script>\n");
	}

	/**
	 * Adds the Total Solves chart.
	 */
	private void insertTotalSolvesChart(String year, List<List<PuzzleTime>> puzzleTimes) {
		StringBuffer page = getPage();
		page.append("\n<a name=\"total\"></a><h2>Total Solves (Both Parts) by Day</h2>\n");
		page.append("<p class=\"tiny\">(as of ").append(readLastModified(year)).append(")</p>\n");
		page.append("<div id=\"chartParticipation\"></div>\n");
		page.append("<script type=\"text/javascript\">\n");
		page.append("var xValues = [\n\t");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			page.append(i + 1);
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("var yValues = [\n\t");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			page.append(puzzleTimes.get(i).size());
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("var dataParticipation = [{\n");
		page.append("\tx: xValues,\n");
		page.append("\ty: yValues,\n");
		page.append("\tmarker: { color: '#006eb7' },\n");
		page.append("\ttext: yValues.map(String),\n");
		page.append("\ttextposition: 'outside',\n");
		page.append("\ttype: 'bar'\n");
		page.append("}];\n");
		page.append("var layout = {\n");
		page.append("\tfont: { family: 'monospace', color: '#cccccc' },\n");
		page.append("\tpaper_bgcolor: '#0f0f23',\n");
		page.append("\tplot_bgcolor: '#0f0f23',\n");
		page.append("\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n");
		page.append("};\n");
		page.append("var options = {displayModeBar: false, responsive: true, staticPlot: true}\n");
		page.append("Plotly.newPlot('chartParticipation', dataParticipation, layout, options);\n");
		page.append("</script>\n");
		page.append("<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>");
	}

	/**
	 * Adds the Top X Daily for each puzzle.
	 */
	private void insertTopDaily(String year, List<List<PuzzleTime>> puzzleTimes) {
		boolean allEmpty = true;
		boolean alertShown = false;

		StringBuffer page = getPage();
		page.append("\n<h2>Top ").append(TOP_DAILY).append(" Daily</h2>\n");
		page.append("<p class=\"tiny\">(as of ").append(readLastModified(year)).append(")</p>\n");
		page.append("<p>Rank is based on time to complete both puzzle parts after midnight release.</p><div class=\"clear\"></div><div>\n");
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<PuzzleTime> places = puzzleTimes.get(i);
			if (!places.isEmpty()) {
				allEmpty = false;
				int day = i + 1;
				Puzzle puzzle = getPuzzles().get(year).get(i);
				page.append("<div class=\"daily\">\n");
				page.append("<a name=\"day").append(day).append("\"></a>");
				page.append("<h3><a href=\"https://adventofcode.com/").append(year).append("/day/").append(day);
				page.append("\">").append(puzzle.getTitle()).append("</a></h3>\n");
				page.append("<ol>\n");

				boolean isNextTie = false;
				for (int place = 0; place < Math.min(TOP_DAILY, places.size()); place++) {
					PuzzleTime record = places.get(place);
					String time = record.getFormattedTime();
					page.append(isNextTie ? "\t" : "\t<li>");
					if (place + 1 <= puzzle.getGlobalCount()) {
						page.append("<a href=\"https://adventofcode.com/").append(year);
						page.append("/leaderboard/day/").append(day).append("\"><sup class=\"global\">*</sup></a>");
					}
					else if (time.length() == 8) {
						page.append("&nbsp;");
					}
					page.append(time);
					page.append("&nbsp;&nbsp;").append(maskName(year, record.getName()));

					isNextTie = (place + 1 < places.size() && record.getTimeCompleted() == places.get(place + 1).getTimeCompleted());
					page.append(isNextTie ? "<br />\n" : "</li>\n");
				}
				page.append("</ol>\n");
				page.append("</div>\n");

				if (!alertShown) {
					// Show console message for most recent time recorded on most recent day, and total number of solves.
					PuzzleTime mostRecent = places.get(places.size() - 1);
					int total = 0;
					for (List<PuzzleTime> times : puzzleTimes) {
						total += times.size();
					}

					StringBuffer alert = new StringBuffer();
					alert.append("Day ").append(day).append(": ").append(places.size()).append(". ");
					alert.append(mostRecent.getFormattedTime()).append(" ").append(mostRecent.getName()).append("\n");
					alert.append(total).append(" Part 2 solves");
					System.out.println(alert.toString());
					alertShown = true;
				}
			}
		}
		page.append("<div class=\"clear\"></div></div>\n");
		if (allEmpty) {
			page.append("<p class=\"empty\">No times recorded yet.</p>");
		}
	}

	/**
	 * Adds the HTML page footer
	 */
	private void insertFooter(String year) {
		StringBuffer page = getPage();
		page.append("<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>");
		page.append("<p class=\"tiny\"><sup class=\"global\">*</sup> Top 100 on the daily Global Leaderboard</p>\n");
		page.append("</body>\n</html>");
	}
}
