package httpws.nio;

/**
 * Interface de servidor Http e Websocket
 *
 * @author Bernardo Breder
 */
public interface HwIServer {
	
	/**
	 * Verifica se tem aguma requisição em pendencia
	 *
	 * @return tem requisição
	 */
	public abstract boolean select();
	
	/**
	 * Indica se o servidor está fechado
	 *
	 * @return servidor está fechado
	 */
	public abstract boolean isClosed();
	
	/**
	 * Fecha o servidor
	 */
	public abstract void close();
	
}
