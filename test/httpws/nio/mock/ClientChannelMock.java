package httpws.nio.mock;

import java.util.LinkedList;
import java.util.Queue;

import httpws.nio.HwBuffer;

public class ClientChannelMock {

	protected boolean quit;

	protected ServerChannelMock serverChannel;

	protected Queue<HwBuffer> buffers = new LinkedList<HwBuffer>();

	public ClientChannelMock(ServerChannelMock serverChannel) {
		this.serverChannel = serverChannel;
		serverChannel.connect(this);
	}

	public void receive(HwBuffer byteBuffer) {
		buffers.offer(byteBuffer);
	}

	public void send(HwBuffer buffer) {
		serverChannel.send(this, buffer);
	}

	public void close() {
		quit = true;
	}

	public boolean connected() {
		return !quit;
	}

}
