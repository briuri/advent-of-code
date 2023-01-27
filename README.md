# Advent of Code

This project contains my Advent of Code solutions. I participated in real-time for the 2018-2022 competitions (in Java).
I also went back and did 2015-2017 in Java for completeness.

The top-level of the project can be imported as an IntelliJ IDEA project. Solutions are found under 
`puzzles-<language>/src/` with a package naming convention of `buri.aoc.y<yy>.d<dd>`. Each Day can be run from a JUnit 
test harness, colocated in the day's `Puzzle` class (this reduces the overhead of switching over to a TestCase file in 
`/src/test/` when competing).

## Test Suite Run Time

* **2015**: 00:40 (Java)
* **2016**: 01:00 (Java)
* **2017**: 00:30 (Java)
* **2018**: 00:40 (Java)
* **2019**: 00:15 (Java)
* **2020**: 00:25 (Java)
* **2021**: 01:25 (Java)
* **2022**: 00:40 (Java)

## Leaderboard Visualization

In addition to puzzle solutions, this project also contains a simple visualization (under `viz-java/src`) of the 
private leaderboard JSON that can show the data as a median-based [Rankings page](http://aoc.urizone.net).

## License

While I am making this source code available for public viewing, I am not releasing it for reuse or modification at 
this time.