package httpws.builder.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HWStringBuilderTest {
	
	@Test
	public void test() {
		assertEquals("abc", new HWStringBuilder().append("abc").toString());
		assertEquals("<b>a</b>bc", new HWStringBuilder().append("***a***bc").toString());
		assertEquals("a<b>b</b>c", new HWStringBuilder().append("a***b***c").toString());
		assertEquals("ab<b>c</b>", new HWStringBuilder().append("ab***c***").toString());
		assertEquals("a<b></b>bc", new HWStringBuilder().append("a******bc").toString());
		assertEquals("a***", new HWStringBuilder().append("a***").toString());
		assertEquals("***c", new HWStringBuilder().append("***c").toString());
	}
	
	@Test
	public void testS() {
		assertEquals("abc", new HWStringBuilder().append("abc").toString());
		assertEquals("<s>a</s>bc", new HWStringBuilder().append("---a---bc").toString());
		assertEquals("a<s>b</s>c", new HWStringBuilder().append("a---b---c").toString());
		assertEquals("ab<s>c</s>", new HWStringBuilder().append("ab---c---").toString());
		assertEquals("a<s></s>bc", new HWStringBuilder().append("a------bc").toString());
		assertEquals("a---", new HWStringBuilder().append("a---").toString());
		assertEquals("---c", new HWStringBuilder().append("---c").toString());
	}
	
}
