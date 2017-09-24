package httpws.nio.old;

import httpws.nio.HwIServer;

/**
 * Servidor em uma Thread Separada
 *
 * @author Bernardo Breder
 */
public class HWServerThread implements Runnable {

	/** Servidor */
	protected final HwIServer server;
	
	/** Nome da thread */
	protected final String name;

	/**
	 * @param server
	 * @param name
	 */
	public HWServerThread(HwIServer server, String name) {
		this.server = server;
		this.name = name;
	}

	/**
	 * Inicia uma Thread com o servidor
	 *
	 * @return this
	 */
	public HWServerThread start() {
		new Thread(this, name).start();
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!server.isClosed()) {
			if (!server.select()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}
	}

	/**
	 * Fecha o servidor e as threads
	 */
	public void close() {
		server.close();
		Thread.currentThread().interrupt();
	}

}
