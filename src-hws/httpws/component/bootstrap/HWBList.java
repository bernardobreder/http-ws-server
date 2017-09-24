package httpws.component.bootstrap;

import httpws.component.HWComponent;
import httpws.component.HWContainer;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

public class HWBList extends HWContainer {
	
	/**
	 * @param isFluid
	 */
	public HWBList() {
		super(HWTagEnum.UL);
		addClass(HWClassEnum.LIST_GROUP);
	}
	
	public HWBList addItem(HWComponent component) {
		addComponent(new HWContainer(HWTagEnum.LI).addComponent(component).addClass(HWClassEnum.LIST_GROUP_ITEM));
		return cast();
	}
	
}
