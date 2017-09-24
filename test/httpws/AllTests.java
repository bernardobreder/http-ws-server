package httpws;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import httpws.builder.HwQueueTest;
import httpws.builder.HwStackTest;
import httpws.builder.css.HwCssTest;
import httpws.builder.http.HwHtmlTest;
import httpws.builder.js.HwJavascriptTest;
import httpws.builder.string.HWStringBuilderTest;
import httpws.nio.http.HwHttpServerTest;
import httpws.nio.ws.HwWsServerTest;
import httpws.util.Base64Test;
import httpws.util.CharsetTest;
import httpws.util.IntArrayMapTest;

@RunWith(Suite.class)
@SuiteClasses({ Base64Test.class, CharsetTest.class, HwStackTest.class, HwQueueTest.class, HWStringBuilderTest.class, HwJavascriptTest.class, IntArrayMapTest.class, HwCssTest.class,
		HwWsServerTest.class, HwHttpServerTest.class, HwHtmlTest.class })
// HwSqlTest.class
public class AllTests {

}
