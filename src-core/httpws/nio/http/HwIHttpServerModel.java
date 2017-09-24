package httpws.nio.http;

import java.util.Map;

/**
 * Modelo do servidor Http
 *
 * @author Bernardo Breder
 */
public interface HwIHttpServerModel {
	
	/**
	 * @param server
	 * @param id
	 * @param header
	 */
	public void request(HwHttpServer server, String id, Map<String, String> header);
	
}