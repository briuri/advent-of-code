import buri.aoc.viz.common.*
import org.junit.Test
import java.util.*

/**
 * Alternate visualization of our private leaderboard showing the Rankings for each puzzle. Generated
 * from the API JSON.
 *
 * NOTE: Inactive accounts are purged yearly from our private leaderboard to avoid the 200-player cap.
 * Redownloading JSON from older years and regenerating their pages will result in missing scores.
 *
 * @author Brian Uri!
 */

/**
 * Generates the Rankings pages on a schedule, for use when I'm not around to do manual updates.
 */
fun main() {
    val minutes = 15
    val reps = 96
    val execPrefix = "cmd /c start /min"
    val downloadScript = "C:\\workspace\\aws-stage\\scripts\\aoc-getJson.bat"
    val uploadScript = "C:\\workspace\\aws-stage\\scripts\\aoc-putViz.bat"
    val rankingsPages = mutableMapOf<String, RankingsPage>()
    for (id in BaseRankingsPage.IDS) {
        rankingsPages[id] = RankingsPage("company-$id.json") 
    }
            
    for (i in 0 until reps) {
        if (i > 0) {
            // Wait for next iteration.
            Thread.sleep(minutes.toLong() * 60 * 1000)
        }
        val date = BaseRankingsPage.MODIFIED_DATE_FORMAT.format(Date())
        println("$date Rankings Update #${i + 1} of $reps")
        // Script uses curl to pass session cookie and copy JSON to /data/viz/json.
        val jsonDowload = Runtime.getRuntime().exec("$execPrefix $downloadScript")
        jsonDowload.waitFor()

        // Add extra time (waitFor is insufficient).
        Thread.sleep(2 * 1000)

        // Suppress exceptions to ignore occasional download / parsing errors. Just try again next time.
        try {
            rankingsPages.forEach { (id, page) -> 
                page.backupJson(id)
                page.generatePages()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Script uses AWS CLI to upload files to S3 bucket hosting static website.
        val htmlUpload = Runtime.getRuntime().exec("$execPrefix $uploadScript")
        htmlUpload.waitFor()
    }
}

/**
 * Generate the Rankings pages (without download/upload) via a JUnit test in IDEA.
 */
class RankingsPageTest {
    @Test
    fun generatePages() {
        for (id in BaseRankingsPage.IDS) {
            val rankingsPage = RankingsPage("company-$id.json")
            rankingsPage.generatePages()
        }
    }
}

class RankingsPage(companyMetadata: String) : BaseRankingsPage(companyMetadata) {

    /**
     * Generate the Rankings pages but do not download / upload any data.
     */
    fun generatePages() {
        for (year in years) {
            visualizeYear(year)
        }
    }
    
    /**
     * Generates the page for a specific year
     */
    private fun visualizeYear(year: String) {
        val company = companies[year]!!
        
        // Always clear current year cache before generating.
        if (year == CURRENT_YEAR) {
            readTimeData(year)
        }
        val puzzleTimes = puzzleTimes[year]!!
        val playerTimes = playerTimes[year]!!

        // Create the Latest page.
        if (year == CURRENT_YEAR) {
            resetPage()
            insertHeader(year, true)
            insertLatestDay(year, puzzleTimes)
            insertInstructions(year)
            insertFooter(false)
            writePage(company.id, "index.html")
        }

        // Create Top X page.
        resetPage()
        insertHeader(year, false)
        insertTopOverall(year, playerTimes, false)
        if (year != CURRENT_YEAR) {
            insertTopDivisionsChart(year, playerTimes)
        }
        insertTotalSolvesChart(year, puzzleTimes)
        insertTopDaily(year, puzzleTimes)
        insertFooter(true)
        writePage(company.id, "$year-top.html")

        // Create All Players page.
        resetPage()
        insertHeader(year, false)
        insertTopOverall(year, playerTimes, true)
        insertFooter(true)
        writePage(company.id, "$year-all.html")
    }

    /**
     * Creates some instructions to get ready for a new season.
     */
    private fun insertInstructions(year: String) {
        val company = companies[year]!!
        
        page.append("\t<div class=\"instructions\">\n")
        page.append("\t<h2>The 2026 competition is just around the corner!</h2>\n")
//        page.append(company.instructions)
        page.append(company.scoringFAQ)
        page.append("</div>\n")
    }

    /**
     * Obfuscates name to deter robots.
     */
    private fun maskName(name: String): String {
        val masked = StringBuilder(name)
        val index = masked.indexOf(" ")
        val maskPoint = 4
        if (index != -1 && index + maskPoint < masked.length) {
            val truncate = index + maskPoint
            val length = masked.length
            masked.delete(truncate, length)
            // Handle multiword last names with spaces at the truncate point (e.g., "de Salvo")
            if (masked.endsWith(' ')) {
                masked.deleteAt(masked.lastIndex)
            }
            masked.append(".")
        }
        masked.insert(1, ANTI_INDEX)
        return masked.toString()
    }

    /**
     * Adds the HTML page header
     */
    private fun insertHeader(year: String, isLatestPage: Boolean) {
        val company = companies[year]!!        
        val title = if (isLatestPage) "Latest Puzzle" else "Rankings"

        page.append("<html>\n<head>\n")
        page.append("\t<meta charset=\"UTF-8\">\n")
        page.append("\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n")
        page.append("\t<title>Advent of Code - $title ($year)</title>\n\n")
        if (!isLatestPage) {
            page.append("\t<script type=\"text/javascript\" src=\"https://cdn.plot.ly/plotly-1.58.1.min.js\" charset=\"utf-8\"></script>\n")
        }
        page.append("\t<script type=\"text/javascript\" src=\"jquery-3.5.1.min.js\"></script>\n")
        page.append("\t<script type=\"text/javascript\" src=\"aoc.js?2023-11-27-1934\"></script>\n")
        page.append("\t<link rel=\"icon\" href=\"favicon.ico\" />\n")
        page.append("\t<link type=\"text/css\" rel=\"stylesheet\" href=\"aoc.css?2023-11-27-1934\" />\n")
        page.append("</head>\n\n<body>\n")
        page.append("\t<div class=\"navBar\">\n")
        page.append("\t\t")
        page.append("${years[0]}: <a href=\"index.html\">Latest</a> | <a href=\"${years[0]}-top.html\">Rankings</a>")
        page.append(" | <a href=\"https://adventofcode.com/${years[0]}/leaderboard/private/view/${company.leaderboard}\">")
        page.append("Raw Stats&rArr;</a>")
        if (years.drop(1).isNotEmpty()) {
            page.append("<br />\n\t\tArchives: ")
            for (i in years.indices.drop(1)) {
                page.append("<a href=\"${years[i]}-top.html\">${years[i]}</a>")
                if (i + 1 < years.size) {
                    page.append(" | ")
                }
            }
        }
        page.append("\n\t</div>\n\n")
        page.append("\t<h1>Advent of Code - $title ($year)</h1>\n\n")
    }

    /**
     * Adds the Top X Overall section.
     */
    private fun insertTopOverall(year: String, playerTimes: List<PlayerTimes>, showAll: Boolean) {
        val company = companies[year]!!
        val numOverall = if (showAll) playerTimes.size else company.maxPlaces.coerceAtMost(playerTimes.size)
        if (numOverall == 0) {
            return
        }
        if (showAll) {
            page.append("\t<h2>All Players</h2>\n")
        } else {
            page.append("\t<h2>Top $numOverall Overall</h2>\n")
        }
        page.append(readLastModified(company.id, year))
        page.append("\t<p>${company.rules}</p>\n")
        val linkText = if (showAll) "Top" else "All"

        // Show Top/All toggle.
        page.append("<a href=\"$year-${linkText.lowercase()}.html\">")
        page.append("<span class=\"overallLink\">Show $linkText Players</span></a>\n")
        page.append("\t<div class=\"clear\"></div>\n\n<div class=\"overall\">\n")
        page.append("<ol>\n")

        // Show each player's complete record.
        val ranks =
            getRanks(year, playerTimes.map { it.tiebreakerTime }, playerTimes.map { it.name in company.ineligible })
        val ineligibleCount = playerTimes.subList(0, numOverall).count { it.name in company.ineligible }
        val isStandardWidth = !(company.scoring.type != ScoringType.MEDIAN && showAll)
        val summaryMargin = if (isStandardWidth) 18 else 19
        for (i in 0 until playerTimes.size.coerceAtMost(numOverall + ineligibleCount)) {
            val player = playerTimes[i]
            val isIneligible = company.ineligible.contains(player.name)
            val timeClass = if (isIneligible) "ineligible" else "bestTime"
            val hoverText = if (isIneligible) "Not eligible for prizes" else "Show/hide all times"

            // Show total stars.
            val starMargin = if (player.stars < 10) "$SPACE$SPACE$SPACE" else "$SPACE$SPACE"
            if (ranks[i] == 0) {
                page.append("\t<li class=\"overallRecord noRank\">")
            } else {
                page.append("\t<li class=\"overallRecord\" value=\"${ranks[i]}\">")
            }
            page.append(starMargin).append(player.stars)
                .append("<span class=\"emoji\" title=\"Stars\">&#x2B50;</span> ")

            // Show tiebreaker time.
            val tiebreakerTime = SolveTime.formatTime(player.tiebreakerTime, isStandardWidth)
            page.append("<span class=\"$timeClass bestTimeLink\" id=\"bestTime$i\" ")
            page.append("title=\"$hoverText\">$tiebreakerTime</span>$SPACE$SPACE")

            // Show player name and division/portfolio.
            if (isIneligible) {
                page.append("<span class=\"ineligible\" title=\"Not eligible for prizes\">")
            }
            page.append(maskName(player.name))
            if (!showAll) {
                page.append(company.getDivisionFor(player.name, true))
            }
            if (isIneligible) {
                page.append("</span>")
            }
            page.append("<br />\n\t\t")

            // On next line, show summary of medals global records
            for (j in 0 until summaryMargin) {
                page.append(SPACE)
            }
            if (player.hasMedals()) {
                page.append("${player.first}<span class=\"emoji\" title=\"1st Place\">&#x1F947;</span> ")
                page.append("${player.second}<span class=\"emoji\" title=\"2nd Place\">&#x1F948;</span> ")
                page.append("${player.third}<span class=\"emoji\" title=\"3rd Place\">&#x1F949;</span> ")
            }
            val globalCount = company.getGlobalCountFor(player.name)
            if (globalCount > 0) {
                page.append(globalCount).append("<span class=\"emoji\" title=\"Global Leaderboard\">&#x1F30E;</span> ")
            }

            // Show expandable DIV containing all the raw times used to generate the tiebreaker time.
            page.append("\n\t\t<div class=\"details\" id=\"details$i\">\n")
            val totalTimes = player.times.size
            for (j in 0 until totalTimes) {
                val dayPadding = if (player.times[j].day < 10) "0" else ""
                val dayPartial = if (player.times[j].isTotal) "$SPACE$SPACE" else "&#189;$SPACE"
                page.append("\t\t\t<span class=\"dayLabel\">${dayPartial}d$dayPadding${player.times[j].day}</span>")
                page.append(SPACE).append(SPACE)
                val time = SolveTime.formatTime(player.times[j].time, isStandardWidth)
                // 2017-2025 use median time, so add descriptive text next to the key times.
                if (company.scoring.type == ScoringType.MEDIAN) {
                    // Averaged median
                    if (totalTimes % 2 == 0 && j == totalTimes / 2 - 1) {
                        page.append("<span class=\"bestTime\">$time</span>").append(SPACE)
                        page.append(SPACE).append("&#x2190; average is")
                    } else if (totalTimes % 2 == 0 && j == totalTimes / 2) {
                        page.append("<span class=\"bestTime\">$time</span>").append(SPACE)
                        page.append(SPACE).append("&#x2190; current median")
                    } else if (j == totalTimes / 2) {
                        page.append("<span class=\"bestTime\">$time</span>").append(SPACE)
                        page.append(SPACE).append("current median")
                    } else if (j == 12) {
                        page.append("<span class=\"bestTime\">$time</span>").append(SPACE)
                        page.append("${SPACE}13th fastest")
                    } else {
                        page.append(time)
                    }
                }
                // 2026 use sum time and drop some arbitrary number of slowest times
                else if (company.scoring.isDropped(j, totalTimes)) {
                    page.append("<span class=\"worstTime\">$time</span>").append(SPACE)
                    page.append("${SPACE}<span class=\"worstTime\">dropped</span>")
                } 
                else {
                    page.append(time)
                }
                page.append("<br />\n")
            }
            page.append("\t\t</div>\n")
            page.append("\t</li>\n")

            // Break overall scores into two columns for > Top 10.
            if (numOverall > 10 && i == (numOverall + 1) / 2 - 1) {
                page.append("</ol>\n</div>\n<div class=\"overall\">\n")
                page.append("<ol>\n")
            }
        }
        page.append("</ol></div>\n<div class=\"clear\"></div>\n")
    }

    /**
     * Adds the Top X Overall by Division section.
     */
    private fun insertTopDivisionsChart(year: String, playerTimes: List<PlayerTimes>) {
        val company = companies[year]!!
        val eligiblePlayers = playerTimes.filter { it.name !in company.ineligible }
        val numOverall = company.maxPlaces.coerceAtMost(eligiblePlayers.size)
        if (numOverall == 0 || company.allDivisions.isEmpty()) {
            return
        }
        val counts = mutableMapOf<String, Int>()
        for (division in company.allDivisions) {
            counts[division] = 0
        }
        for (i in 0 until numOverall) {
            val division = company.getDivisionFor(eligiblePlayers[i].name, false)
            if (division.isNotEmpty()) {
                counts[division] = counts[division]!! + 1
            }
        }

        // Calculate max for chart height.
        val max = counts.values.max()
        page.append("\n\t<a name=\"division\"></a><h2>Top $numOverall Overall by ${company.divisionLabel}</h2>\n")
        page.append(readLastModified(company.id, year))
        page.append("\t<div id=\"chartDivisions\"></div>\n")
        page.append("\t<script type=\"text/javascript\">\n")
        page.append("\t\tvar xValues = [")
        var iter = counts.keys.iterator()
        while (iter.hasNext()) {
            page.append("'").append(iter.next()).append("'")
            if (iter.hasNext()) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yValues = [")
        iter = counts.keys.iterator()
        while (iter.hasNext()) {
            page.append(counts[iter.next()])
            if (iter.hasNext()) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar dataDivisions = [{\n")
        page.append("\t\t\tx: xValues,\n")
        page.append("\t\t\ty: yValues,\n")
        page.append("\t\t\tmarker: { color: '#006eb7' },\n")
        page.append("\t\t\ttext: yValues.map(String),\n")
        page.append("\t\t\ttextposition: 'outside',\n")
        page.append("\t\t\ttype: 'bar'\n")
        page.append("\t\t}];\n")
        page.append("\t\tvar layout = {\n")
        page.append("\t\t\tfont: { family: 'monospace', color: '#cccccc' },\n")
        page.append("\t\t\tpaper_bgcolor: '#0f0f23',\n")
        page.append("\t\t\tplot_bgcolor: '#0f0f23',\n")
        page.append("\t\t\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n")
        page.append("\t\t\tyaxis: {range: [0, ${max + 2}]}\n")
        page.append("\t\t};\n")
        page.append("\t\tvar options = {displayModeBar: false, responsive: true, staticPlot: true}\n")
        page.append("\t\tPlotly.newPlot('chartDivisions', dataDivisions, layout, options);\n")
        page.append("\t</script>\n")
    }

    /**
     * Adds the Total Solves chart.
     */
    private fun insertTotalSolvesChart(year: String, puzzleTimes: PuzzleTimes) {
        val company = companies[year]!!
        page.append("\n\t<a name=\"total\"></a><h2>Total Solves by Day (${puzzleTimes.stars} stars)</h2>\n")
        page.append(readLastModified(company.id, year))
        page.append("\t<div id=\"chartParticipation\"></div>\n")
        page.append("\t<script type=\"text/javascript\">\n")
        page.append("\t\tvar xValues = [")
        for (i in 0 until Puzzle.getTotalPuzzles(year)) {
            page.append(i + 1)
            if (i + 1 < Puzzle.getTotalPuzzles(year)) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yPart1 = [")
        for (i in 0 until Puzzle.getTotalPuzzles(year)) {
            page.append(puzzleTimes.getCount(TimeType.ONE, i))
            if (i + 1 < Puzzle.getTotalPuzzles(year)) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yPart2 = [")
        for (i in 0 until Puzzle.getTotalPuzzles(year)) {
            val split = puzzleTimes.getCount(TimeType.TOTAL, i) - puzzleTimes.getCount(TimeType.ONE, i)
            page.append(split)
            if (i + 1 < Puzzle.getTotalPuzzles(year)) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yTotal = [")
        for (i in 0 until Puzzle.getTotalPuzzles(year)) {
            page.append(puzzleTimes.getCount(TimeType.TOTAL, i))
            if (i + 1 < Puzzle.getTotalPuzzles(year)) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar trace1 = {\n")
        page.append("\t\t\tx: xValues,\n")
        page.append("\t\t\ty: yPart1,\n")
        page.append("\t\t\tmarker: { color: '#9999cc' },\n")
        page.append("\t\t\tname: 'Part 1',\n")
        page.append("\t\t\ttype: 'bar'\n")
        page.append("\t\t};\n")
        page.append("\t\tvar trace2 = {\n")
        page.append("\t\t\tx: xValues,\n")
        page.append("\t\t\ty: yPart2,\n")
        page.append("\t\t\tmarker: { color: '#ffff66' },\n")
        page.append("\t\t\tname: 'Part 2',\n")
        page.append("\t\t\ttext: yTotal.map(String),\n")
        page.append("\t\t\ttextposition: 'outside',\n")
        page.append("\t\t\ttype: 'bar'\n")
        page.append("\t\t};\n")
        page.append("\t\tvar dataParticipation = [trace1, trace2];\n")
        page.append("\t\tvar layout = {\n")
        page.append("\t\t\tbarmode: 'stack',\n")
        page.append("\t\t\tfont: { family: 'monospace', color: '#cccccc' },\n")
        page.append("\t\t\tlegend: { x: 1, xanchor: 'right', y: 1 },\n")
        page.append("\t\t\tmargin: { t: 32, r: 32, b: 75, l: 32 },\n")
        page.append("\t\t\tpaper_bgcolor: '#0f0f23',\n")
        page.append("\t\t\tplot_bgcolor: '#0f0f23',\n")
        page.append("\t\t};\n")
        page.append("\t\tvar options = {displayModeBar: false, responsive: true, staticPlot: true}\n")
        page.append("\t\tPlotly.newPlot('chartParticipation', dataParticipation, layout, options);\n")
        page.append("\t</script>\n\n")
        page.append("\t<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>\n\n")
    }

    /**
     * Adds the Top X Daily for each puzzle.
     */
    private fun insertTopDaily(year: String, puzzleTimes: PuzzleTimes) {
        val company = companies[year]!!        
        page.append("\n\t<h2>Top ${company.maxPlaces} Daily</h2>\n")
        page.append(readLastModified(company.id, year))
        page.append("\t<p>Rank is based on time to complete both puzzle parts after midnight release.</p>\n")
        insertSplitToggle()
        page.append("\t<div class=\"clear\"></div>\n\n")
        for (i in Puzzle.getTotalPuzzles(year) - 1 downTo 0) {
            val places = mutableListOf<SolveTime>()
            places.addAll(puzzleTimes.getTimes(TimeType.TOTAL)[i])
            places.addAll(puzzleTimes.getTimes(TimeType.ONE)[i])
            if (places.isNotEmpty()) {
                insertDay(year, i + 1, places, false)
            }
        }
        page.append("<div class=\"clear\"></div>\n\n")
    }

    /**
     * Adds a button toggle between split and total times.
     */
    private fun insertSplitToggle() {
        page.append("\t<p><a href=\"javascript:void(0);\">\n")
        page.append("\t\t<span id=\"dailySplit\" class=\"dT dailyLink\">Show Split Times</span>")
        page.append("<span class=\"dS dailyLink\">Show Total Times</span>\n")
        page.append("\t</a></p>\n")
    }

    /**
     * Adds a fast-loading dashboard showing just the most recent puzzle.
     */
    private fun insertLatestDay(year: String, puzzleTimes: PuzzleTimes) {
        val company = companies[year]!!
        page.append("<div class=\"daily\">\n")
        page.append("<span class=\"rankingsLink\">")
        page.append("<a href=\"$CURRENT_YEAR-top.html\">See Overall Rankings</a></span>")
        page.append("\t<h2>Latest Puzzle</h2>\n")
        page.append(readLastModified(company.id, year))
        insertSplitToggle()
        for (i in Puzzle.getTotalPuzzles(year) - 1 downTo 0) {
            val places = mutableListOf<SolveTime>()
            places.addAll(puzzleTimes.getTimes(TimeType.TOTAL)[i])
            if (places.isNotEmpty()) {
                // Show console message for most recent total solve on most recent day, and total number of stars.
                val mostRecent = places[places.size - 1]
                val time = SolveTime.formatTime(mostRecent.getTime(TimeType.TOTAL), true)
                val alert = "  (${company.id} ${puzzleTimes.stars}*) - Day ${i + 1}: ${places.size}." +
                        time.replace(SPACE, " ") + " ${mostRecent.name}"
                println(alert)
            }
            places.addAll(puzzleTimes.getTimes(TimeType.ONE)[i])
            if (places.isNotEmpty()) {
                insertDay(year, i + 1, places, true)
                break
            }
        }
        page.append("</div>\n")
    }

    /**
     * Adds the entry for a single day's puzzle.
     */
    private fun insertDay(year: String, day: Int, places: List<SolveTime>, showAll: Boolean) {
        val company = companies[year]!!
        val puzzle = allPuzzles[year]!![day - 1]
        page.append("<div class=\"daily\">\n")
        page.append("\t<a name=\"day$day\"></a>")
        page.append("<h3><a href=\"https://adventofcode.com/$year/day/$day\">${puzzle.title}</a></h3>\n")
        page.append("\t<ol>\n")
        val maxPlaces = if (showAll) places.size else company.maxPlaces.coerceAtMost(places.size)
        val bestPart1 = getFastestSplitTime(places, maxPlaces, TimeType.ONE)
        val bestPart2 = getFastestSplitTime(places, maxPlaces, TimeType.TWO)
        val ranks = getRanks(year, places.map { it.getTime(TimeType.TOTAL) })
        for (i in 0 until maxPlaces) {
            val record = places[i]
            if (ranks[i] == 0) {
                page.append("\t\t<li class=\"noRank\">")
            } else {
                page.append("\t\t<li value=\"${ranks[i]}\">")
            }

            // Show total time and split times
            page.append("<span class=\"dS\">")
            insertSplitTime(record.getTime(TimeType.ONE), bestPart1)
            page.append(SPACE)
            insertSplitTime(record.getTime(TimeType.TWO), bestPart2)
            page.append("</span>")
            val totalTime: Long? = record.getTime(TimeType.TOTAL)
            page.append("<span class=\"dT\">").append(SolveTime.formatTime(totalTime, true))
            page.append("</span>")

            // Show global indicator and player name
            page.append(SPACE)
            if (puzzle.globalNames.contains(record.name)) {
                page.append("<a href=\"https://adventofcode.com/$year/leaderboard/day/$day\">")
                page.append("<span class=\"global\" title=\"Top 100 on daily Global Leaderboard\">*</span></a>")
            } else {
                page.append(SPACE)
            }
            page.append(maskName(record.name))
            page.append("</li>\n")
        }
        // Pad incomplete lists so each Day is the same height for floating DIVs.
        if (!showAll) {
            for (place in places.size until company.maxPlaces) {
                page.append("<br />")
            }
        }
        page.append("\t</ol>\n")
        page.append("</div>\n")
    }

    /**
     * Records a split time, highlighting it if it's the fastest.
     */
    private fun insertSplitTime(splitTime: Long?, fastestTime: Long) {
        if (splitTime != null && splitTime == fastestTime) {
            page.append("<span class=\"bestTime\">")
        }
        page.append(SolveTime.formatTime(splitTime, true))
        if (splitTime != null && splitTime == fastestTime) {
            page.append("</span>")
        }
    }

    /**
     * Adds the HTML page footer
     */
    private fun insertFooter(showJumpLink: Boolean) {
        if (showJumpLink) {
            page.append("\t<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>\n")
        }
        page.append("<div class=\"clear disclaimer\">This rankings page is a volunteer project that is not endorsed or supported by our company.</div>")
        page.append("</body>\n</html>")
    }

    /**
     * Calculates the ranks of each solve time, starting with 1 and going up. Ineligible players and ties have a rank of 0.
     */
    private fun getRanks(year: String, times: List<Long?>, ineligibles: List<Boolean> = listOf()): List<Int> {
        val ranks = mutableListOf<Int>()
        var currentRank = 1
        for (i in times.indices) {
            if (ineligibles.isNotEmpty() && ineligibles[i]) {
                ranks.add(0)
                continue
            }
            if (i > 0) {
                val previousTime = times[i - 1]
                val currentTime = times[i]
                if (previousTime != null && currentTime != null) {
                    if (previousTime == currentTime && (ineligibles.isEmpty() || !ineligibles[i - 1])) {
                        ranks.add(0)
                        currentRank++
                        continue
                    }
                }
            }
            ranks.add(currentRank)
            currentRank++
        }
        return ranks
    }
}