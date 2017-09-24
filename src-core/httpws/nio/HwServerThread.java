package httpws.nio;


/**
 * Servidor em uma Thread Separada
 *
 * @author Bernardo Breder
 */
public class HwServerThread implements Runnable {

	/** Servidor */
	protected final HwIServer server;

	/**
	 * @param server
	 */
	public HwServerThread(HwIServer server) {
		this.server = server;
	}

	/**
	 * Inicia uma Thread com o servidor
	 *
	 * @return this
	 */
	public HwServerThread start() {
		new Thread(this, server.getClass().getSimpleName()).start();
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
