package httpws.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class Base64Test {
	
	@Test
	public void test() {
		int id = 54324353;
		byte[] bytes = new byte[] { (byte) (id & 0xFF000000), (byte) (id & 0x00FF0000), (byte) (id & 0x0000FF00), (byte) (id & 0x000000FF) };
		Assert.assertEquals("AAAAgQ==", Base64.encode(bytes));
		byte[] decode = Base64.decode("AAAAgQ==");
		Assert.assertEquals(Arrays.toString(bytes), Arrays.toString(decode));
	}
	
}
