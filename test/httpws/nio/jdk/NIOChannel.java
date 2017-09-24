package httpws.nio.jdk;

import httpws.nio.HwBuffer;
import httpws.nio.IChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NIOChannel implements IChannel {
	
	private ServerSocketChannel server;
	
	private Selector selector;
	
	private SelectionKey[] selectedArray;
	
	private Set<SelectionKey> selectedKeys;
	
	/**
	 * Construtor
	 *
	 * @param port
	 * @throws IOException
	 */
	public NIOChannel(int port) throws IOException {
		this.server = ServerSocketChannel.open();
		this.server.configureBlocking(false);
		this.server.socket().setReceiveBufferSize(1024);
		this.server.socket().bind(new InetSocketAddress(port));
		this.selector = Selector.open();
		this.server.register(selector, server.validOps());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int select() throws IOException {
		int selectCount = selector.selectNow();
		if (selectCount == 0) { return 0; }
		this.selectedKeys = selector.selectedKeys();
		this.selectedArray = selectedKeys.toArray(new SelectionKey[selectedKeys.size()]);
		return this.selectedArray.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(int index) throws IOException {
		if (this.selectedArray == null) { return false; }
		return this.selectedArray[index].isValid();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAcceptable(int index) throws IOException {
		if (this.selectedArray == null) { return false; }
		return this.selectedArray[index].isAcceptable();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadable(int index) throws IOException {
		if (this.selectedArray == null) { return false; }
		return this.selectedArray[index].isReadable();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(int index, Object attach) throws IOException {
		if (this.selectedArray == null) { return; }
		SocketChannel channel = server.accept();
		channel.configureBlocking(false);
		int ops = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
		Object[] attachArray = new Object[] { channel, attach };
		channel.register(selector, ops, attachArray);
		this.selectedArray[index].attach(attachArray);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int write(int index, HwBuffer buffer) throws IOException {
		if (this.selectedArray == null) { return -1; }
		return channel(index).write(buffer.buffer());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read(int index, HwBuffer buffer) throws IOException {
		if (this.selectedArray == null) { return -1; }
		return channel(index).read(buffer.buffer());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> E attach(int index, Class<E> type) {
		if (this.selectedArray == null) { return null; }
		Object[] attach = (Object[]) this.selectedArray[index].attachment();
		if (attach == null) { return null; }
		return type.cast(attach[1]);
	}
	
	/**
	 * Retorna o channel de um cliente
	 *
	 * @param index
	 * @return channel
	 */
	protected SocketChannel channel(int index) {
		if (this.selectedArray == null) { return null; }
		Object[] attach = (Object[]) this.selectedArray[index].attachment();
		if (attach == null) { return null; }
		SocketChannel channel = (SocketChannel) attach[0];
		return channel;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		if (selectedKeys != null) {
			this.selectedKeys.clear();
			this.selectedArray = null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close(int index) {
		if (this.selectedArray == null) { return; }
		this.selectedArray[index].cancel();
		this.selectedArray[index].attach(null);
		try {
			((SocketChannel) this.selectedArray[index].channel()).socket().close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		try {
			selector.close();
		} catch (IOException e) {
		}
		try {
			server.close();
		} catch (IOException e) {
		}
		try {
			server.socket().close();
		} catch (IOException e) {
		}
	}
	
}
