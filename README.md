# Advent of Code

This project contains my Advent of Code solutions. 

I've solved every puzzle from 2015-2022 in both Java and Kotlin and 2023-2024 in Kotlin. I also participated in 
real-time during the midnight competitions (2018 - 2022 in Java and 2023-2024 in Kotlin).

The top-level of the project can be imported as an IntelliJ IDEA project. Solutions are found under 
`puzzles-<language>/src/` with a package naming convention of `buri.aoc.y<yy>.d<dd>`. Each Day can be run from a JUnit 
test harness, colocated in the day's `Puzzle` class (this avoids the mental context-switching cost of using a `TestCase` file
under `/src/test/` when competing).

## Test Suite Run Time

| Year       | Java      | Kotlin    |
|------------|-----------|-----------|
| **2015**   | 00:30     | 00:10     |
| **2016**   | 00:45     | 00:35     |
| **2017**   | 00:30     | 00:15     |
| **2018**   | 00:35     | 00:20     |
| **2019**   | 00:15     | 00:10     |
| **2020**   | 00:25     | 00:20     |
| **2021**   | 01:40     | 00:50     |
| **2022**   | 00:50     | 01:00     |
| **2023**   | --:--     | 01:15     |
| **2024**   | --:--     | 00:29     |
| **Total**  | **05:30** | **05:24** |

## Leaderboard Visualization

In addition to puzzle solutions, this project also contains a simple visualization (under `viz-kotlin/src`) of the 
private leaderboard JSON that can show the data as a median-based [Rankings page](http://aoc.urizone.net).

## License

While I am making this source code available for public viewing, I am not releasing it for reuse or modification at 
this time.