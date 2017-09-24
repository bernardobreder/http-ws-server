package httpws.hws;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;

import httpws.builder.css.HwCss;
import httpws.builder.js.HwJavascript;
import httpws.builder.js.HwVmJavascript;
import httpws.nio.HwBuffer;
import httpws.nio.http.HwHttpServer;
import httpws.nio.http.HwIHttpServerModel;
import httpws.util.Charset;

/**
 * Aplicativo do HttpWebSocket Server
 *
 * @author Bernardo Breder
 */
public class HWHttpServerModel implements HwIHttpServerModel {

	/** Path do Websocket */
	public static final String WS_URL = "ws://localhost:9090";

	/** Mime */
	protected Map<String, String> mime;

	/** Primeiro acesso */
	private long lastModified;

	/**
	 * Construtor
	 *
	 * @throws IOException
	 */
	public HWHttpServerModel() throws IOException {
		mime = new HwMimeXml().map();
		lastModified = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void request(HwHttpServer server, String id, Map<String, String> headers) {
		String path = headers.get("path");
		String method = headers.get("method");
		try {
			if (path.contains("..")) { throw new IOException("path with '..' is invalid"); }
			ClassLoader loader = getClass().getClassLoader();
			URL url = loader.getResource("httpws/resource" + path);
			if (!method.startsWith("GET")) {
				errorMethodNotAllowed(server, id, path, headers);
			} else if (path.equals("/")) {
				successfulIndex(server, id, path, headers);
			} else if (url != null) {
				successfulUrl(server, id, path, headers, url);
			} else {
				successfulFile(server, id, path, headers);
			}
		} catch (IOException e) {
			errorBadRequest(server, id, path, headers);
		} catch (Throwable e) {
			e.printStackTrace();
			errorInternalServer(server, id, path, headers);
		}
	}

	/**
	 * Responde a requisição baseado no sistema de arquivo
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 * @throws IOException
	 */
	protected void successfulFile(HwHttpServer server, String id, String path, Map<String, String> headers) throws IOException {
		File file = new File("pub", path);
		if (!path.contains("..") && file.exists()) {
			successfulFileFound(server, id, path, headers, file);
		} else {
			successfulFileNotFound(server, id, path, headers, file);
		}
	}

	/**
	 * Responde a requisição no arquivo encontrado
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 * @param file
	 * @throws IOException
	 */
	protected void successfulFileFound(HwHttpServer server, String id, String path, Map<String, String> headers, File file) throws IOException {
		long length = file.length();
		if (length <= 0) { throw new IOException("path invalid: " + path); }
		FileInputStream in = new FileInputStream(file);
		HwBuffer buffer = new HwBuffer((int) (1024 + length));
		buffer.putHttpHeader(headers, file.lastModified());
		buffer.putHttpContentLength(length);
		buffer.putHttpContentType(mime, file.getName());
		buffer.putHttpDateExperiesLastModified(new Date(), new Date(), new Date(file.lastModified()));
		buffer.putHttpEof();
		try {
			byte[] bytes = new byte[8 * 1024];
			for (int n; (n = in.read(bytes)) != -1;) {
				buffer.put(bytes, 0, n);
			}
		} finally {
			in.close();
		}
		server.send(id, buffer.flip());
	}

	/**
	 * Responde a requisição respondendo que o arquivo não foi encontrado
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 * @param file
	 */
	protected void successfulFileNotFound(HwHttpServer server, String id, String path, Map<String, String> headers, File file) {
		HwBuffer buffer = new HwBuffer(64);
		buffer.putHttpHeader(401, "Unauthorized");
		buffer.putHttpContentLength(0);
		buffer.putHttpEof();
		server.send(id, buffer.flip());
	}

	/**
	 * Responde a requisição para o path "/"
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 * @throws IOException
	 */
	protected void successfulIndex(HwHttpServer server, String id, String path, Map<String, String> headers) throws IOException {
		ClassLoader loader = getClass().getClassLoader();
		HwCss bootstrapCss = new HwCss(loader.getResourceAsStream("httpws/resource/css/bootstrap.min.css"), 120970);
		HwCss bootstrapThemeCss = new HwCss(loader.getResourceAsStream("httpws/resource/css/bootstrap-theme.min.css"), 23189);
		HwCss hwCss = new HwCss();
		HwJavascript jqueryJs = new HwJavascript(loader.getResourceAsStream("httpws/resource/js/jquery.min.js"), 95957);
		HwJavascript bootstrapJs = new HwJavascript(loader.getResourceAsStream("httpws/resource/js/bootstrap.min.js"), 36743);
		HwVmJavascript hwJs = new HwVmJavascript(WS_URL);

		HwIndexHtml indexHtml = new HwIndexHtml(bootstrapCss, bootstrapThemeCss, hwCss, jqueryJs, bootstrapJs, hwJs);
		byte[] bytes = Charset.utf8(indexHtml.toString());

		HwBuffer buffer = new HwBuffer(bytes.length + 1024);
		buffer.putHttpHeader(headers, lastModified);
		buffer.putHttpContentLength(bytes.length);
		buffer.putHttpContentType("text/html", "utf-8");
		buffer.putHttpDateExperiesLastModified(new Date(), new Date(), new Date(lastModified));
		buffer.putHttpEof();
		buffer.put(bytes);
		server.send(id, buffer.flip());
	}

	/**
	 * Responde a requisição para o path "/"
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 * @param url
	 * @throws IOException
	 */
	protected void successfulUrl(HwHttpServer server, String id, String path, Map<String, String> headers, URL url) throws IOException {
		URLConnection connection = url.openConnection();
		long length = connection.getContentLengthLong();
		long lastModified = connection.getLastModified();
		if (length <= 0) { throw new IOException("path invalid: " + path); }
		InputStream in = url.openStream();
		HwBuffer buffer = new HwBuffer((int) (1024 + length));
		buffer.putHttpHeader(headers, lastModified);
		buffer.putHttpContentLength(length);
		buffer.putHttpContentType(mime, url.getFile());
		buffer.putHttpDateExperiesLastModified(new Date(), new Date(), new Date(lastModified));
		buffer.putHttpEof();
		try {
			byte[] bytes = new byte[8 * 1024];
			for (int n; (n = in.read(bytes)) != -1;) {
				buffer.put(bytes, 0, n);
			}
		} finally {
			in.close();
		}
		server.send(id, buffer.flip());
	}

	/**
	 * Responde a requisição com um erro de método não permitido
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 */
	protected void errorMethodNotAllowed(HwHttpServer server, String id, String path, Map<String, String> headers) {
		HwBuffer buffer = new HwBuffer(64);
		buffer.putHttpHeader(405, "Only Get");
		buffer.putHttpContentLength(0);
		buffer.putHttpEof();
		server.send(id, buffer.flip());
	}

	/**
	 * Responde a requisição com um erro com requisição inválida
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 */
	protected void errorBadRequest(HwHttpServer server, String id, String path, Map<String, String> headers) {
		HwBuffer buffer = new HwBuffer(64);
		buffer.putHttpHeader(400, "Bad Request");
		buffer.putHttpContentLength(0);
		buffer.putHttpEof();
		server.send(id, buffer.flip());
	}

	/**
	 * Responde a requisição com um erro de erro interno do servidor
	 *
	 * @param server
	 * @param id
	 * @param path
	 * @param headers
	 */
	protected void errorInternalServer(HwHttpServer server, String id, String path, Map<String, String> headers) {
		HwBuffer buffer = new HwBuffer(64);
		buffer.putHttpHeader(500, "Internal Server Error");
		buffer.putHttpContentLength(0);
		buffer.putHttpEof();
		server.send(id, buffer.flip());
	}
	
}
