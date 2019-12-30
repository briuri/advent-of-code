# Advent of Code

This project contains my Advent of Code solutions, written in Java. 
I participated in real-time for 2018-2019, and also did 2015-2017 as practice.

The top-level of the project can be imported as an Eclipse project. 
Solutions are found under `/src/<year>/` with a package naming convention of `buri.aoc.y<yy>.d<dd>`. 
Each Day can be run from a JUnit test harness, colocated in the same source folder as the solution 
(this reduces the overhead of navigating out to `/src/test/` when competing).
 
## Test Suite Run Time

* **2015**: 00:45
* **2016**: 01:05
* **2017**: 02:20 
* **2018**: 18:10
* **2019**: 00:15

This solution use a naive solution that takes a long time to run, and could probably be replaced with a better algorithm:
* **y18d11**: 04:55

## Leaderboard Visualization
 
In addition to puzzle solutions, this project also contains a simple visualization (under `src/viz`) of the private leaderboard JSON 
that can show the data as a [Top 10 Solve Times page](http://aoc.urizone.net).

## License

While I am making this source code available for public viewing, I am not releasing it for reuse or modification at this time.