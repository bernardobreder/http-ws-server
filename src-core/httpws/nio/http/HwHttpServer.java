package httpws.nio.http;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import httpws.nio.HwAbstractServer;
import httpws.nio.IChannel;

/**
 * Servidor Http
 *
 * @author Bernardo Breder
 */
public class HwHttpServer extends HwAbstractServer {
	
	/** Modelo do servidor Http */
	protected HwIHttpServerModel model;
	
	/**
	 * Construtor
	 *
	 * @param server
	 * @param numberOfThreads
	 * @param model
	 * @throws IOException
	 */
	public HwHttpServer(IChannel server, int numberOfThreads, HwIHttpServerModel model) throws IOException {
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
		HttpClientContext context = new HttpClientContext(index, id, bufferCapacity);
		server.register(index, context);
		contexts.put(id, context);
	}
	
	/**
	 * Tratamento de leitura
	 *
	 * @param index
	 * @throws IOException
	 */
	@Override
	protected void selectKeyReadable(int index) throws IOException {
		HttpClientContext context = attach(index, HttpClientContext.class);
		int readed = server.read(index, context.buffer);
		if (readed < 0) { throw new IOException(); }
		Map<String, String> header = header(context);
		if (header == null) {
			if (!context.buffer.hasRemaining()) { throw new IOException(); }
			throw new EOFException();
		}
		threadPool.execute(() -> {
			try {
				model.request(this, context.id, header);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Contexto de conexão com um cliente
	 *
	 * @author Bernardo Breder
	 */
	protected static class HttpClientContext extends ClientContext {
		
		/**
		 * @param index
		 * @param id
		 * @param capacity
		 */
		public HttpClientContext(int index, String id, int capacity) {
			super(index, id, capacity);
		}
		
	}
	
}
