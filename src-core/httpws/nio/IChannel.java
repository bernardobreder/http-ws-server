package httpws.nio;

import java.io.IOException;

public interface IChannel {
	
	public int select() throws IOException;
	
	public boolean isValid(int index) throws IOException;
	
	public boolean isAcceptable(int index) throws IOException;
	
	public boolean isReadable(int index) throws IOException;
	
	public void register(int index, Object attach) throws IOException;
	
	/**
	 * Arquivo um objeto para um cliente
	 *
	 * @param index
	 * @param type
	 * @return valor arquivado
	 */
	public <E> E attach(int index, Class<E> type);
	
	public int write(int index, HwBuffer buffer) throws IOException;
	
	public int read(int index, HwBuffer buffer) throws IOException;
	
	public void clear();
	
	public void close(int index);
	
	public void close();
	
}
