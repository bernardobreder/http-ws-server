package httpws.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HwStackTest {

	@Test
	public void test() {
		HwStack<Integer> stack = new HwStack<Integer>();
		assertEquals("[]", stack.toString());
		stack.push(1);
		assertEquals("[1]", stack.toString());
		stack.push(2);
		assertEquals("[1, 2]", stack.toString());
		stack.push(3);
		assertEquals("[1, 2, 3]", stack.toString());
		assertEquals(3, stack.pop().intValue());
		assertEquals("[1, 2]", stack.toString());
		assertEquals(2, stack.pop().intValue());
		assertEquals("[1]", stack.toString());
		assertEquals(1, stack.pop().intValue());
		assertEquals("[]", stack.toString());
	}

	@Test
	public void testManyLowCapacity() {
		HwStack<Integer> stack = new HwStack<Integer>(0);
		for (int n = 0; n <= 1024; n++) {
			stack.push(n);
			assertEquals(n, stack.peek().intValue());
		}
		for (int n = 1024; n >= 0; n--) {
			assertEquals(n, stack.pop().intValue());
		}
	}

}
