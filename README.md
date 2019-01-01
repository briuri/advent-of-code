# Advent of Code

This project contains my Advent of Code solutions, written in Java. 2018 was the first year I participated in real-time, although I have been working through the older years as time permits.

The top-level of the project can be imported as an Eclipse project. Solutions are found under `/src/<year>/` with a package naming convention of `buri.aoc.y<yy>.d<dd>`. The JUnit test harness is colocated in the same source folder as the solution, to reduce context-switch / navigation overhead when competing.

These solutions all got the right answer for the puzzles, although I cannot guarantee that they are the optimal solutions. In particular:

* **y17d07** makes some assumptions about the structure described in the input to reach the correct answer.
* **y18d23** uses a sampling approach that returns different answers based on the chunk size and resizing size used in the solution (the right answer was returned when I ran it though!).

These solutions use a naive solution that takes a long time to run, and could probably be replaced with a better algorithm. If I ever get bored, I'll go back and re-implement with the algorithms that appear in the Reddit solution megathreads:

* **y16d14**
* **y16d19**
* **y17d17**
* **y18d11**
* **y18d21** 

In addition to the solutions, this project also contains a simple visualization of the leaderboard JSON (under `src/viz`) that can show the data as a Top 10 Fastest Times list for each puzzle.

While I am making this source code available for public viewing, I am not releasing it under any sort of software license at this time.