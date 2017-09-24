package httpws.hws;

import static httpws.nio.HwBuffer.UINT_COMPRESS_SIZE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_ATTR_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_BODY_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_CLASS_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_CLASS_NAME_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.APPEND_CHILD_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.BUTTON_ACTION_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.CREATE_ELEMENT_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_ATTR_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_CHILD_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_CLASS_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_CLASS_NAME_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.SET_LINK_CONTENT_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.SET_TEXT_CONTENT_OPCODE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import httpws.component.HWComponent;
import httpws.nio.HwBuffer;
import httpws.opcode.HWTagEnum;
import httpws.util.Charset;
import httpws.util.collection.IntArrayMap;

/**
 * Navegador
 *
 * @author Bernardo Breder
 */
public class HWBrowser implements IBrowser {
	
	/** Lista de componentes */
	protected final IntArrayMap<HWComponent> components;
	
	/** Buffer de bytes */
	protected final LinkedList<HwBuffer> bufferList;
	
	/** Houve o handshaked */
	protected boolean handshaked;
	
	private int initialCapacity;
	
	/**
	 * Construtor
	 */
	public HWBrowser() {
		this.initialCapacity = 1024;
		this.bufferList = new LinkedList<HwBuffer>();
		this.components = new IntArrayMap<HWComponent>(1024);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createElement(HWTagEnum tag, int code, HWComponent component) {
		components.put(code, component);
		growBuffer(1 + 1 + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(CREATE_ELEMENT_OPCODE.ordinal());
		buffer.putUInt8(tag.ordinal());
		buffer.putUInt32Compressed(code);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setText(int id, String text) {
		byte[] utf8 = Charset.utf8(text);
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE + utf8.length);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(SET_TEXT_CONTENT_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
		buffer.putUtf8Bytes(utf8);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLink(int id, String link) {
		byte[] utf8 = Charset.utf8(link);
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE + utf8.length);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(SET_LINK_CONTENT_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
		buffer.putUtf8Bytes(utf8);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAction(int id) {
		growBuffer(1 + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(BUTTON_ACTION_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendBody(int id) {
		growBuffer(1 + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(ADD_BODY_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void appendChild(int parentId, int childId) {
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(APPEND_CHILD_OPCODE.ordinal());
		buffer.putUInt32Compressed(parentId);
		buffer.putUInt32Compressed(childId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeChild(int parentId, int childId) {
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(REMOVE_CHILD_OPCODE.ordinal());
		buffer.putUInt32Compressed(parentId);
		buffer.putUInt32Compressed(childId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addClass(int id, String name) {
		byte[] utf8 = Charset.utf8(name);
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE + utf8.length);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(ADD_CLASS_NAME_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
		buffer.putUtf8Bytes(utf8);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeClass(int id, String name) {
		byte[] utf8 = Charset.utf8(name);
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE + utf8.length);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(REMOVE_CLASS_NAME_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
		buffer.putUtf8Bytes(utf8);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addClass(int id, int item) {
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(ADD_CLASS_INDEX_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
		buffer.putUInt32Compressed(item);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeClass(int id, int item) {
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE);
		HwBuffer buffer = bufferList.getLast();
		buffer.putUInt8(REMOVE_CLASS_INDEX_OPCODE.ordinal());
		buffer.putUInt32Compressed(id);
		buffer.putUInt32Compressed(item);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAttribute(int code, int key, int value) {
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE);
		HwBuffer bytes = bufferList.getLast();
		bytes.putUInt8(ADD_ATTR_INDEX_OPCODE.ordinal());
		bytes.putUInt32Compressed(code);
		bytes.putUInt32Compressed(key);
		bytes.putUInt32Compressed(value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAttribute(int code, int key) {
		growBuffer(1 + UINT_COMPRESS_SIZE + UINT_COMPRESS_SIZE);
		HwBuffer bytes = bufferList.getLast();
		bytes.putUInt8(REMOVE_ATTR_INDEX_OPCODE.ordinal());
		bytes.putUInt32Compressed(code);
		bytes.putUInt32Compressed(key);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HWComponent getComponent(int id) {
		return components.get(id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HwBuffer> consumeBuffers() {
		List<HwBuffer> result = new ArrayList<HwBuffer>(bufferList);
		bufferList.clear();
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHandshaked() {
		return handshaked;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHandshaked(boolean flag) {
		handshaked = flag;
	}
	
	/**
	 * Adiciona bytes
	 *
	 * @param size
	 */
	protected void growBuffer(int size) {
		if (bufferList.isEmpty() || bufferList.getLast().remaining() < size) {
			bufferList.addLast(new HwBuffer(initialCapacity));
		}
	}
	
}
