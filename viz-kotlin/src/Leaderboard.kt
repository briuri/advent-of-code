import buri.aoc.viz.common.*
import org.junit.Test
import java.util.*

/**
 * Alternate visualization of our private leaderboard showing the Fastest Times for each puzzle. Generated
 * from the API JSON.
 *
 * NOTE: Inactive accounts are purged yearly from our leaderboard to avoid the 200-player cap. Redownloading
 * JSON from older years and regenerating their pages will result in missing scores.
 *
 * @author Brian Uri!
 */

/**
 * Generates the Fastest Times pages on a schedule, for use when I'm not around to do manual updates.
 */
fun main() {
    val minutes = 15
    val reps = 96
    val execPrefix = "cmd /c start /min"
    val downloadScript = "C:\\workspace\\aws-stage\\scripts\\aoc-get-json.bat"
    val uploadScript = "C:\\workspace\\aws-stage\\scripts\\aoc-put-s3.bat"
    val leaderboard = Leaderboard()

    for (i in 0 until reps) {
        if (i > 0) {
            // Wait for next iteration.
            Thread.sleep(minutes.toLong() * 60 * 1000)
        }
        val date = BaseLeaderboard.MODIFIED_DATE_FORMAT.format(Date())
        println("$date Leaderboard Auto-Update #${i + 1} of $reps")
        // Script uses curl to pass session cookie and copy JSON to /data/viz/json.
        val jsonDowload = Runtime.getRuntime().exec("$execPrefix $downloadScript")
        jsonDowload.waitFor()

        // Add extra time (waitFor is insufficient).
        Thread.sleep(2 * 1000)

        // Suppress exceptions to ignore occasional download / parsing errors. Just try again next time.
        try {
            leaderboard.generatePages()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Script uses AWS CLI to upload files to S3 bucket hosting static website.
        val htmlUpload = Runtime.getRuntime().exec("$execPrefix $uploadScript")
        htmlUpload.waitFor()
    }
}

class Leaderboard : BaseLeaderboard() {
    /**
     * Generate the Fastest Times pages via a JUnit test in IDEA.
     */
    @Test
    fun generatePages() {
        for (year in YEARS) {
            visualizeYear(year)
        }
    }

    /**
     * Generates the page for a specific year
     */
    private fun visualizeYear(year: String) {
        val leaderboardJson = readLeaderboards(year)
        val puzzleTimes = getSolveTimes(year, leaderboardJson)
        val playerTimes = getPlayerTimes(year, puzzleTimes)

        // Create the Latest page.
        if (year == CURRENT_YEAR) {
            resetPage()
            insertHeader(year, true)
//2023      insertLatestDay(year, puzzleTimes)
            insertInstructions()
            insertFooter(false)
            writePage("index.html")
        }

        // Create Top X page.
        resetPage()
        insertHeader(year, false)
        if (year == CURRENT_YEAR) {
            page.append("<div class=\"instructions\">\n")
            page.append("See you after hours on November 30, 2023!")
            page.append("</div>")
        }
        else {
            insertTopOverall(year, playerTimes, false)
            insertTopDivisionsChart(year, playerTimes)
            insertTotalSolvesChart(year, puzzleTimes)
            insertTopDaily(year, puzzleTimes, false)
            insertFooter(true)
        }
        writePage("$year-top.html")

        // Create All Players page.
        resetPage()
        insertHeader(year, false)
        insertTopOverall(year, playerTimes, true)
        if (year == CURRENT_YEAR) {
            insertTopDaily(year, puzzleTimes, true)
        }
        insertFooter(true)
        writePage("$year-all.html")
    }

    /**
     * Creates some instructions to get ready for a new season.
     */
    private fun insertInstructions() {
//		page.append("<img src=\"teaser.jpg\" width=\"500\" height=\"174\" title=\"Advent of Code 2022\"/>\n");
        page.append("<div class=\"instructions\">\n")
//      page.append("<p>The 2022 competition is over. See you after hours on November 30, 2023!</p>")
        page.append("\t<h2>See you after hours on November 30, 2023!</h2>\n");
//		page.append("\t<h2>Late to the party?</h2>\n");
		page.append("\t<ol>\n");
        page.append("\t\t<li>Company-wide instructions for joining this year's competition will come out on Friday, November 24.</li>\n");
//		page.append("\t\t<li>Follow the instructions on the <a href=\"https://accenturefederal.servicenowservices.com/help?id=kb_article_view&sys_kb_id=b80e20a31b0fd11030c920efe54bcb3d\">Portal Page</a> (Commercial login).</li>\n");
//		page.append("\t\t<li>Join the <a href=\"https://teams.microsoft.com/l/team/19%3a3iu5nRqsxOjUvC7ZdfaCGw0xlHEUrqu8zvVdmRhG7hw1%40thread.tacv2/conversations?groupId=590beb17-d466-40bd-8631-22f023f9ae69&tenantId=0ee6c63b-4eab-4748-b74a-d1dc22fc1a24\">Advent of Code Teams channel</a> (Commercial login) to chat with other puzzle solvers.</li>\n");
		page.append("\t\t<li>The first puzzle unlocks at midnight Eastern on Dec. 1. This is the night of Nov. 30, <i>not</i> the night of Dec. 1!</li>\n");
		page.append("\t\t<li>Advent of Code is still fun if you don't want to be up at midnight. Do the puzzles later to flex your problem-solving skills or learn a new language!</li>\n");
		page.append("\t</ol>\n");
		page.append("\t<h2>Scoring FAQ</h2>");
		page.append("\t<ul>\n");
		page.append("\t\t<li>Your daily time to complete each two-part puzzle (worth 2 stars) is measured as \"time since the puzzle unlocked at midnight Eastern\".</li>");
		page.append("\t\t<li>The winner in the company competition will have the most stars by <span class=\"bestTime\">11:59 PM on December 31</span>.</li>");
		page.append("\t\t<li>Last year, 15 people earned all 50 stars. Ties are broken by the <i>lowest median daily time</i> (in other words, your 13th fastest daily time out of 25 if you finish every puzzle).</li>\n");
		page.append("\t\t<li>Use this page to track everyone's progress since the official private leaderboard uses a different scoring system.</li>\n");
		page.append("\t</ul>\n");
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
            masked.append(".")
        }
        masked.insert(1, ANTI_INDEX)
        return masked.toString()
    }

    /**
     * Adds the HTML page header
     */
    private fun insertHeader(year: String, isLatestPage: Boolean) {
        page.append("<html>\n<head>\n")
        page.append("\t<meta charset=\"UTF-8\">\n")
        page.append("\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n")
        page.append("\t<title>Advent of Code - Rankings ($year)</title>\n\n")
        if (!isLatestPage) {
            page.append("\t<script type=\"text/javascript\" src=\"https://cdn.plot.ly/plotly-1.58.1.min.js\" charset=\"utf-8\"></script>\n")
        }
        page.append("\t<script type=\"text/javascript\" src=\"jquery-3.5.1.min.js\"></script>\n")
        page.append("\t<script type=\"text/javascript\" src=\"aoc.js?2021-01-02-1615\"></script>\n")
        page.append("\t<link rel=\"icon\" href=\"favicon.ico\" />\n")
        page.append("\t<link type=\"text/css\" rel=\"stylesheet\" href=\"aoc.css?2021-01-02-1615\" />\n")
        page.append("</head>\n\n<body>\n")
        page.append("\t<div class=\"navBar\">\n")
        page.append("\t\t")
        page.append("<a href=\"index.html\">Latest</a> | ")
        for (i in YEARS.indices) {
            page.append("<a href=\"${YEARS[i]}-top.html\">${YEARS[i]}</a>")
            if (i + 1 < YEARS.size) {
                page.append(" | ")
            }
        }
        if (year == CURRENT_YEAR) {
            page.append("<br />\n<a href=\"https://adventofcode.com/$year/leaderboard/private/view/105906\">")
            page.append("Private Leaderboard&rArr;</a> | ")
            page.append("<a href=\"https://teams.microsoft.com/l/team/19%3a3iu5nRqsxOjUvC7ZdfaCGw0xlHEUrqu8zvVdmRhG7hw1%40thread.tacv2/conversations?groupId=590beb17-d466-40bd-8631-22f023f9ae69&tenantId=0ee6c63b-4eab-4748-b74a-d1dc22fc1a24\">Teams&rArr;</a>")
        }
        page.append("\n\t</div>\n\n")
        page.append("\t<h1>Advent of Code - Rankings ($year)</h1>\n\n")
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
        page.append(readLastModified(year))
        page.append("\t<p>${company.rules}</p>\n")
        val linkText = if (showAll) "Top" else "All"
        page.append("<a href=\"$year-${linkText.lowercase()}.html\">")
        page.append("<span class=\"overallLink\">Show $linkText Players</span></a>\n")
        page.append("\t<div class=\"clear\"></div>\n\n<div class=\"overall\">\n")
        page.append("<ol>\n")
        var isNextTie = false
        var tieCount = 0
        val isStandardWidth = !(year == "2016" && showAll)
        val summaryMargin = if (isStandardWidth) 12 else 13
        for (i in 0 until numOverall) {
            val player = playerTimes[i]
            val isIneligible = company.ineligible.contains(player.name)
            val timeClass = if (isIneligible) "ineligible" else "bestTime"

            // Show tiebreaker time.
            val overallTime = SolveTime.formatTime(player.tiebreakerTime, isStandardWidth)
            val hoverText = if (isIneligible) "Not eligible for prizes" else "Show/hide all times"
            page.append(if (isNextTie) "\t" else "\t<li class=\"overallRecord\">")
            page.append("<span class=\"$timeClass bestTimeLink\" id=\"bestTime$i\" ")
            page.append("title=\"$hoverText\">$overallTime</span>&nbsp;&nbsp;")

            // Show player name and division.
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

            // Show summary of medals and global records
            for (j in 0 until summaryMargin) {
                page.append("&nbsp;")
            }
            page.append(player.stars).append("<span class=\"emoji\" title=\"Stars\">&#x2B50;</span> ")
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
                page.append("\t\t\t")
                val time = SolveTime.formatTime(player.times[j], isStandardWidth)
                // 2017+ use median time, so add descriptive text next to the key times.
                if (year != "2016") {
                    // Averaged median
                    if (totalTimes % 2 == 0 && j == totalTimes / 2 - 1) {
                        page.append("<span class=\"bestTime\">$time</span>")
                        page.append("&nbsp;&nbsp;average is")
                    } else if (j == totalTimes / 2) {
                        page.append("<span class=\"bestTime\">$time</span>")
                        page.append("&nbsp;&nbsp;current median")
                    } else if (j == 12) {
                        page.append("<span class=\"bestTime\">$time</span>")
                        page.append("&nbsp;&nbsp;13th fastest median")
                    } else {
                        page.append(time)
                    }
                } else {
                    page.append(time)
                }
                page.append("<br />\n")
            }
            page.append("\t\t</div>\n")
            isNextTie = (i + 1 < numOverall) && (player.tiebreakerTime == playerTimes[i + 1].tiebreakerTime)
            if (isNextTie) {
                tieCount++
            }
            page.append(if (isNextTie) "\t<br />\n" else "\t</li>\n")

            // Break overall scores into two columns for > Top 10.
            if (numOverall > 10 && i == (numOverall + 1) / 2 - 1) {
                page.append("</ol>\n</div>\n<div class=\"overall\">\n")
                page.append("<ol start=\"${i + 2 - tieCount}\">\n")
            }
        }
        page.append("</ol></div>\n<div class=\"clear\"></div>\n")
    }

    /**
     * Adds the Top X Overall by Division section.
     */
    private fun insertTopDivisionsChart(year: String, playerTimes: List<PlayerTimes>) {
        val company = companies[year]!!
        val numOverall = company.maxPlaces.coerceAtMost(playerTimes.size)
        if (numOverall == 0 || company.allDivisions.isEmpty()) {
            return
        }
        val counts = mutableMapOf<String, Int>()
        for (division in company.allDivisions.sorted()) {
            counts[division] = 0
        }
        for (i in 0 until numOverall) {
            val division = company.getDivisionFor(playerTimes[i].name, false)
            if (division.isNotEmpty()) {
                counts[division] = counts[division]!! + 1
            }
        }

        // Calculate max for chart height.
        val max = counts.values.max()
        page.append("\n\t<a name=\"division\"></a><h2>Top $numOverall Overall by ${company.divisionLabel}</h2>\n")
        page.append(readLastModified(year))
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
        page.append("\n\t<a name=\"total\"></a><h2>Total Solves by Day (${puzzleTimes.stars} stars)</h2>\n")
        page.append(readLastModified(year))
        page.append("\t<div id=\"chartParticipation\"></div>\n")
        page.append("\t<script type=\"text/javascript\">\n")
        page.append("\t\tvar xValues = [")
        for (i in 0 until Puzzle.TOTAL_PUZZLES) {
            page.append(i + 1)
            if (i + 1 < Puzzle.TOTAL_PUZZLES) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yPart1 = [")
        for (i in 0 until Puzzle.TOTAL_PUZZLES) {
            page.append(puzzleTimes.getCount(TimeType.ONE, i))
            if (i + 1 < Puzzle.TOTAL_PUZZLES) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yPart2 = [")
        for (i in 0 until Puzzle.TOTAL_PUZZLES) {
            val split = puzzleTimes.getCount(TimeType.TOTAL, i) - puzzleTimes.getCount(TimeType.ONE, i)
            page.append(split)
            if (i + 1 < Puzzle.TOTAL_PUZZLES) {
                page.append(",")
            }
        }
        page.append("];\n")
        page.append("\t\tvar yTotal = [")
        for (i in 0 until Puzzle.TOTAL_PUZZLES) {
            page.append(puzzleTimes.getCount(TimeType.TOTAL, i))
            if (i + 1 < Puzzle.TOTAL_PUZZLES) {
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
    private fun insertTopDaily(year: String, puzzleTimes: PuzzleTimes, showAll: Boolean) {
        if (showAll) {
            page.append("\t<h2>All Players Daily</h2>\n")
        } else {
            page.append("\n\t<h2>Top ${companies[year]!!.maxPlaces} Daily</h2>\n")
        }
        page.append(readLastModified(year))
        page.append("\t<p>Rank is based on time to complete both puzzle parts after midnight release.</p>\n")
        insertSplitToggle()
        page.append("\t<div class=\"clear\"></div>\n\n")
        for (i in Puzzle.TOTAL_PUZZLES - 1 downTo 0) {
            val places = puzzleTimes.getTimes(TimeType.TOTAL)[i]
            if (places.isNotEmpty()) {
                insertDay(year, i + 1, places, showAll)
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
        page.append("<div class=\"daily\">\n")
        page.append("<span class=\"rankingsLink\">")
        page.append("<a href=\"$CURRENT_YEAR-top.html\">See Overall Rankings</a></span>")
        page.append("\t<h2>Latest Puzzle</h2>\n")
        page.append(readLastModified(year))
        insertSplitToggle()
        for (i in Puzzle.TOTAL_PUZZLES - 1 downTo 0) {
            val places = mutableListOf<SolveTime>()
            places.addAll(puzzleTimes.getTimes(TimeType.TOTAL)[i])
            if (places.isNotEmpty()) {
                // Show console message for most recent total solve on most recent day, and total number of stars.
                val mostRecent = places[places.size - 1]
                val time = SolveTime.formatTime(mostRecent.getTime(TimeType.TOTAL), true)
                val alert = "\t(${puzzleTimes.stars} stars) - Day ${i + 1}: ${places.size}." +
                        time.replace("&nbsp;", " ") + " ${mostRecent.name}\n"
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
        var isNextTie = false
        val maxPlaces = if (showAll) places.size else company.maxPlaces.coerceAtMost(places.size)
        val bestPart1 = getFastestSplitTime(places, maxPlaces, TimeType.ONE)
        val bestPart2 = getFastestSplitTime(places, maxPlaces, TimeType.TWO)
        for (place in 0 until maxPlaces) {
            val record = places[place]
            if (isNextTie) {
                page.append("\t\t")
            } else {
                page.append("\t\t<li value=\"${place + 1}\">")
            }

            // Show total time and split times
            page.append("<span class=\"dS\">")
            insertSplitTime(record.getTime(TimeType.ONE), bestPart1)
            page.append("&nbsp;")
            insertSplitTime(record.getTime(TimeType.TWO), bestPart2)
            page.append("</span>")
            val totalTime: Long? = record.getTime(TimeType.TOTAL)
            page.append("<span class=\"dT\">").append(SolveTime.formatTime(totalTime, true))
            page.append("</span>")

            // Show global indicator and player name
            page.append("&nbsp;")
            if (puzzle.globalNames.contains(record.name)) {
                page.append("<a href=\"https://adventofcode.com/$year/leaderboard/day/$day\">")
                page.append("<span class=\"global\" title=\"Top 100 on daily Global Leaderboard\">*</span></a>")
            } else {
                page.append("&nbsp;")
            }
            page.append(maskName(record.name))
            val nextTime = if (place + 1 < places.size) places[place + 1].getTime(TimeType.TOTAL) else null
            isNextTie = (totalTime != null) && (totalTime == nextTime)
            page.append(if (isNextTie) "<br />\n" else "</li>\n")
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
}