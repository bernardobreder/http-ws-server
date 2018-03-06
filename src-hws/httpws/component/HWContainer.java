package httpws.component;

import java.util.LinkedList;
import java.util.List;

import httpws.hws.IBrowser;
import httpws.opcode.HWTagEnum;

public class HWContainer extends HWComponent {
	
	/** Filhos do componente */
	private List<HWComponent> children;
	
	/**
	 * @param tag
	 */
	public HWContainer(HWTagEnum tag) {
		super(tag);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends HWComponent> E setBrowser(IBrowser browser) {
		super.setBrowser(browser);
		if (browser != null) {
			if (children != null) {
				for (int n = 0; n < children.size(); n++) {
					HWComponent child = children.get(n);
					child.setBrowser(browser);
					browser.appendChild(getCode(), child.getCode());
				}
			}
		}
		return cast();
	}
	
	/**
	 * @param component
	 * @return this
	 */
	public <E extends HWContainer> E addComponent(HWComponent component) {
		if (children == null) {
			children = new LinkedList<HWComponent>();
		}
		children.add(component);
		IBrowser browser = getBrowser();
		component.setParent(this);
		component.setBrowser(browser);
		if (browser != null) {
			browser.appendChild(getCode(), component.getCode());
		}
		return cast();
	}
	
	/**
	 * @param component
	 * @return this
	 */
	public <E extends HWContainer> E remove(HWComponent component) {
		if (children != null) {
			children.remove(component);
			component.setParent(null);
			component.setBrowser(null);
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.removeChild(getCode(), component.getCode());
			}
			component.reset();
		}
		return cast();
	}
	
	/**
	 * @return this
	 */
	public <E extends HWContainer> E removeAll() {
		if (children != null) {
			for (HWComponent child : children) {
				child.setParent(null);
				child.setBrowser(null);
			}
			children.clear();
		}
		return cast();
	}
	
	public int getComponentCount() {
		return children.size();
	}
	
	public <E extends HWContainer> E getComponentAt(int index) {
		return children.get(index).cast();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <E extends HWComponent> E reset() {
		super.reset();
		if (children != null) {
			for (HWComponent child : children) {
				child.reset();
			}
			children.clear();
			children = null;
		}
		return cast();
	}
	
}
