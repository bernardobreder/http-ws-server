package httpws.component;

import httpws.hws.IBrowser;
import httpws.opcode.HWTagEnum;

public class HWLabel extends HWComponent {
	
	private String text;
	
	public HWLabel() {
		super(HWTagEnum.SPAN);
	}
	
	public HWLabel(String text) {
		this();
		setText(text);
	}
	
	/**
	 * {@inheritDoc} @
	 */
	@Override
	public <E extends HWComponent> E setBrowser(IBrowser browser) {
		super.setBrowser(browser);
		if (browser != null) {
			if (text != null) {
				browser.setText(code, text);
			}
		}
		return cast();
	}
	
	public String getText() {
		return text;
	}
	
	public HWLabel setText(String text) {
		if (!text.equals(this.text)) {
			this.text = text;
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.setText(code, text);
			}
		}
		return cast();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <E extends HWComponent> E reset() {
		super.reset();
		text = null;
		return cast();
	}
	
}
