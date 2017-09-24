package httpws.nio;

/**
 * Interface de servidor Http e Websocket
 *
 * @author Bernardo Breder
 */
public interface HwIServer {
	
	/**
	 * Verifica se tem aguma requisi��o em pendencia
	 *
	 * @return tem requisi��o
	 */
	public abstract boolean select();
	
	/**
	 * Indica se o servidor est� fechado
	 *
	 * @return servidor est� fechado
	 */
	public abstract boolean isClosed();
	
	/**
	 * Fecha o servidor
	 */
	public abstract void close();
	
}
