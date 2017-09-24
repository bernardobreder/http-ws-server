package httpws.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import httpws.util.Charset;

public class CharsetTest {
	
	@Test
	public void test() throws UnsupportedEncodingException {
		Assert.assertEquals(Arrays.toString("ação".getBytes("utf-8")), Arrays.toString(Charset.utf8("ação")));
		Assert.assertEquals("ação", Charset.utf8(Charset.utf8("ação")));
	}
	
}
