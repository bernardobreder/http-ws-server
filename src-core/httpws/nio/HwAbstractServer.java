package httpws.nio;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import httpws.util.ByteBufferUtil;
import httpws.util.thread.HWThreadPool;

/**
 * Classe abstrata de um servidor
 *
 * @author Tecgraf/PUC-Rio
 */
public abstract class HwAbstractServer implements HwIServer {

	/** Indica se está fechado */
	protected boolean quit;

	/** Contextos das conexões */
	protected Map<String, ClientContext> contexts;

	/** Canal do servidor */
	protected IChannel server;

	/** Tamanho da primeira leitura de uma requisição */
	protected int bufferCapacity = 32 * 1024;

	/** Tamanho máximo de buffer de leitura */
	protected int bufferMax = 32 * 1024 * 1024;

	/** Pool de Thread */
	protected HWThreadPool threadPool;

	/**
	 * Construtor
	 *
	 * @param server
	 * @param numberOfThreads
	 * @throws IOException
	 */
	public HwAbstractServer(IChannel server, int numberOfThreads) throws IOException {
		this.server = server;
		this.contexts = new HashMap<String, ClientContext>();
		this.threadPool = new HWThreadPool(numberOfThreads).setDaemon(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean select() {
		if (quit) { return false; }
		boolean flag = false;
		try {
			int selected = server.select();
			for (int n = 0; n < selected; n++) {
				ClientContext context = server.attach(n, ClientContext.class);
				try {
					if (server.isValid(n) && server.isAcceptable(n)) {
						selectKeyAcceptable(n);
						context = server.attach(n, ClientContext.class);
						flag = true;
					}
					if (server.isValid(n) && server.isReadable(n)) {
						selectKeyReadable(n);
						flag = true;
					}
					if (server.isValid(n) && !context.outputQueue.isEmpty()) {
						HwBuffer buffer = context.outputQueue.peek();
						server.write(n, buffer);
						if (!buffer.hasRemaining()) {
							context.outputQueue.remove();
						}
						flag = true;
					}
				} catch (EOFException e) {
				} catch (IOException e) {
					if (context != null) {
						contexts.remove(context.id);
					}
					server.close(n);
				}
			}
			server.clear();
		} catch (IOException e) {
			close();
		}
		return flag;
	}

	/**
	 * @param index
	 * @param type
	 * @return attach
	 * @see httpws.nio.IChannel#attach(int, java.lang.Class)
	 */
	public <E> E attach(int index, Class<E> type) {
		return server.attach(index, type);
	}

	/**
	 * Tratamento de aceitar uma nova conexão
	 *
	 * @param index
	 * @throws IOException
	 * @throws EOFException
	 */
	protected abstract void selectKeyAcceptable(int index) throws IOException, EOFException;

	/**
	 * Tratamento de leitura
	 *
	 * @param index
	 * @throws IOException
	 * @throws EOFException
	 */
	protected abstract void selectKeyReadable(int index) throws IOException, EOFException;

	/**
	 * Realiza a leitura de uma header
	 *
	 * @param context
	 * @return header ou nulo
	 */
	protected Map<String, String> header(ClientContext context) {
		int position = context.buffer.position();
		int remaining = context.buffer.remaining();
		context.buffer.flip();
		Map<String, String> header = ByteBufferUtil.http(context.buffer);
		if (header == null) {
			context.buffer.position(position);
			context.buffer.limit(remaining);
			return null;
		}
		context.buffer.flip(context.buffer.remaining());
		return header;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		return quit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		quit = true;
		server.close();
		threadPool.close();
	}

	/**
	 * Inicializa o servidor
	 *
	 * @return this
	 */
	public HwAbstractServer start() {
		threadPool.start();
		return this;
	}

	/**
	 * Envia um pacote para o cliente
	 *
	 * @param id
	 * @param buffer
	 */
	public void send(String id, HwBuffer buffer) {
		ClientContext context = contexts.get(id);
		if (context == null) { return; }
		context.outputQueue.offer(buffer);
	}

	/**
	 * Converte para o contexto do usuário
	 *
	 * @param key
	 * @return contexto
	 */
	protected ClientContext context(SelectionKey key) {
		return (ClientContext) key.attachment();
	}

	/**
	 * Retorna
	 *
	 * @return bufferCapacity
	 */
	public int getBufferCapacity() {
		return bufferCapacity;
	}

	/**
	 * @param bufferCapacity
	 */
	public void setBufferCapacity(int bufferCapacity) {
		this.bufferCapacity = bufferCapacity;
	}

	/**
	 * Retorna
	 *
	 * @return bufferMax
	 */
	public int getBufferMax() {
		return bufferMax;
	}

	/**
	 * @param bufferMax
	 */
	public void setBufferMax(int bufferMax) {
		this.bufferMax = bufferMax;
	}

	/**
	 * Contexto de conexão com um cliente
	 *
	 * @author Bernardo Breder
	 */
	protected abstract static class ClientContext {

		/** Canal de comunicação */
		public final int index;

		/** Identificador da Sessão */
		public final String id;

		/** Buffer de output não enviado */
		public final Queue<HwBuffer> outputQueue;

		/** Buffer do cliente */
		public HwBuffer buffer;

		/**
		 * @param index
		 * @param id
		 * @param capacity
		 */
		public ClientContext(int index, String id, int capacity) {
			super();
			this.index = index;
			this.id = id;
			this.buffer = new HwBuffer(capacity);
			this.outputQueue = new ConcurrentLinkedQueue<HwBuffer>();
		}

	}

}
