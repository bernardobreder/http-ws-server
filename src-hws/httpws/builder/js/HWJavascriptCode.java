package httpws.builder.js;

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
public class HWJavascriptCode {
	
	/** Lista de comandos adicionados */
	private List<Object> itens = new ArrayList<Object>();
	
	/** Nível de bloco */
	protected int level = 0;
	
	/**
	 * Aumenta um nível de bloco
	 *
	 * @return this
	 */
	public HWJavascriptCode push() {
		level++;
		return this;
	}
	
	/**
	 * Diminui um nível de bloco
	 *
	 * @return this
	 */
	public HWJavascriptCode pop() {
		level--;
		return this;
	}
	
	/**
	 * Escreve um comando
	 *
	 * @param format
	 * @param objects
	 * @return this
	 */
	public HWJavascriptCode add(Object format, Object... objects) {
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
	public HWJavascriptCode addFile(File file, String encoding) throws IOException {
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
	public HWJavascriptCode addInputStream(InputStream input, int length, String encoding) throws IOException {
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
		return toString(level);
	}
	
	/**
	 * @param level
	 * @return string
	 */
	protected String toString(int level) {
		StringBuilder sb = new StringBuilder();
		int size = itens.size();
		for (int n = 0; n < size; n++) {
			Object item = itens.get(n);
			sb.append(item);
			if (n != size - 1) {
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Declara uma variável com um valor
	 *
	 * @param name
	 * @param value
	 * @return comando
	 */
	protected static HWJavascriptCode declare(String name, Object value) {
		return new HWJavascriptCode().add("var %s = %s;", name, value);
	}
	
	/**
	 * Declara uma variável com um valor
	 *
	 * @param name
	 * @param params
	 * @param block
	 * @return comando
	 */
	protected static HWJavascriptCode objectConstrutor(String name, Object params, HWJavascriptCode block) {
		return new HWJavascriptCode().add("var %s = function(%s) {", name, params).push().add(block).pop().add("}");
	}
	
	/**
	 * Declara uma variável com um valor
	 *
	 * @param classname
	 * @param name
	 * @param params
	 * @param block
	 * @return comando
	 */
	protected static HWJavascriptCode objectMethod(String classname, String name, Object params, HWJavascriptCode block) {
		return new HWJavascriptCode().add("%s.prototype.%s = function(%s) {", classname, name, params).push().add(block).pop().add("}");
	}
	
	/**
	 * Declara uma variável com um valor
	 *
	 * @param name
	 * @param params
	 * @param block
	 * @return comando
	 */
	protected static HWJavascriptCode func(String name, String params, HWJavascriptCode block) {
		return new HWJavascriptCode().add("function %s (%s) {", name, params).push().add(block).pop().add("}");
	}
	
	/**
	 * Declara uma função de inicialização da página
	 *
	 * @param init
	 * @return comando
	 */
	protected static HWJavascriptCode declareInitFunction(HWJavascriptCode init) {
		return new HWJavascriptCode().add("function init() {").push().add(init).add("}").pop().add("window.addEventListener('load', init, false);");
	}
	
	/**
	 * Declara um Websocket
	 *
	 * @param uri
	 * @param wsOpen
	 * @param wsClose
	 * @param wsMessage
	 * @param wsError
	 * @return comando
	 */
	protected static HWJavascriptCode declareWebSocket(String uri, HWJavascriptCode wsOpen, HWJavascriptCode wsClose, HWJavascriptCode wsMessage, HWJavascriptCode wsError) {
		HWJavascriptCode cmd = new HWJavascriptCode();
		cmd.add("ws = new WebSocket(%s);", toJsString(uri));
		if (wsOpen != null) {
			cmd.add("ws.onopen = function(evt) {").push().add(wsOpen).pop().add("};");
		}
		if (wsClose != null) {
			cmd.add("ws.onclose = function(evt) {").push().add(wsClose).pop().add("};");
		}
		if (wsMessage != null) {
			cmd.add("ws.onmessage = function(evt) {").push().add(wsMessage).pop().add("};");
		}
		if (wsError != null) {
			cmd.add("ws.onerror = function(evt) {").push().add(wsError).pop().add("};");
		}
		return cmd;
	}
	
	/**
	 * Declara um leitor de arquivo
	 *
	 * @param dataVar
	 * @param onLoad
	 * @return comando
	 */
	protected static HWJavascriptCode declareFileReaderFunction(String dataVar, HWJavascriptCode onLoad) {
		HWJavascriptCode cmd = new HWJavascriptCode();
		cmd.add("var fileReader = new FileReader();");
		cmd.add("fileReader.onload = function(progressEvent) {").push();
		cmd.add("console.log(new Uint8Array(this.result).length);").push();
		if (onLoad != null) {
			cmd.add(onLoad);
		}
		cmd.pop().add("};");
		cmd.add("fileReader.readAsArrayBuffer(%s);", dataVar);
		return cmd;
	}
	
	/**
	 * Converte objetos Java para objetos Javascript
	 *
	 * @param value
	 * @return texto do objeto javascript
	 */
	protected static String toJsString(Object value) {
		if (value instanceof String) {
			String string = (String) value;
			return "\"" + string + "\"";
		} else if (value instanceof Enum[]) {
			Object[] array = (Object[]) value;
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int n = 0; n < array.length; n++) {
				sb.append(toJsString(array[n].toString().toLowerCase().replace('_', '-')));
				if (n != array.length - 1) {
					sb.append(", ");
				}
			}
			sb.append("]");
			return sb.toString();
		} else if (value instanceof Object[]) {
			Object[] array = (Object[]) value;
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int n = 0; n < array.length; n++) {
				sb.append(toJsString(array[n]));
				if (n != array.length - 1) {
					sb.append(", ");
				}
			}
			sb.append("]");
			return sb.toString();
		}
		return value.toString();
	}
	
	/**
	 * Converte vários objetos java para javascript
	 *
	 * @param values
	 * @return objetos javascript
	 */
	protected static String toJsStrings(Object... values) {
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < values.length; n++) {
			sb.append(toJsString(values[n]));
			if (n != values.length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
}
