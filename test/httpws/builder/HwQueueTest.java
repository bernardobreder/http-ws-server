package httpws.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HwQueueTest {

	@Test
	public void test() {
		HwQueue<Integer> queue = new HwQueue<Integer>();
		assertEquals("[]", queue.toString());
		queue.offer(1);
		assertEquals("[1]", queue.toString());
		queue.offer(2);
		assertEquals("[1, 2]", queue.toString());
		queue.offer(3);
		assertEquals("[1, 2, 3]", queue.toString());
		assertEquals(1, queue.poll().intValue());
		assertEquals("[2, 3]", queue.toString());
		assertEquals(2, queue.poll().intValue());
		assertEquals("[3]", queue.toString());
		assertEquals(3, queue.poll().intValue());
		assertEquals("[]", queue.toString());
		queue.offer(1);
		assertEquals("[1]", queue.toString());
		assertEquals(1, queue.poll().intValue());
		assertEquals("[]", queue.toString());
	}

	@Test
	public void testGrow() {
		HwQueue<Integer> queue = new HwQueue<Integer>(2);
		queue.offer(1);
		queue.offer(2);
		queue.offer(3);
		assertEquals(1, queue.poll().intValue());
		assertEquals("[2, 3]", queue.toString());
		queue.offer(4);
		queue.offer(5);
	}

	@Test
	public void testEqualHash() {
		HwQueue<Integer> queue1 = new HwQueue<Integer>(2);
		HwQueue<Integer> queue2 = new HwQueue<Integer>(2);
		queue1.offer(1);
		queue1.offer(1);
		queue1.offer(2);
		queue1.poll();
		queue2.offer(1);
		queue2.offer(2);
		assertEquals(queue1.hashCode(), queue2.hashCode());
		assertTrue(queue1.equals(queue2));
	}

}
