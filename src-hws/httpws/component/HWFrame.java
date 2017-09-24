package httpws.component;

import httpws.opcode.HWTagEnum;

public class HWFrame extends HWContainer {
	
	public HWFrame() {
		super(HWTagEnum.DIV);
	}
	
	public <E extends HWFrame> E push() {
		return cast();
	}
	
	public void pop() {
	
	}
	
}
