package httpws.nio.ws;

import httpws.nio.HwBuffer;

/**
 * Modelo do servidor Websocket
 *
 * @author Bernardo Breder
 */
public interface HwIWsServerModel {

	/**
	 * A��o de um usu�rio conectando
	 *
	 * @param server
	 * @param id
	 * @return context
	 */
	public Object connected(HwWsServer server, String id);

	/**
	 * @param server
	 * @param id
	 * @param context
	 * @param buffer
	 */
	public void request(HwWsServer server, String id, Object context, HwBuffer buffer);

	/**
	 * @param server
	 * @param id
	 */
	public void disconnected(HwWsServer server, String id);

}
