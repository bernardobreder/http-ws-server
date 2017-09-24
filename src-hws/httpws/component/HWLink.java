package httpws.component;

import httpws.hws.IBrowser;
import httpws.opcode.HWTagEnum;

public class HWLink extends HWComponent {
	
	private String text;
	
	private String link;
	
	private HSButtonOkAction okAction;
	
	/**
	 * @param title
	 * @
	 */
	public HWLink(String title, String url) {
		super(HWTagEnum.A);
		setText(title);
		setLink(url);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends HWComponent> E setBrowser(IBrowser browser) {
		super.setBrowser(browser);
		if (browser != null) {
			if (link != null) {
				browser.setLink(code, link);
			}
			if (text != null) {
				browser.setText(code, text);
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
	
	public HWLink setText(String text) {
		if (!text.equals(this.text)) {
			this.text = text;
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.setText(code, text);
			}
		}
		return cast();
	}
	
	public String getLink() {
		return link;
	}
	
	public HWLink setLink(String link) {
		if (!link.equals(this.link)) {
			this.link = link;
			IBrowser browser = getBrowser();
			if (browser != null) {
				browser.setLink(code, link);
			}
		}
		return cast();
	}
	
	public HSButtonOkAction getOkAction() {
		return okAction;
	}
	
	public HWLink setOkAction(HSButtonOkAction action) {
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
		link = null;
		okAction = null;
		return cast();
	}
	
	public interface HSButtonOkAction {
		
		public void action(HWLink button);
		
	}
	
}
