package httpws.nio.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import httpws.nio.HwBuffer;
import httpws.nio.IChannel;

public class ServerChannelMock implements IChannel {

	private boolean quit;

	private List<Context> contexts = new ArrayList<Context>();

	protected void connect(ClientChannelMock client) {
		contexts.add(new Context(client));
	}

	public void send(ClientChannelMock client, HwBuffer buffer) {
		for (Context context : contexts) {
			if (context.client == client) {
				context.buffers.offer(buffer);
				break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int select() throws IOException {
		return contexts.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(int index) throws IOException {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAcceptable(int index) throws IOException {
		return !contexts.get(index).accepted;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadable(int index) throws IOException {
		return !contexts.get(index).buffers.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(int index, Object attach) throws IOException {
		Context context = contexts.get(index);
		context.attach = attach;
		context.accepted = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> E attach(int index, Class<E> type) {
		return type.cast(contexts.get(index).attach);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int write(int index, HwBuffer buffer) throws IOException {
		Context context = contexts.get(index);
		HwBuffer byteBuffer = new HwBuffer(buffer.remaining());
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		byteBuffer.put(bytes).flip();
		context.client.receive(byteBuffer);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read(int index, HwBuffer buffer) throws IOException {
		Context context = contexts.get(index);
		if (!context.client.connected()) { return -1; }
		if (context.buffers.isEmpty()) { return 0; }
		HwBuffer byteBuffer = context.buffers.peek();
		byte[] bytes = new byte[Math.min(buffer.remaining(), byteBuffer.remaining())];
		byteBuffer.get(bytes);
		if (!byteBuffer.hasRemaining()) {
			context.buffers.remove();
		}
		buffer.put(bytes);
		return bytes.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		for (int n = 0; n < contexts.size(); n++) {
			Context context = contexts.get(n);
			if (context.closed) {
				contexts.remove(n--);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close(int index) {
		Context context = contexts.get(index);
		context.client.close();
		context.closed = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		quit = true;
	}

	public static class Context {

		public final ClientChannelMock client;

		public boolean accepted;

		public Object attach;

		public Queue<HwBuffer> buffers = new LinkedList<HwBuffer>();

		public boolean closed;

		public Context(ClientChannelMock client) {
			super();
			this.client = client;
		}

	}

}
