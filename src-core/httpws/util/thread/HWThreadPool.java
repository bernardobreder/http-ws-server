package httpws.util.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pool de Thread
 *
 * @author Bernardo Breder
 */
public class HWThreadPool {

	/** Trabalhadores */
	protected final HWThreadWorker[] workers;

	/** Trabalhador atual */
	protected AtomicInteger workerIndex;

	/**
	 * Construtor
	 *
	 * @param numberOfThreads
	 */
	public HWThreadPool(int numberOfThreads) {
		workers = new HWThreadWorker[numberOfThreads];
		for (int n = 0; n < workers.length; n++) {
			String name = getClass().getSimpleName() + "-" + (n + 1);
			workers[n] = new HWThreadWorker(name);
		}
		workerIndex = new AtomicInteger(0);
	}

	/**
	 * Executa o {@link Runnable}
	 *
	 * @param runnable
	 */
	public void execute(Runnable runnable) {
		if (workers.length == 0) {
			runnable.run();
		} else {
			int index = workerIndex.getAndIncrement();
			index = index % workers.length;
			workers[index].execute(runnable);
		}
	}

	/**
	 * @param flag
	 * @return this
	 */
	public HWThreadPool setDaemon(boolean flag) {
		for (int n = 0; n < workers.length; n++) {
			workers[n].setDaemon(flag);
		}
		return this;
	}

	/**
	 * Inicializa o thread pool
	 *
	 * @return this
	 */
	public HWThreadPool start() {
		for (int n = 0; n < workers.length; n++) {
			workers[n].start();
		}
		return this;
	}

	/**
	 * Fecha o pool
	 */
	public void close() {
		for (int n = 0; n < workers.length; n++) {
			workers[n].close();
		}
	}

}
