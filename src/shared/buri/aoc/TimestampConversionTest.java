package buri.aoc;

import java.time.Instant;
import java.util.Date;

import org.junit.Test;

public class TimestampConversionTest {
	
	/**
	 * Converts a UNIX timestamp into an amount of time after midnight.
	 */
	private static String convert(long timestamp) {
		return (Date.from(Instant.ofEpochSecond(timestamp)).toString().substring(11, 19));
	}
	
	@Test
	public void testUnixTimestampConversion() {
		long unixTimestamp1 = 1543727318;
		long unixTimestamp2 = 1543727590;
		long unixTimestamp3 = 1543727679;
		System.out.println(convert(unixTimestamp1));
		System.out.println(convert(unixTimestamp2));
		System.out.println(convert(unixTimestamp3));
	}
}
