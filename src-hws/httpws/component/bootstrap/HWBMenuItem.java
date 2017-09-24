package httpws.component.bootstrap;

import httpws.action.HSClickAction;
import httpws.component.HWButton;
import httpws.component.HWComponent;
import httpws.component.HWContainer;
import httpws.component.HWLink;
import httpws.hws.IBrowser;
import httpws.opcode.HWTagEnum;

public class HWBMenuItem extends HWContainer {
	
	private HSClickAction okAction;
	
	/**
	 * @param title
	 * @param url
	 */
	public HWBMenuItem(String title, String url) {
		super(HWTagEnum.LI);
		addComponent(new HWLink(title, url));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends HWComponent> E setBrowser(IBrowser browser) {
		super.setBrowser(browser);
		if (browser != null) {
			if (okAction != null) {
				browser.setAction(code);
			}
		}
		return cast();
	}
	
	public HSClickAction getOkAction() {
		return okAction;
	}
	
	public HWButton setOkAction(HSClickAction action) {
		this.okAction = action;
		IBrowser browser = getBrowser();
		if (browser != null) {
			browser.setAction(code);
		}
		return cast();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <E extends HWComponent> E reset() {
		super.reset();
		okAction = null;
		return cast();
	}
	
}
