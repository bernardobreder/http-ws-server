package httpws.hws;

import java.util.List;

import httpws.component.HWComponent;
import httpws.nio.HwBuffer;
import httpws.opcode.HWTagEnum;

/**
 * Contrato de um Navegador
 *
 * @author Bernardo Breder
 */
public interface IBrowser {

	/**
	 * Cria um elemento
	 *
	 * @param tag
	 * @param code
	 * @param component
	 */
	public void createElement(HWTagEnum tag, int code, HWComponent component);

	/**
	 * Altera o texto de um componente
	 *
	 * @param code
	 * @param text
	 */
	public void setText(int code, String text);

	/**
	 * Altera o texto de um componente
	 *
	 * @param code
	 * @param link
	 */
	public void setLink(int code, String link);

	/**
	 * Atribui uma ação para um componente
	 *
	 * @param code
	 */
	public void setAction(int code);

	/**
	 * Adiciona um componente no corpo da página
	 *
	 * @param code
	 */
	public void appendBody(int code);

	/**
	 * Adiciona um filho no componente
	 *
	 * @param parentCode
	 * @param childCode
	 */
	public void appendChild(int parentCode, int childCode);

	/**
	 * Adiciona um filho no componente
	 *
	 * @param parentCode
	 * @param childCode
	 */
	public void removeChild(int parentCode, int childCode);

	/**
	 * Adiciona um filho no componente
	 *
	 * @param code
	 * @param name
	 */
	public void addClass(int code, String name);

	/**
	 * Adiciona uma classe
	 *
	 * @param code
	 * @param item
	 */
	public void addClass(int code, int item);

	/**
	 * Remove uma classe
	 *
	 * @param code
	 * @param name
	 */
	public void removeClass(int code, String name);

	/**
	 * Remove uma classe
	 *
	 * @param code
	 * @param item
	 */
	public void removeClass(int code, int item);

	/**
	 * @param code
	 * @param key
	 * @param value
	 */
	public void addAttribute(int code, int key, int value);

	/**
	 * @param code
	 * @param key
	 */
	public void removeAttribute(int code, int key);

	/**
	 * Recupera um componente
	 *
	 * @param code
	 * @return componente
	 */
	public HWComponent getComponent(int code);

	/**
	 * Retorna o buffer
	 *
	 * @return buffer
	 */
	public List<HwBuffer> consumeBuffers();

	/**
	 * Indica se houve o aperto de mão
	 *
	 * @return aperto de mão
	 */
	public boolean isHandshaked();

	/**
	 * Indica se houve o aporto de mão
	 *
	 * @param flag
	 */
	public void setHandshaked(boolean flag);

}
