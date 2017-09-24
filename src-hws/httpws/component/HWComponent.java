package httpws.component;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import httpws.hws.IBrowser;
import httpws.opcode.HWAttributeKeyEnum;
import httpws.opcode.HWAttributeValueEnum;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

/**
 * @author Bernardo Breder
 */

public class HWComponent {

	protected int code = -1;

	protected Reference<IBrowser> browserRef;

	protected Reference<HWContainer> parent;

	protected Set<String> classname;

	protected Map<HWAttributeKeyEnum, HWAttributeValueEnum> attributes;

	protected final HWTagEnum tag;

	/**
	 * Construtor
	 *
	 * @param tag
	 */
	public HWComponent(HWTagEnum tag) {
		this.tag = tag;
	}

	public int getCode() {
		return code;
	}

	public IBrowser getBrowser() {
		return browserRef == null ? null : browserRef.get();
	}

	/**
	 * @param browser
	 * @return this @
	 */
	public <E extends HWComponent> E setBrowser(IBrowser browser) {
		this.browserRef = browser == null ? null : new WeakReference<IBrowser>(browser);
		if (browser != null) {
			if (code < 0) {
				code = System.identityHashCode(this);
				browser.createElement(tag, code, this);
			}
			if (classname != null) {
				for (String name : classname) {
					try {
						HWClassEnum item = HWClassEnum.valueOf(name.toUpperCase().replace('-', '_'));
						browser.addClass(code, item.ordinal());
					} catch (IllegalArgumentException e) {
						browser.addClass(code, name);
					}
				}
			}
			if (attributes != null) {
				for (Entry<HWAttributeKeyEnum, HWAttributeValueEnum> entry : attributes.entrySet()) {
					browser.addAttribute(code, entry.getKey().ordinal(), entry.getValue().ordinal());
				}
			}
		}
		return cast();
	}

	public boolean hasClass(String classname) {
		if (classname == null) { return false; }
		return this.classname.contains(classname);
	}

	public <E extends HWComponent> E addClass(String name) {
		if (this.classname == null) {
			this.classname = new TreeSet<String>();
		}
		if (!this.classname.contains(name)) {
			this.classname.add(name);
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.addClass(code, name);
			}
		}
		return cast();
	}

	public <E extends HWComponent> E addClass(HWClassEnum classEnum) {
		if (this.classname == null) {
			this.classname = new TreeSet<String>();
		}
		String name = classEnum.name().replace('_', '-').toLowerCase();
		if (!this.classname.contains(name)) {
			this.classname.add(name);
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.addClass(code, classEnum.ordinal());
			}
		}
		return cast();
	}

	public <E extends HWComponent> E removeClass(String name) {
		if (this.classname != null) {
			if (this.classname.contains(name)) {
				this.classname.remove(name);
				if (this.classname.isEmpty()) {
					this.classname = null;
				}
				IBrowser browser = getBrowser();
				if (browser != null) {
					browser.removeClass(code, name);
				}
			}
		}
		return cast();
	}

	/**
	 * Indica se tem o atributo
	 *
	 * @param key
	 * @return tem o atributo
	 */
	public boolean hasAttribute(HWAttributeKeyEnum key) {
		if (attributes == null) { return false; }
		return attributes.containsKey(key);
	}

	/**
	 * Adiciona um atributo ao elemento
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
	public <E extends HWComponent> E addAttribute(HWAttributeKeyEnum key, HWAttributeValueEnum value) {
		if (attributes == null) {
			attributes = new TreeMap<HWAttributeKeyEnum, HWAttributeValueEnum>();
		}
		if (!attributes.containsKey(key)) {
			attributes.put(key, value);
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.addAttribute(code, key.ordinal(), value.ordinal());
			}
		}
		return cast();
	}

	/**
	 * Adiciona um atributo ao elemento
	 *
	 * @param key
	 * @return this
	 */
	public <E extends HWComponent> E removeAttribute(HWAttributeKeyEnum key) {
		if (this.classname != null) {
			String keyName = key.name().replace('_', '-').toLowerCase();
			if (this.classname.contains(keyName)) {
				this.classname.remove(keyName);
				if (this.classname.isEmpty()) {
					this.classname = null;
				}
				IBrowser browser = getBrowser();
				if (browser != null) {
					browser.removeAttribute(code, key.ordinal());
				}
			}
		}
		return cast();
	}

	public HWContainer getParent() {
		return parent == null ? null : parent.get();
	}

	protected <E extends HWComponent> E setParent(HWContainer parent) {
		this.parent = parent == null ? null : new WeakReference<HWContainer>(parent);
		return cast();
	}

	protected <E extends HWComponent> E reset() {
		code = -1;
		browserRef = null;
		parent = null;
		classname = null;
		attributes = null;
		return cast();
	}

	/**
	 * Retorna o this
	 *
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected <E extends HWComponent> E cast() {
		return (E) this;
	}

}
