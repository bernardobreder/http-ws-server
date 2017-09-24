package httpws.builder.css;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import httpws.util.Charset;

/**
 * Constroi um código javascript
 *
 * @author Bernardo Breder
 */
public class HWStyleCode {
	
	/** Lista de comandos adicionados */
	private List<Object> itens = new ArrayList<Object>();
	
	/**
	 * Escreve um comando
	 *
	 * @param format
	 * @param objects
	 * @return this
	 */
	public HWStyleCode add(Object format, Object... objects) {
		Object formatStr = format == null ? "null" : format;
		if (objects.length > 0) {
			formatStr = String.format(format.toString(), objects);
		}
		itens.add(formatStr);
		return this;
	}
	
	/**
	 * @param file
	 * @param encoding
	 * @return this
	 * @throws IOException
	 */
	public HWStyleCode addFile(File file, String encoding) throws IOException {
		if (!file.exists()) { throw new FileNotFoundException(file.toString()); }
		long length = file.length();
		if (length > 32 * 1024 * 1024) { throw new IllegalArgumentException("file is too long"); }
		InputStream in = new FileInputStream(file);
		try {
			return addInputStream(in, (int) length, encoding);
		} finally {
			in.close();
		}
	}
	
	/**
	 * @param input
	 * @param length
	 * @param encoding
	 * @return this
	 * @throws IOException
	 */
	public HWStyleCode addInputStream(InputStream input, int length, String encoding) throws IOException {
		length = length < 0 ? 1024 : length;
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		byte[] bytes = new byte[1024];
		for (int n = 0; (n = input.read(bytes)) != -1;) {
			out.write(bytes, 0, n);
		}
		bytes = out.toByteArray();
		if (encoding == null) {
			add(Charset.utf8(bytes));
		} else {
			add(new String(bytes, encoding));
		}
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = itens.size();
		for (int n = 0; n < size; n++) {
			Object item = itens.get(n);
			sb.append(item.toString());
			if (n != size - 1) {
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
}
