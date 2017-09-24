package httpws.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import httpws.util.collection.IntArrayMap;

public class IntArrayMapTest {
	
	@Test
	public void test() {
		IntArrayMap<String> map = new IntArrayMap<String>(1);
		map.put(2, "a");
		map.put(8, "b");
		map.put(4, "c");
		map.put(6, "d");
		map.put(1, "e");
		map.put(9, "f");
		map.put(3, "g");
		map.put(7, "h");
		map.put(5, "i");
		assertEquals("a", map.get(2));
		assertEquals("b", map.get(8));
		assertEquals("c", map.get(4));
		assertEquals("d", map.get(6));
		assertEquals("e", map.get(1));
		assertEquals("f", map.get(9));
		assertEquals("g", map.get(3));
		assertEquals("h", map.get(7));
		assertEquals("i", map.get(5));
	}
	
}
