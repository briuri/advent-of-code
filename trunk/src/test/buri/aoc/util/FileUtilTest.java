package buri.aoc.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class FileUtilTest {

	@Test
	public void testGetDay1NumberStringSuccess() throws IOException {
		String content = FileUtil.getDay1NumberString("data/2017-01.txt");
		assertEquals(2074, content.length());
	}

	@Test(expected = IOException.class)
	public void testGetDay1NumberStringFailure() throws IOException {
		FileUtil.getDay1NumberString("unknown");
	}
	
	@Test
	public void testGetDay2SpreadsheetSuccess() throws IOException {
		List<List<Integer>> rows = FileUtil.getDay2Spreadsheet("data/2017-02.txt");
		assertEquals(16, rows.size());
		assertEquals(16, rows.get(0).size());
		assertEquals(Integer.valueOf(4347), rows.get(0).get(0));
	}

	@Test(expected = IOException.class)
	public void testGetDay2SpreadsheetFailure() throws IOException {
		FileUtil.getDay1NumberString("unknown");
	}
	
	@Test
	public void testGetDay4PassphrasesSuccess() throws IOException {
		List<List<String>> rows = FileUtil.getDay4Passphrases("data/2017-04.txt");
		assertEquals(512, rows.size());
		assertEquals(10, rows.get(0).size());
		assertEquals("pphsv", rows.get(0).get(0));
	}

	@Test(expected = IOException.class)
	public void testGetDay4PassphrasesFailure() throws IOException {
		FileUtil.getDay4Passphrases("unknown");
	}
}
