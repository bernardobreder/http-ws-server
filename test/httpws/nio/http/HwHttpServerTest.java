package httpws.nio.http;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import httpws.nio.HwBuffer;
import httpws.nio.mock.ClientChannelFixture;
import httpws.nio.mock.ServerChannelFixture;

public class HwHttpServerTest {
	
	@Test
	public void test() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwHttpServer server = new HwHttpServer(serverFxt.target(), 0, new HwMockHttpServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		Assert.assertTrue(server.select());
		Assert.assertFalse(server.select());
		clientFxt.send("GET / HTTP");
		Assert.assertFalse(server.select());
		clientFxt.send("\r\n");
		Assert.assertFalse(server.select());
		clientFxt.send("\r\n");
		Assert.assertTrue(server.select());
		Assert.assertFalse(server.select());
		clientFxt.requireReceive("a");
	}
	
	@Test
	public void test3Paths() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwHttpServer server = new HwHttpServer(serverFxt.target(), 0, new HwMockHttpServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		Assert.assertTrue(server.select());
		clientFxt.send("GET /a HTTP");
		clientFxt.send("\r\n");
		clientFxt.send("\r\n");
		clientFxt.send("GET /b HTTP");
		clientFxt.send("\r\n");
		clientFxt.send("\r\n");
		clientFxt.send("GET /c HTTP");
		clientFxt.send("\r\n");
		clientFxt.send("\r\n");
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		
		Assert.assertFalse(server.select());
		clientFxt.requireReceive("a");
		clientFxt.requireReceive("b");
		clientFxt.requireReceive("c");
	}
	
	@Test
	public void test2() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwHttpServer server = new HwHttpServer(serverFxt.target(), 0, new HwMockHttpServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		clientFxt.send("GET / HTTP\r\n\r\n");
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("a");
	}
	
	private final class HwMockHttpServerModel implements HwIHttpServerModel {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void request(HwHttpServer server, String id, Map<String, String> header) {
			String path = header.get("path");
			if (path.equals("/") || path.equals("/a")) {
				server.send(id, new HwBuffer(1).put('a').flip());
			} else if (path.equals("/b")) {
				server.send(id, new HwBuffer(1).put('b').flip());
			} else if (path.equals("/c")) {
				server.send(id, new HwBuffer(1).put('c').flip());
			}
		}
		
	}
	
}
