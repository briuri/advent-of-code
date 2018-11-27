package buri.aoc.y2017.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author Brian Uri!
 */
public class FileUtilTest {

	@Test
	public void getDay01() {
		String content = FileUtil.getDay01();
		assertEquals(2074, content.length());
	}

	@Test
	public void testGetDay02() {
		List<List<Integer>> rows = FileUtil.getDay02();
		assertEquals(16, rows.size());
		assertEquals(16, rows.get(0).size());
		assertEquals(Integer.valueOf(4347), rows.get(0).get(0));
	}

	@Test
	public void testGetDay04() {
		List<List<String>> rows = FileUtil.getDay04();
		assertEquals(512, rows.size());
		assertEquals(10, rows.get(0).size());
		assertEquals("pphsv", rows.get(0).get(0));
	}

	@Test
	public void testGetDay05() {
		List<Integer> jumps = FileUtil.getDay05();
		assertEquals(1033, jumps.size());
		assertEquals(Integer.valueOf(0), jumps.get(0));
	}

	@Test
	public void testGetDay06() {
		List<Integer> banks = FileUtil.getDay06();
		assertEquals(16, banks.size());
		assertEquals(Integer.valueOf(4), banks.get(0));
	}
}
