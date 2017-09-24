package httpws.component;

import httpws.opcode.HWTagEnum;

public class HWTextField extends HWComponent {
	
	private String text;
	
	public HWTextField() {
		super(HWTagEnum.SPAN);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
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
