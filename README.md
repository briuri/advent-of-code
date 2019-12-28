# Advent of Code

This project contains my Advent of Code solutions, written in Java. 2018 was the first year I participated in real-time, although I completed the older years as practice.

The top-level of the project can be imported as an Eclipse project. Solutions are found under `/src/<year>/` with a package naming convention of `buri.aoc.y<yy>.d<dd>`. 
The JUnit test harness is colocated in the same source folder as the solution, to reduce context-switch / navigation overhead when competing.

Total Run Time on Test Suites:

* **2015**: 00:49
* **2016**: 01:02
* **2017**: 02:30 
* **2018**: 24:51
* **2019**: 00:14

These solutions use a naive solution that takes a long time to run, and could probably be replaced with a better algorithm:

* **y18d11**: 04:53
* **y18d21**: 09:30
 
In addition to the solutions, this project also contains a simple visualization of the leaderboard JSON (under `src/viz`) that can show the data as a Top 10 Solve Times list for each puzzle.

While I am making this source code available for public viewing, I am not releasing it under any sort of software license at this time.