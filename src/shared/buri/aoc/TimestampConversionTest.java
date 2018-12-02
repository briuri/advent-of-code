package buri.aoc;

import java.time.Instant;
import java.util.Date;

import org.junit.Test;

public class TimestampConversionTest {
	
	@Test
	public void testUnixTimestampConversion() {
		long unixTimestamp = 1543727318;
		String timestamp = Date.from(Instant.ofEpochSecond(unixTimestamp)).toString();
		System.out.println(unixTimestamp + "=" + timestamp.substring(11, 19));
	}
}
