package httpwsserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import httpws.util.Base64;
import httpws.util.Charset;
import httpws.util.Sha1;

public class Main {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9090);
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(() -> {

		});
		executor.execute(() -> {
			for (;;) {
				try {
					Socket socket = server.accept();
					executor.execute(() -> {
						try {
							StringBuilder sb = new StringBuilder();
							while (sb.length() < 4 || !(sb.charAt(sb.length() - 4) == '\r' && sb.charAt(sb.length() - 3) == '\n' && sb.charAt(sb.length() - 2) == '\r' && sb.charAt(sb.length() - 1) == '\n')) {
								sb.append((char) socket.getInputStream().read());
							}
							String request = sb.toString();
							if (request.endsWith("\r\n\r\n")) {
								Map<String, String> header = Arrays.stream(request.trim().split("\n")).skip(1).collect(Collectors.toMap(e -> getKey(e), e -> getValue(e)));
								OutputStream out = socket.getOutputStream();
								out.write(Charset.ascii("HTTP/1.1 101 Switching Protocols\r\n"));
								out.write(Charset.ascii("Upgrade: Websocket\r\n"));
								out.write(Charset.ascii("Connection: Upgrade\r\n"));
								out.write(Charset.ascii("Sec-WebSocket-Accept: "));
								String key = header.get("Sec-WebSocket-Key") + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
								out.write(Charset.ascii(Base64.encode(Sha1.encode(key)) + "\r\n"));
								out.write(Charset.ascii("\r\n"));
								out.flush();
								for (;;) {
									byte[] bytes = read(socket);
									send(socket, new String(bytes));
									System.out.println(new String(bytes));
								}
							}
						} catch (IOException e) {
							try {
								socket.close();
							} catch (IOException e1) {
							}
						}
					});
				} catch (IOException e) {
				}
			}
		});
	}

	public static String getKey(String line) {
		return line.substring(0, line.indexOf(':'));
	}

	public static String getValue(String line) {
		return line.substring(line.indexOf(':') + 1).trim();
	}

	protected static byte[] read(Socket socket) throws IOException {
		InputStream in = socket.getInputStream();
		int c1 = in.read();
		int opcode = c1 & 0xF;
		int c2 = in.read();
		boolean closing = opcode == 0x8;
		boolean hasMask = (c2 & 0x80) == 0x80;
		int length = c2 & 0x7F;
		if (length == 126) {
			int c3 = in.read();
			int c4 = in.read();
			length = (c3 << 8) + c4;
		} else if (length == 127) {
			int c3 = in.read();
			int c4 = in.read();
			int c5 = in.read();
			int c6 = in.read();
			int c7 = in.read();
			int c8 = in.read();
			int c9 = in.read();
			int c10 = in.read();
			long longLength = ((long) c3 << 56) + ((long) c4 << 48) + ((long) c5 << 40) + ((long) c6 << 32) + ((long) c7 << 24) + (c8 << 16) + (c9 << 8) + c10;
			if (length > Integer.MAX_VALUE) {
				throw new IOException();
			}
			length = length;
		}
		int[] mask = new int[4];
		if (hasMask) {
			for (int n = 0; n < mask.length; n++) {
				mask[n] = in.read();
			}
		}
		byte[] bytes = new byte[length];
		if (hasMask) {
			for (int n = 0; n < length; n++) {
				bytes[n] = (byte) (in.read() ^ mask[n % 4]);
			}
		} else {
			for (int n = 0; n < length; n++) {
				bytes[n] = (byte) (in.read());
			}
		}
		return bytes;
	}

	public static void send(Socket socket, String text) throws IOException {
		long length = 0;
		for (int n = 0; n < text.length(); n++) {
			char c = text.charAt(n);
			if (c <= 0x7F) {
				length++;
			} else if (c <= 0x7FF) {
				length += 2;
			} else {
				length += 3;
			}
		}
		OutputStream out = socket.getOutputStream();
		out.write((byte) (0x80 + 0x1));
		if (length <= 125) {
			out.write((byte) length);
		} else if (length <= 0xFFFF) {
			out.write((byte) 126);
			out.write((byte) ((length >> 8) & 0xFF));
			out.write((byte) (length & 0xFF));
		} else {
			out.write((byte) ((length >> 56) & 0xFF));
			out.write((byte) ((length >> 48) & 0xFF));
			out.write((byte) ((length >> 40) & 0xFF));
			out.write((byte) ((length >> 32) & 0xFF));
			out.write((byte) ((length >> 24) & 0xFF));
			out.write((byte) ((length >> 16) & 0xFF));
			out.write((byte) ((length >> 8) & 0xFF));
			out.write((byte) (length & 0xFF));
		}
		for (int n = 0; n < text.length(); n++) {
			int c = text.charAt(n);
			if (c <= 0x7F) {
				out.write((byte) c);
			} else if (c <= 0x7FF) {
				out.write((byte) (((c >> 6) & 0x1F) + 0xC0));
				out.write((byte) ((c & 0x3F) + 0x80));
			} else {
				out.write((byte) (((c >> 12) & 0xF) + 0xE0));
				out.write((byte) (((c >> 6) & 0x3F) + 0x80));
				out.write((byte) ((c & 0x3F) + 0x80));
			}
		}
	}

	public static void putWsBytes(Socket socket, byte[] bytes) throws IOException {
		OutputStream out = socket.getOutputStream();
		long length = bytes.length;
		out.write((byte) (0x80 + 0x2));
		if (length <= 125) {
			out.write((byte) length);
		} else if (length <= 0xFFFF) {
			out.write((byte) 126);
			out.write((byte) ((length >> 8) & 0xFF));
			out.write((byte) (length & 0xFF));
		} else {
			out.write((byte) ((length >> 56) & 0xFF));
			out.write((byte) ((length >> 48) & 0xFF));
			out.write((byte) ((length >> 40) & 0xFF));
			out.write((byte) ((length >> 32) & 0xFF));
			out.write((byte) ((length >> 24) & 0xFF));
			out.write((byte) ((length >> 16) & 0xFF));
			out.write((byte) ((length >> 8) & 0xFF));
			out.write((byte) (length & 0xFF));
		}
		for (int n = 0; n < length; n++) {
			out.write(bytes[n]);
		}
	}

}
