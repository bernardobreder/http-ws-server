package httpws.nio.mock;

import httpws.nio.HwBuffer;
import httpws.util.Charset;

import org.junit.Assert;
import org.junit.ComparisonFailure;

public class ClientChannelFixture {

	protected final ClientChannelMock mock;

	public ClientChannelFixture(ServerChannelFixture serverFxt) {
		this(new ClientChannelMock(serverFxt.target()));
	}

	public ClientChannelFixture(ClientChannelMock mock) {
		super();
		this.mock = mock;
	}

	/**
	 * Retorna o mock
	 *
	 * @return mock
	 */
	public ClientChannelMock target() {
		return mock;
	}

	public void send(String text) {
		send(Charset.utf8(text));
	}

	public void send(byte[] bytes) {
		send(new HwBuffer(bytes.length).put(bytes).flip());
	}

	public void send(HwBuffer buffer) {
		mock.send(buffer);
	}

	public ClientChannelFixture requireReceive(String expected) {
		if (mock.buffers.isEmpty()) { throw new ComparisonFailure("No message receive", expected, ""); }
		HwBuffer buffer = mock.buffers.poll();
		int remaining = buffer.remaining();
		String message = Charset.utf8(buffer.array(), buffer.position(), remaining);
		buffer.position(buffer.position() + remaining);
		Assert.assertEquals(expected, message);
		return this;
	}

	public ClientChannelFixture requireReceiveEmpty() {
		if (!mock.buffers.isEmpty()) { throw new ComparisonFailure("must be empty", "", mock.buffers.peek().toString()); }
		return this;
	}

	public boolean connected() {
		return mock.connected();
	}

	public void close() {
		mock.close();
	}

}
