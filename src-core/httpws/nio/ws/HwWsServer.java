package httpws.nio.ws;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import httpws.nio.HwAbstractServer;
import httpws.nio.HwBuffer;
import httpws.nio.IChannel;
import httpws.util.ByteBufferUtil;

/**
 * Servidor Websocket
 *
 * @author Bernardo Breder
 */
public class HwWsServer extends HwAbstractServer {

	/** Modelo do servidor Websocket */
	protected HwIWsServerModel model;

	/**
	 * Construtor
	 *
	 * @param server
	 * @param numberOfThreads
	 * @param model
	 * @throws IOException
	 */
	public HwWsServer(IChannel server, int numberOfThreads, HwIWsServerModel model) throws IOException {
		super(server, numberOfThreads);
		this.model = model;
	}

	/**
	 * Tratamento de aceitar uma nova conexão
	 *
	 * @param index
	 * @throws IOException
	 */
	@Override
	protected void selectKeyAcceptable(int index) throws IOException {
		String id = UUID.randomUUID().toString();
		WsClientContext context = new WsClientContext(index, id, bufferCapacity, null);
		server.register(index, context);
		contexts.put(id, context);
		threadPool.execute(() -> {
			try {
				context.userData = model.connected(this, id);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Tratamento de leitura
	 *
	 * @param index
	 * @throws IOException
	 */
	@Override
	protected void selectKeyReadable(int index) throws IOException, EOFException {
		WsClientContext context = attach(index, WsClientContext.class);
		int readed = server.read(index, context.buffer);
		if (readed < 0) { throw new IOException(); }
		context.buffer.flip();
		do {
			if (context.handshaked) {
				if (context.bodyBuffer == null) {
					int c1 = context.buffer.getOrFlip();
					int opcode = c1 & 0xF;
					context.closing = opcode == 0x8;
					int c2 = context.buffer.getOrFlip();
					context.hasMask = (c2 & 0x80) == 0x80;
					context.length = c2 & 0x7F;
					if (context.length == 126) {
						int c3 = context.buffer.getOrFlip();
						int c4 = context.buffer.getOrFlip();
						context.length = (c3 << 8) + c4;
					} else if (context.length == 127) {
						int c3 = context.buffer.getOrFlip();
						int c4 = context.buffer.getOrFlip();
						int c5 = context.buffer.getOrFlip();
						int c6 = context.buffer.getOrFlip();
						int c7 = context.buffer.getOrFlip();
						int c8 = context.buffer.getOrFlip();
						int c9 = context.buffer.getOrFlip();
						int c10 = context.buffer.getOrFlip();
						long length = ((long) c3 << 56) + ((long) c4 << 48) + ((long) c5 << 40) + ((long) c6 << 32) + ((long) c7 << 24) + (c8 << 16) + (c9 << 8) + c10;
						if (length > Integer.MAX_VALUE) { throw new IOException(); }
						context.length = (int) length;
					}
					if (context.hasMask) {
						for (int n = 0; n < context.mask.length; n++) {
							context.mask[n] = context.buffer.get();
						}
					}
					context.bodyBuffer = new HwBuffer(context.length);
					context.buffer.shift();
				}
				while (context.buffer.hasRemaining() && context.bodyBuffer.hasRemaining()) {
					if (context.hasMask) {
						context.bodyBuffer.put(context.buffer.get() ^ context.mask[context.bodyBuffer.position() % 4]);
					} else {
						context.bodyBuffer.put(context.buffer.get());
					}
				}
				if (!context.bodyBuffer.hasRemaining()) {
					context.bodyBuffer.flip();
					Object userData = context.userData;
					HwBuffer bodyBuffer = context.bodyBuffer;
					if (context.closing) {
						threadPool.execute(() -> {
							try {
								model.disconnected(this, context.id);
							} catch (Throwable e) {
								e.printStackTrace();
							}
						});
					} else {
						threadPool.execute(() -> {
							try {
								model.request(this, context.id, userData, bodyBuffer);
							} catch (Throwable e) {
								e.printStackTrace();
							}
						});
					}
					context.bodyBuffer = null;
				}
			} else {
				Map<String, String> headers = ByteBufferUtil.http(context.buffer);
				if (headers == null) {
					context.buffer.position(context.buffer.limit()).limit(context.buffer.capacity());
					throw new EOFException();
				}
				if (!headers.containsKey("method") || !headers.containsKey("path") || !headers.containsKey("sec-websocket-key")) { throw new EOFException(); }
				server.write(index, new HwBuffer(256).putWsHeader(headers).flip());
				context.handshaked = true;
			}
		} while (context.buffer.hasRemaining());
		context.buffer.clear();
	}

	/**
	 * Contexto de conexão com um cliente
	 *
	 * @author Bernardo Breder
	 */
	protected static class WsClientContext extends ClientContext {

		/** Indica que está fechando a conexão */
		public boolean closing;

		/** Comprimento da menssagem */
		public int length;

		/** Buffer do cliente */
		public HwBuffer bodyBuffer;

		/** Contexto */
		public Object userData;

		/** Mascara */
		public final int[] mask = new int[4];

		/** Mascara */
		public boolean hasMask;

		/** Multi Byte Buffer */
		public boolean handshaked;

		/**
		 * @param index
		 * @param id
		 * @param capacity
		 * @param attchment
		 */
		public WsClientContext(int index, String id, int capacity, Object attchment) {
			super(index, id, capacity);
			this.userData = attchment;
		}

	}

}
