package buri.aoc.viz;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import buri.aoc.BaseLeaderboard;
import buri.aoc.Part;

/**
 * Alternate visualization of Novetta's private leaderboard showing the Fastest Times for each puzzle. Generated
 * from the API JSON.
 *
 * @author Brian Uri!
 */
public class Leaderboard extends BaseLeaderboard {
	private static final String CURRENT_YEAR = "2020";

	/**
	 * Generate the Fastest Times pages via a JUnit test in Eclipse.
	 *
	 * NOTE: Inactive accounts are purged yearly from Novetta's leaderboard to avoid the 200-player cap. Redownloading
	 * JSON from older years and regenerating their pages will result in missing scores.
	 */
	@Test
	public void generatePages() {
		for (String year : YEARS) {
			visualizeYear(year);
		}
	}

	/**
	 * Generates the Fastest Times pages on a schedule, for use when I'm not around to do manual updates. Stops after 40 iterations (10 hours).
	 *
	 * No arguments required.
	 */
	public static void main(String[] args) throws Exception {
		final int minutes = 15;
		final int reps = 40;
		final Leaderboard leaderboard = new Leaderboard();
		for (int i = 0; i < reps; i++) {
			System.out.println(new Date() + " Leaderboard Auto-Update");
			// Script uses curl to pass session cookie and copy JSON to /data/viz/json.
			Process jsonDowload = Runtime.getRuntime().exec("cmd /c start C:\\projects\\aws-stage\\get-aoc-json.bat");
			jsonDowload.waitFor();

			leaderboard.generatePages();

			// Script uses AWS CLI to upload files to S3 bucket hosting static website.
			Process htmlUpload = Runtime.getRuntime().exec("cmd /c start C:\\projects\\aws-stage\\put-s3-aoc.urizone.net.bat");
			htmlUpload.waitFor();

			// Wait for next iteration.
			Thread.sleep(minutes * 60 * 1000);
		}
	}

	/**
	 * Generates the page for a specific year
	 */
	private void visualizeYear(String year) {
		final Map<String, Object> leaderboardJson = readLeaderboards(year);
		final PuzzleTimes puzzleTimes = getPuzzleTimes(year, leaderboardJson);
		final List<OverallTimes> overallTimes = getOverallTimes(year, puzzleTimes);

		resetPage();
		insertHeader(year);
		insertTopOverall(year, overallTimes);
		insertTopDivisionsChart(year, overallTimes);
		insertTotalSolvesChart(year, puzzleTimes);
		insertTopDaily(year, puzzleTimes);
		insertFooter(year);
		writePage(year, !year.equals(CURRENT_YEAR));
	}

