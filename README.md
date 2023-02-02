# Advent of Code

This project contains my Advent of Code solutions. 

I've solved every puzzle from 2015-2022 in Java (2018-2022 were done in real-time during the midnight competitions).
I'm currently going back through and solving older puzzles again as a way to learn Kotlin.

The top-level of the project can be imported as an IntelliJ IDEA project. Solutions are found under 
`puzzles-<language>/src/` with a package naming convention of `buri.aoc.y<yy>.d<dd>`. Each Day can be run from a JUnit 
test harness, colocated in the day's `Puzzle` class (this avoids the context-switching cost of using a `TestCase` file
under `/src/test/` when competing).

## Test Suite Run Time

| Year     | Java  | Kotlin |
|----------|-------|--------|
| **2015** | 00:40 | 00:10  |
| **2016** | 01:00 |        |
| **2017** | 00:30 |        |
| **2018** | 00:40 |        |
| **2019** | 00:15 |        |
| **2020** | 00:25 |        |
| **2021** | 01:25 |        |
| **2022** | 00:40 |        |

## Leaderboard Visualization

In addition to puzzle solutions, this project also contains a simple visualization (under `viz-java/src`) of the 
private leaderboard JSON that can show the data as a median-based [Rankings page](http://aoc.urizone.net).

## License

While I am making this source code available for public viewing, I am not releasing it for reuse or modification at 
this time.