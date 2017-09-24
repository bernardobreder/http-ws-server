package httpws.util.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Bernardo Breder
 */
public class HWThreadWorker implements Runnable {
	
	/** Lista de tarefas */
	protected final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	
	/** Thread */
	protected Thread thread;
	
	/** Indica se acabou a Thread */
	protected boolean quit;
	
	/**
	 * @param name
	 */
	public HWThreadWorker(String name) {
		this.thread = new Thread(this, name);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!quit) {
			try {
				Runnable runnable = queue.take();
				try {
					runnable.run();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	/**
	 * Adiciona uma tarefa no worker
	 *
	 * @param r
	 * @return se consegui adicionar
	 */
	public boolean execute(Runnable r) {
		return queue.offer(r);
	}
	
	/**
	 * Inicia a thread
	 *
	 * @return this
	 */
	public HWThreadWorker start() {
		this.thread.start();
		return this;
	}
	
	/**
	 * Fecha o trabalhador
	 */
	public void close() {
		quit = true;
		thread.interrupt();
	}
	
	/**
	 * @param flag
	 * @return this
	 */
	public HWThreadWorker setDaemon(boolean flag) {
		thread.setDaemon(flag);
		return this;
	}
	
}