	/**
	 * Looks up the alternate name of the player, if available, and also obfuscates name to deter robots.
	 */
	private String maskName(String year, String name) {
		Novetta novetta = getNovettas().get(year);
		StringBuffer buffer = new StringBuffer(novetta.getAlternateNameFor(name));
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
		page.append("\t<script type=\"text/javascript\" src=\"jquery-3.5.1.min.js\"></script>\n");
		page.append("\t<script type=\"text/javascript\" src=\"plotly-1.58.1.min.js\"></script>\n");
		page.append("\t<script type=\"text/javascript\" src=\"aoc.js?2021-01-02-1615\"></script>\n");
		page.append("\t<link type=\"text/css\" rel=\"stylesheet\" href=\"aoc.css?2021-01-02-1615\" />\n");
		page.append("</head>\n\n<body>\n");
		page.append("\t<h1>Novetta AoC - Fastest Times").append(" (").append(year).append(")</h1>\n\n");
		page.append("\t<div class=\"navBar\">\n");
		if (year.equals(CURRENT_YEAR)) {
			page.append("\t\t<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/self\">Your Times&rArr;</a> | ");
			page.append("L.Board ");
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/private/view/105906\">1&rArr;</a> ");
			page.append("<a href=\"https://adventofcode.com/").append(year).append("/leaderboard/private/view/368083\">2&rArr;</a> | ");
			page.append("<a href=\"https://novetta.slack.com/archives/advent-of-code\">Slack&rArr;</a> | ");
			page.append("<a href=\"https://sites.google.com/novetta.com/novettanet/lifeatnovetta/advent-of-code\">NN&rArr;</a><br />\n");
		}
		page.append("\t\t").append(year.equals("2020") ? year : "<a href=\"index.html\">2020</a>").append(" | ");
		page.append(year.equals("2019") ? year : "<a href=\"2019-top.html\">2019</a>").append(" | ");
		page.append(year.equals("2018") ? year : "<a href=\"2018-top.html\">2018</a>").append(" | ");
		page.append(year.equals("2017") ? year : "<a href=\"2017-top.html\">2017</a>").append(" | ");
		page.append(year.equals("2016") ? year : "<a href=\"2016-top.html\">2016</a>");
		page.append("\n\t</div>\n\n");
	}

	/**
	 * Adds the Top X Overall section.
	 */
	private void insertTopOverall(String year, List<OverallTimes> overallTimes) {
		Novetta novetta = getNovettas().get(year);
		int numOverall = Math.min(novetta.getPlaces(), overallTimes.size());
		if (numOverall == 0) {
			return;
		}
		StringBuffer page = getPage();
		page.append("\t<h2>Top ").append(numOverall).append(" Overall</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<p>").append(novetta.getRules()).append("</p>\n");
		page.append("\t<div class=\"clear\"></div>\n\n<div class=\"overall\">\n");
		page.append("<ol>\n");

		boolean isNextTie = false;
		for (int i = 0; i < numOverall; i++) {
			OverallTimes player = overallTimes.get(i);
			String overallTime = PuzzleTime.formatTime(player.getTiebreakerTime());
			boolean isIneligible = novetta.getIneligible().contains(player.getName());
			String timeClass = (isIneligible ? "ineligible" : "tieTime");

			// Show tiebreaker time.
			page.append(isNextTie ? "\t" : "\t<li class=\"overallRecord\">");
			page.append("<span class=\"").append(timeClass).append(" tieTimeLink\" id=\"tieTime").append(i).append("\" ");
			page.append("title=\"").append(isIneligible ? "Ineligible for Top 3 prizes" : "Show/hide all times").append("\">");
			page.append(overallTime).append("</span>&nbsp;&nbsp;");

			// Show player name and division.
			if (isIneligible) {
				page.append("<span class=\"ineligible\" title=\"Ineligible for Top 3 prizes\">");
			}
			page.append(maskName(year, player.getName()));
			page.append(novetta.getDivisionFor(player.getName(), true));
			if (isIneligible) {
				page.append("</span>");
			}
			page.append("<br />\n\t\t");

			// Show summary of medals and global records
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

			// Show expandable DIV containing all the raw times used to generate the tiebreaker time.
			page.append("\n\t\t<div class=\"details\" id=\"details").append(i).append("\">\n");
			int totalTimes = player.getTimes().size();
			for (int j = 0; j < totalTimes; j++) {
				String time = PuzzleTime.formatTime(player.getTimes().get(j));
				page.append("\t\t\t");
				// 2017+ use median time, so add descriptive text next to the key times.
				if (!year.equals("2016")) {
					// Averaged median
					if (totalTimes % 2 == 0 && (j == totalTimes / 2 - 1)) {
						page.append("<span class=\"tieTime\">").append(time).append("</span>");
						page.append("&nbsp;&nbsp;average is");
					}
					// Single median
					else if (j == totalTimes / 2) {
						page.append("<span class=\"tieTime\">").append(time).append("</span>");
						page.append("&nbsp;&nbsp;current median");
					}
					// 13th median (superceded by current median once all 50 stars are earned).
					else if (j == 12) {
						page.append("<span class=\"tieTime\">").append(time).append("</span>");
						page.append("&nbsp;&nbsp;13th fastest median");
					}
					else {
						page.append(time);
					}
				}
				// 2016 used total time instead of median time.
				else {
					page.append(time);
				}
				page.append("<br />\n");
			}
			page.append("\t\t</div>\n");
			isNextTie = (i + 1 < numOverall && player.getTiebreakerTime().equals(overallTimes.get(i + 1).getTiebreakerTime()));
			page.append(isNextTie ? "\t<br />\n" : "\t</li>\n");

			// Break overall scores into two columns for > Top 10.
			if (i == numOverall / 2 && numOverall > 10) {
				page.append("</ol>\n</div>\n<div class=\"overall\">\n<ol start=\"").append(i + 2).append("\">\n");
			}
		}
		page.append("</ol></div>\n<div class=\"clear\"></div>\n");
	}

	/**
	 * Adds the Top X Overall by Division section.
	 */
	private void insertTopDivisionsChart(String year, List<OverallTimes> overallTimes) {
		Novetta novetta = getNovettas().get(year);
		int numOverall = Math.min(novetta.getPlaces(), overallTimes.size());
		List<String> allDivisions = novetta.getAllDivisions();
		if (numOverall == 0 || allDivisions.isEmpty()) {
			return;
		}
		Map<String, Integer> counts = new TreeMap<>();
		for (String division : allDivisions) {
			counts.put(division, 0);
		}
		for (int i = 0; i < numOverall; i++) {
			OverallTimes player = overallTimes.get(i);
			String division = novetta.getDivisionFor(player.getName(), false);
			if (division.length() > 0) {
				counts.put(division, counts.get(division) + 1);
			}
		}

		// Calculate max for chart height.
		int max = 0;
		for (int i : counts.values()) {
			max = Math.max(max, i);
		}

		StringBuffer page = getPage();
		page.append("\n\t<a name=\"division\"></a><h2>Top ").append(numOverall).append(" Overall by Division</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<div id=\"chartDivisions\"></div>\n");
		page.append("\t<script type=\"text/javascript\">\n");
		page.append("\t\tvar xValues = [");
		for (Iterator<String> iter = counts.keySet().iterator(); iter.hasNext();) {
			page.append("'").append(iter.next()).append("'");
			if (iter.hasNext()) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar yValues = [");
		for (Iterator<String> iter = counts.keySet().iterator(); iter.hasNext();) {
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
	private void insertTotalSolvesChart(String year, PuzzleTimes puzzleTimes) {
		StringBuffer page = getPage();
		page.append("\n\t<a name=\"total\"></a><h2>Total Solves by Day ");
		page.append("(").append(puzzleTimes.getStars()).append(" stars)</h2>\n");
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
		page.append("\t\tvar yPart1 = [");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			page.append(puzzleTimes.getTimes(Part.ONE).get(i).size());
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar yPart2 = [");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			int split = puzzleTimes.getTimes(Part.TWO).get(i).size() - puzzleTimes.getTimes(Part.ONE).get(i).size();
			page.append(split);
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");
		page.append("\t\tvar yTotal = [");
		for (int i = 0; i < TOTAL_PUZZLES; i++) {
			page.append(puzzleTimes.getTimes(Part.TWO).get(i).size());
			if (i + 1 < TOTAL_PUZZLES) {
				page.append(",");
			}
		}
		page.append("];\n");

		page.append("\t\tvar trace1 = {\n");
		page.append("\t\t\tx: xValues,\n");
		page.append("\t\t\ty: yPart1,\n");
		page.append("\t\t\tmarker: { color: '#9999cc' },\n");
		page.append("\t\t\tname: 'Part 1',\n");
		page.append("\t\t\ttype: 'bar'\n");
		page.append("\t\t};\n");

		page.append("\t\tvar trace2 = {\n");
		page.append("\t\t\tx: xValues,\n");
		page.append("\t\t\ty: yPart2,\n");
		page.append("\t\t\tmarker: { color: '#ffff66' },\n");
		page.append("\t\t\tname: 'Part 2',\n");
		page.append("\t\t\ttext: yTotal.map(String),\n");
		page.append("\t\t\ttextposition: 'outside',\n");
		page.append("\t\t\ttype: 'bar'\n");
		page.append("\t\t};\n");

		page.append("\t\tvar dataParticipation = [trace1, trace2];\n");

		page.append("\t\tvar layout = {\n");
		page.append("\t\t\tbarmode: 'stack',\n");
		page.append("\t\t\tfont: { family: 'monospace', color: '#cccccc' },\n");
		page.append("\t\t\tlegend: { x: 1, xanchor: 'right', y: 1 },\n");
		page.append("\t\t\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n");
		page.append("\t\t\tpaper_bgcolor: '#0f0f23',\n");
		page.append("\t\t\tplot_bgcolor: '#0f0f23',\n");
		page.append("\t\t};\n");

		page.append("\t\tvar options = {displayModeBar: false, responsive: true, staticPlot: true}\n");

		page.append("\t\tPlotly.newPlot('chartParticipation', dataParticipation, layout, options);\n");
		page.append("\t</script>\n\n");
		page.append("\t<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>\n\n");
	}

	/**
	 * Adds the Top X Daily for each puzzle.
	 */
	private void insertTopDaily(String year, PuzzleTimes puzzleTimes) {
		boolean alertShown = !year.equals(CURRENT_YEAR);
		Novetta novetta = getNovettas().get(year);

		StringBuffer page = getPage();
		page.append("\n\t<h2>Top ").append(novetta.getPlaces()).append(" Daily</h2>\n");
		page.append(readLastModified(year, CURRENT_YEAR));
		page.append("\t<p>Rank is based on time to complete both puzzle parts after midnight release.</p>\n");
		page.append("\t<p><a href=\"javascript:void(0);\">\n");
		page.append("\t\t<span id=\"dailySplit\" class=\"dT dailyLink\">Show Split Times</span>");
		page.append("<span class=\"dS dailyLink\">Show Total Times</span>\n");
		page.append("\t</a></p>\n");
		page.append("\t<div class=\"clear\"></div>\n\n");
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<PuzzleTime> places = puzzleTimes.getTimes(Part.TWO).get(i);
			if (!places.isEmpty()) {
				int day = i + 1;
				Puzzle puzzle = getPuzzles().get(year).get(i);
				page.append("<div class=\"daily\">\n");
				page.append("\t<a name=\"day").append(day).append("\"></a>");
				page.append("<h3><a href=\"https://adventofcode.com/").append(year).append("/day/").append(day);
				page.append("\">").append(puzzle.getTitle()).append("</a></h3>\n");
				page.append("\t<ol>\n");

				boolean isNextTie = false;
				int maxPlaces = Math.min(novetta.getPlaces(), places.size());
				for (int place = 0; place < maxPlaces; place++) {
					PuzzleTime record = places.get(place);
					String time = PuzzleTime.formatTime(record.getTime(Part.TWO));
					page.append(isNextTie ? "\t\t" : "\t\t<li>");

					// Show total time by default
					page.append("<span class=\"dT\">").append(time).append("</span>");

					// Show split times
					time = PuzzleTime.formatTime(record.getTime(Part.ONE));
					page.append("<span class=\"dS\">").append(time).append(" ");
					time = PuzzleTime.formatTime(record.getTime(Part.TWO) - record.getTime(Part.ONE));
					page.append(time).append("</span>");

					// Show global indicator and player name
					page.append("&nbsp;");
					if (place + 1 <= puzzle.getGlobalCount()) {
						page.append("<a href=\"https://adventofcode.com/").append(year);
						page.append("/leaderboard/day/").append(day).append("\">");
						page.append("<span class=\"global\" title=\"Top 100 on the daily Global Leaderboard\">*</span></a>");
					}
					else {
						page.append("&nbsp;");
					}
					page.append(maskName(year, record.getName()));

					isNextTie = (place + 1 < places.size() && record.getTime(Part.TWO).equals(places.get(place + 1).getTime(Part.TWO)));
					page.append(isNextTie ? "<br />\n" : "</li>\n");
				}
				// Pad incomplete lists so each Day is the same height for floating.
				for (int place = places.size(); place < novetta.getPlaces(); place++) {
					page.append("<br />");
				}
				page.append("\t</ol>\n");
				page.append("</div>\n");

				// Show console message for most recent time recorded on most recent day, and total number of stars.
				if (!alertShown) {
					PuzzleTime mostRecent = places.get(places.size() - 1);
					StringBuffer alert = new StringBuffer();
					alert.append(year).append(": ").append(puzzleTimes.getStars()).append(" stars\n");
					alert.append("Day ").append(day).append(": ").append(places.size()).append(". ");
					alert.append(PuzzleTime.formatTime(mostRecent.getTime(Part.TWO))).append(" ").append(mostRecent.getName()).append("\n");
					System.out.println(alert.toString());
					alertShown = true;
				}
			}
		}
		page.append("<div class=\"clear\"></div>\n\n");
	}

	/**
	 * Adds the HTML page footer
	 */
	private void insertFooter(String year) {
		StringBuffer page = getPage();
		page.append("\t<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>\n");
		page.append("</body>\n</html>");
	}
}
