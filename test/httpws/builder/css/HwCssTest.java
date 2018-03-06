package httpws.builder.css;

import org.junit.Assert;
import org.junit.Test;

public class HwCssTest extends HwCss {
	
	@Test
	public void testH1OneTime() {
		eq("", this);
		eq("h1 {\n" + "\tmargin: 5px;\n" + "}", $h1().$margin(5).$close());
	}
	
	@Test
	public void testH1TwoTime() {
		$h1().$margin(5).$close();
		$h1().$padding(5).$close();
		eq("h1 {\n" + "\tmargin: 5px;\n" + "\tpadding: 5px;\n" + "}", this);
	}
	
	@Test
	public void testPH1() {
		$p().$margin(10);
		$h1().$padding(5);
		eq("h1 {\n\tpadding: 5px;\n}\np {\n\tmargin: 10px;\n}", this);
	}
	
	@Test
	public void testClass() {
		$(".sss").$margin(5);
		eq(".sss {\n" + "\tmargin: 5px;\n" + "}", this);
	}
	
	public void test() {
		// $audio($a.NOT, $b.CONTROLS).$margin(5);
		// eq(".sss {\n" + "\tmargin: 5px;\n" + "}", this);
	}
	
	private void eq(String expected, HwCss css) {
		Assert.assertEquals(expected, css.$eof().toString());
		css.$reset();
	}
	
}
