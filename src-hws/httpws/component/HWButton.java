package httpws.component;

import httpws.action.HSClickAction;
import httpws.hws.IBrowser;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

public class HWButton extends HWContainer {
	
	private String text;
	
	private HSClickAction okAction;
	
	private HWComponent iconTag;
	
	private HWComponent bodyTag;
	
	/**
	 * @param text
	 */
	public HWButton(String text) {
		super(HWTagEnum.BUTTON);
		setText(text);
		addComponent(iconTag = new HWComponent(HWTagEnum.SPAN).addClass(HWClassEnum.GLYPHICON).addClass(HWClassEnum.GLYPHICON_STAR));
		addComponent(bodyTag = new HWComponent(HWTagEnum.SPAN));
		addClass("btn").addClass("btn-default");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends HWComponent> E setBrowser(IBrowser browser) {
		super.setBrowser(browser);
		if (browser != null) {
			if (text != null) {
				browser.setText(bodyTag.code, text);
			}
			if (okAction != null) {
				browser.setAction(code);
			}
		}
		return cast();
	}
	
	public String getText() {
		return text;
	}
	
	public HWButton setText(String text) {
		if (!text.equals(this.text)) {
			this.text = text;
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.setText(bodyTag.code, text);
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
		text = null;
		okAction = null;
		return cast();
	}
	
}
