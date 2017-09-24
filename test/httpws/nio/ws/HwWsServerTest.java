package httpws.nio.ws;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import httpws.nio.HwBuffer;
import httpws.nio.mock.ClientChannelFixture;
import httpws.nio.mock.ServerChannelFixture;

public class HwWsServerTest {
	
	private static final String WS_HANDSHAKE = "HTTP/1.1 101 Switching Protocols\r\n" + "Upgrade: Websocket\r\n" + "Connection: Upgrade\r\n"
		+ "Sec-WebSocket-Accept: zwKd5BnoWxQ6GxTxxSmBSHGHX9M=\r\n\r\n";

	@Test
	public void testConnected() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		Assert.assertTrue(clientFxt.connected());
		Assert.assertTrue(server.select());
		Assert.assertTrue(clientFxt.connected());
	}
	
	@Test
	public void testSendEverything() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("????");
		Assert.assertFalse(server.select());
		Assert.assertTrue(clientFxt.connected());
	}
	
	@Test
	public void testDisconnect() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send(new HwBuffer(0).flip());
		clientFxt.close();
		Assert.assertFalse(server.select());
		Assert.assertFalse(clientFxt.connected());
	}
	
	@Test
	public void testWrongHeader() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("????\r\n");
		Assert.assertFalse(server.select());
		Assert.assertTrue(clientFxt.connected());
	}
	
	@Test
	public void testWrongHeaderWithKey() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("????\r\nsec-websocket-key: abc\r\n\r\n");
		Assert.assertFalse(server.select());
		Assert.assertTrue(clientFxt.connected());
	}
	
	@Test
	public void testWrongHeaderWithItem() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("????\r\na: b\r\n\r\n");
		Assert.assertFalse(server.select());
		Assert.assertTrue(clientFxt.connected());
	}
	
	@Test
	public void testHeaderWithKey() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\nsec-websocket-key: abc\r\n\r\n");
		Assert.assertTrue(server.select());
		Assert.assertTrue(clientFxt.connected());
		clientFxt.requireReceive(WS_HANDSHAKE);
	}
	
	@Test
	public void testHandshakeSeparated() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET ");
		clientFxt.send("/ ");
		clientFxt.send("HTTP\r\n");
		clientFxt.send("sec-websocket-key: ");
		clientFxt.send("abc");
		clientFxt.send("\r\n");
		clientFxt.send("\r\n");
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		Assert.assertTrue(clientFxt.connected());
		clientFxt.requireReceive(WS_HANDSHAKE);
	}
	
	@Test
	public void test7() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\n");
		clientFxt.send("sec-websocket-key: abc\r\n");
		clientFxt.send("\r\n");
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		Assert.assertTrue(clientFxt.connected());
		clientFxt.requireReceive(WS_HANDSHAKE);
	}
	
	@Test
	public void test77() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\n");
		clientFxt.send("sec-websocket-key: abc\r\n");
		clientFxt.send("\r");
		clientFxt.send("\n");
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		Assert.assertTrue(clientFxt.connected());
		clientFxt.requireReceive(WS_HANDSHAKE);
		clientFxt.send(new HwBuffer(64).putWsString("1").flip());
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("1");
	}
	
	@Test
	public void testEchoMessage() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\nsec-websocket-key: abc\r\n\r\n");
		server.select();
		clientFxt.requireReceive(WS_HANDSHAKE);
		clientFxt.send(new HwBuffer(64).putWsString("ação").flip());
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("ação");
	}
	
	@Test
	public void testEchoMessage126() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\nsec-websocket-key: abc\r\n\r\n");
		server.select();
		clientFxt.requireReceive(WS_HANDSHAKE);
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < 126; n++) {
			sb.append('a');
		}
		clientFxt.send(new HwBuffer(64 + 126).putWsString(sb.toString()).flip());
		Assert.assertTrue(server.select());
		clientFxt.requireReceive(sb.toString());
	}
	
	@Test
	public void testEchoMessage0xFFFF() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\nsec-websocket-key: abc\r\n\r\n");
		server.select();
		clientFxt.requireReceive(WS_HANDSHAKE);
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < 0xFFFF; n++) {
			sb.append('a');
		}
		clientFxt.send(new HwBuffer(64 + 0xFFFF).putWsString(sb.toString()).flip());
		Assert.assertTrue(server.select());
		Assert.assertTrue(server.select());
		Assert.assertTrue(server.select());
		clientFxt.requireReceive(sb.toString());
	}
	
	@Test
	public void testEchoMessageSeparated() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\nsec-websocket-key: abc\r\n\r\n");
		server.select();
		clientFxt.requireReceive(WS_HANDSHAKE);
		clientFxt.send(new HwBuffer(64).put(0x81).flip());
		clientFxt.send(new HwBuffer(64).put(1).flip());
		clientFxt.send(new HwBuffer(64).put('a').flip());
		clientFxt.requireReceiveEmpty();
		Assert.assertFalse(server.select());
		clientFxt.requireReceiveEmpty();
		Assert.assertTrue(server.select());
		clientFxt.requireReceiveEmpty();
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("a");
	}
	
	@Test
	public void test3MessageOnce() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		server.select();
		clientFxt.send("GET / HTTP\r\nsec-websocket-key: abc\r\n\r\n");
		server.select();
		clientFxt.requireReceive(WS_HANDSHAKE);
		clientFxt.send(new HwBuffer(64).putWsString("1").putWsString("11").putWsString("111").flip());
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("1");
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("11");
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("111");
	}
	
	@Test
	public void testHandshake3MessageOnce() throws IOException {
		ServerChannelFixture serverFxt = new ServerChannelFixture();
		HwWsServer server = new HwWsServer(serverFxt.target(), 0, new HwMockWsServerModel());
		ClientChannelFixture clientFxt = new ClientChannelFixture(serverFxt);
		Assert.assertTrue(server.select());
		clientFxt.send("GET / HTTP\r\n");
		clientFxt.send("sec-websocket-key: abc\r\n");
		clientFxt.send("\r\n");
		clientFxt.send(new HwBuffer(64).putWsString("1").putWsString("11").putWsString("111").flip());
		Assert.assertFalse(server.select());
		Assert.assertFalse(server.select());
		Assert.assertTrue(server.select());
		clientFxt.requireReceive(WS_HANDSHAKE);
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("1");
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("11");
		Assert.assertTrue(server.select());
		clientFxt.requireReceive("111");
	}
	
	private final class HwMockWsServerModel implements HwIWsServerModel {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void request(HwWsServer server, String id, Object context, HwBuffer buffer) {
			server.send(id, new HwBuffer(buffer.remaining()).put(buffer.array(), buffer.position(), buffer.remaining()).flip());
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object connected(HwWsServer server, String id) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void disconnected(HwWsServer server, String id) {
		}
		
	}
	
}
