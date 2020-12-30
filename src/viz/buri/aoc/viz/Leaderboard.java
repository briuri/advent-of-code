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

	/**
	 * Generate the Fastest Solve Times pages via a JUnit test in Eclipse.
	 *
	 * NOTE: Inactive accounts are purged yearly from Novetta's leaderboard to avoid the 200-player cap. Redownloading
	 * JSON from older years and regenerating their pages will result in missing scores.
	 */
	@Test
	public void generatePages() {
		visualizeYear("2020");
		visualizeYear("2019");
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

		resetPage();
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
		page.append("\t<meta charset=\"UTF-8\">\n");
		page.append("\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n");
		page.append("\t<title>Novetta Advent of Code - Fastest Times (").append(year).append(")").append("</title>\n\n");
		page.append("\t<link type=\"text/css\" rel=\"stylesheet\" href=\"aoc.css\" />\n");
		page.append("\t<script type=\"text/javascript\" src=\"jquery-3.5.1.min.js\"></script>\n");
		page.append("\t<script type=\"text/javascript\" src=\"plotly-1.58.1.min.js\"></script>\n");
		page.append("\t<script type=\"text/javascript\">\n");
		page.append("\t\t$(document).ready(function() {\n");
		page.append("\t\t\t$(\"span.medianLink\").click(\n");
		page.append("\t\t\t\tfunction() {\n");
		page.append("\t\t\t\t\tvar place = $(this).attr(\"id\").substring(6);\n");
		page.append("\t\t\t\t\toldDisplay = document.getElementById('details' + place).style.display;\n");
		page.append("\t\t\t\t\tif (oldDisplay == 'block') {\n");
		page.append("\t\t\t\t\t\t$('#details' + place).hide(150);\n");
		page.append("\t\t\t\t\t}\n");
		page.append("\t\t\t\t\telse {\n");
		page.append("\t\t\t\t\t\t$('#details' + place).show(300);\n");
		page.append("\t\t\t\t\t}\n");
		page.append("\t\t\t\t\tdocument.getElementById('median' + place).style.color =\n");
		page.append("\t\t\t\t\t\t(oldDisplay == 'block' ? '#ffffff' : '#888800');\n");
		page.append("\t\t\t\t});\n");
		page.append("\t\t\t});\n");
		page.append("\t</script>\n");
		page.append("</head>\n\n<body>\n");
		page.append("\t<h1>Novetta AoC - Fastest Times").append(" (").append(year).append(")</h1>\n\n");
		page.append("\t<div class=\"navBar\">\n");
		if (year.equals(CURRENT_YEAR)) {
			page.append("\t\t<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/self\">Your Times&rArr;</a> | ");
			page.append("L.Board ");
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/private/view/105906\">1&rArr;</a> ");
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/private/view/368083\">2&rArr;</a> | ");
			page.append("<a href=\"https://novetta.slack.com/archives/advent-of-code\">Slack&rArr;</a> | ");
			page.append("<a href=\"https://sites.google.com/novetta.com/novettanet/lifeatnovetta/advent-of-code?authuser=1\">NN&rArr;</a><br />\n");
		}
		page.append("\t\t").append(year.equals("2020") ? year : "<a href=\"index.html\">2020</a>").append(" | ");
		page.append(year.equals("2019") ? year : "<a href=\"index-2019.html\">2019</a>").append(" | ");
		page.append(year.equals("2018") ? year : "<a href=\"index-2018.html\">2018</a>").append(" | ");
		page.append(year.equals("2017") ? year : "<a href=\"index-2017.html\">2017</a>").append(" | ");
		page.append(year.equals("2016") ? year : "<a href=\"index-2016.html\">2016</a>\n");
		page.append("\t</div>\n\n");
	}

	/**
	 * Adds the Top X Overall section.
	 */
	private void insertTopOverall(String year, List<MedianTimes> medianTimes) {
		int numMedians = Math.min(getNovettas().get(year).getPlaces(), medianTimes.size());
		if (numMedians == 0) {
			return;
		}
		StringBuffer page = getPage();
		page.append("\t<h2>Top ").append(numMedians).append(" Overall</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<p>Rank is based on number of stars earned, with ties broken by the fastest median solve time. ");
		page.append("Click median time to show/hide all times.</p>\n");
		page.append("\t<div class=\"clear\"></div>\n\n<div class=\"overall\">\n");
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
			page.append("<span class=\"median medianLink\" id=\"median").append(i).append("\" title=\"Show/Hide All Times\">");
			page.append(medianTime);
			page.append("</span>&nbsp;&nbsp;").append(maskName(year, player.getName()));
			page.append(novetta.getDivisionFor(player.getName(), true)).append("<br />\n\t\t");
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
			page.append("\n\t\t<div class=\"details\" id=\"details").append(i).append("\">\n");
			int totalTimes = player.getTimes().size();
			for (int j = 0; j < totalTimes; j++) {
				String time = PuzzleTime.formatTime(player.getTimes().get(j));
				page.append("\t\t\t");
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
			page.append("\t\t</div>\n");
			isNextTie = (i + 1 < numMedians && player.getMedianTime().equals(medianTimes.get(i + 1).getMedianTime()));
			page.append(isNextTie ? "\t<br />\n" : "\t</li>\n");
			// Break overall scores into two columns for > Top 10.
			if (i == numMedians / 2 && numMedians > 10) {
				page.append("</ol>\n</div>\n<div class=\"overall\">\n<ol start=\"").append(i + 2).append("\">\n");
			}
		}
		page.append("</ol></div>\n<div class=\"clear\"></div>\n");
	}

	/**
	 * Adds the Top X Overall by Division section.
	 */
	private void insertTopDivisionsChart(String year, List<MedianTimes> medianTimes) {
		int numMedians = Math.min(getNovettas().get(year).getPlaces(), medianTimes.size());
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
		page.append("\n\t<a name=\"division\"></a><h2>Top ").append(numMedians).append(" Overall by Division</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<div id=\"chartDivisions\"></div>\n");
		page.append("\t<script type=\"text/javascript\">\n");
		page.append("\t\tvar xValues = [");
		for (Iterator<String> iter = counts.keySet().iterator(); iter.hasNext(); ) {
			page.append("'").append(iter.next()).append("'");
			if (iter.hasNext()) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar yValues = [");
		for (Iterator<String> iter = counts.keySet().iterator(); iter.hasNext(); ) {
			page.append(counts.get(iter.next()));
			if (iter.hasNext()) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar dataDivisions = [{\n");
		page.append("\t\t\tx: xValues,\n");
		page.append("\t\t\ty: yValues,\n");
		page.append("\t\t\tmarker: { color: '#006eb7' },\n");
		page.append("\t\t\ttext: yValues.map(String),\n");
		page.append("\t\t\ttextposition: 'outside',\n");
		page.append("\t\t\ttype: 'bar'\n");
		page.append("\t\t}];\n");
		page.append("\t\tvar layout = {\n");
		page.append("\t\t\tfont: { family: 'monospace', color: '#cccccc' },\n");
		page.append("\t\t\tpaper_bgcolor: '#0f0f23',\n");
		page.append("\t\t\tplot_bgcolor: '#0f0f23',\n");
		page.append("\t\t\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n");
		page.append("\t\t\tyaxis: {range: [0, ").append(max + 2).append("]}\n");
		page.append("\t\t};\n");
		page.append("\t\tvar options = {displayModeBar: false, responsive: true, staticPlot: true}\n");
		page.append("\t\tPlotly.newPlot('chartDivisions', dataDivisions, layout, options);\n");
		page.append("\t</script>\n");
	}

	/**
	 * Adds the Total Solves chart.
	 */
	private void insertTotalSolvesChart(String year, List<List<PuzzleTime>> puzzleTimes) {
		StringBuffer page = getPage();
		page.append("\n\t<a name=\"total\"></a><h2>Total Solves (Both Parts) by Day</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<div id=\"chartParticipation\"></div>\n");
		page.append("\t<script type=\"text/javascript\">\n");
		page.append("\t\tvar xValues = [");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			page.append(i + 1);
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar yValues = [");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			page.append(puzzleTimes.get(i).size());
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar dataParticipation = [{\n");
		page.append("\t\t\tx: xValues,\n");
		page.append("\t\t\ty: yValues,\n");
		page.append("\t\t\tmarker: { color: '#006eb7' },\n");
		page.append("\t\t\ttext: yValues.map(String),\n");
		page.append("\t\t\ttextposition: 'outside',\n");
		page.append("\t\t\ttype: 'bar'\n");
		page.append("\t\t}];\n");
		page.append("\t\tvar layout = {\n");
		page.append("\t\t\tfont: { family: 'monospace', color: '#cccccc' },\n");
		page.append("\t\t\tpaper_bgcolor: '#0f0f23',\n");
		page.append("\t\t\tplot_bgcolor: '#0f0f23',\n");
		page.append("\t\t\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n");
		page.append("\t\t};\n");
		page.append("\t\tvar options = {displayModeBar: false, responsive: true, staticPlot: true}\n");
		page.append("\t\tPlotly.newPlot('chartParticipation', dataParticipation, layout, options);\n");
		page.append("\t</script>\n\n");
		page.append("\t<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>\n\n");
	}

	/**
	 * Adds the Top X Daily for each puzzle.
	 */
	private void insertTopDaily(String year, List<List<PuzzleTime>> puzzleTimes) {
		boolean allEmpty = true;
		boolean alertShown = !year.equals(CURRENT_YEAR);

		StringBuffer page = getPage();
		page.append("\n\t<h2>Top ").append(getNovettas().get(year).getPlaces()).append(" Daily</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<p>Rank is based on time to complete both puzzle parts after midnight release.</p>\n");
		page.append("\t<div class=\"clear\"></div>\n\n");
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<PuzzleTime> places = puzzleTimes.get(i);
			if (!places.isEmpty()) {
				allEmpty = false;
				int day = i + 1;
				Puzzle puzzle = getPuzzles().get(year).get(i);
				page.append("<div class=\"daily\">\n");
				page.append("\t<a name=\"day").append(day).append("\"></a>");
				page.append("<h3><a href=\"https://adventofcode.com/").append(year).append("/day/").append(day);
				page.append("\">").append(puzzle.getTitle()).append("</a></h3>\n");
				page.append("\t<ol>\n");

				boolean isNextTie = false;
				for (int place = 0; place < Math.min(getNovettas().get(year).getPlaces(), places.size()); place++) {
					PuzzleTime record = places.get(place);
					String time = record.getFormattedTime();
					page.append(isNextTie ? "\t\t" : "\t\t<li>");
					if (place + 1 <= puzzle.getGlobalCount()) {
						page.append("<a href=\"https://adventofcode.com/").append(year);
						page.append("/leaderboard/day/").append(day).append("\"><span class=\"global\">*</span></a>");
					}
					else if (time.length() == 8) {
						page.append("&nbsp;");
					}
					page.append(time);
					page.append("&nbsp;&nbsp;").append(maskName(year, record.getName()));

					isNextTie = (place + 1 < places.size() && record.getTimeCompleted() == places.get(place + 1).getTimeCompleted());
					page.append(isNextTie ? "<br />\n" : "</li>\n");
				}
				page.append("\t</ol>\n");
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
		page.append("<div class=\"clear\"></div>\n\n");
		if (allEmpty) {
			page.append("\t<p class=\"empty\">No times recorded yet.</p>\n");
		}
	}

	/**
	 * Adds the HTML page footer
	 */
	private void insertFooter(String year) {
		StringBuffer page = getPage();
		page.append("\t<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>\n");
		page.append("\t<p class=\"tiny\"><span class=\"global\">*</span>Top 100 on the daily Global Leaderboard</p>\n");
		page.append("</body>\n</html>");
	}
}
