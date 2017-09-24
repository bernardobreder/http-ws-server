package httpws.hws;

import java.io.IOException;

import httpws.nio.http.HwHttpServer;
import httpws.nio.http.HwIHttpServerModel;
import httpws.nio.jdk.NIOChannel;
import httpws.nio.old.HWServerThread;
import httpws.nio.ws.HwIWsServerModel;
import httpws.nio.ws.HwWsServer;
import httpws.util.Log;

public class AppMain {
	
	static int httpPort = 8080;
	
	static int wsPort = 9090;
	
	public static void main(String[] args) throws IOException {
		Log.init(Log.Level.INFO);
		HWHttpServerModel httpModel = new HWHttpServerModel();
		HwWsServerModel wsModel = new HwWsServerModel();
		HWServerThread httpServer = createHttpServerSocket(httpModel);
		HWServerThread wsServer = createWsServerSocket(wsModel);
		if (httpServer == null) {
			Log.error("Http Server port %d already opened.", httpPort);
		}
		if (wsServer == null) {
			Log.error("Ws Server port %d already opened.", httpPort);
		}
		if (httpServer != null && wsServer != null) {
			httpServer.start();
			wsServer.start();
			Log.info("Http Websocket Server Started.");
		} else {
			if (httpServer != null) {
				httpServer.close();
			}
			if (wsServer != null) {
				wsServer.close();
			}
		}
	}
	
	protected static HWServerThread createHttpServerSocket(HwIHttpServerModel model) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		Log.info("Http Server Model with %d Workers.", availableProcessors);
		try {
			return new HWServerThread(new HwHttpServer(new NIOChannel(httpPort), availableProcessors, model).start(), "Http Accept");
		} catch (IOException e) {
			return null;
		}
	}
	
	protected static HWServerThread createWsServerSocket(HwIWsServerModel model) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		Log.info("Websocket Server Model with %d Workers.", availableProcessors * 4);
		try {
			return new HWServerThread(new HwWsServer(new NIOChannel(wsPort), availableProcessors * 4, model).start(), "Websocket Accept");
		} catch (IOException e) {
			return null;
		}
	}
	
}
