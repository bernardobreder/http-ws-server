package httpws.component.bootstrap;

import httpws.component.HWComponent;
import httpws.component.HWContainer;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

public class HWBContainer extends HWContainer {
	
	/**
	 * @param isFluid
	 */
	public HWBContainer(boolean isFluid) {
		super(HWTagEnum.DIV);
		if (isFluid) {
			addClass(HWClassEnum.CONTAINER);
		} else {
			addClass(HWClassEnum.CONTAINER_FLUID);
		}
	}
	
	/**
	 * @return this
	 */
	public HWBContainer addRow() {
		addComponent(new HWContainer(HWTagEnum.DIV).addClass(HWClassEnum.ROW));
		return this;
	}
	
	/**
	 * @param component
	 * @param columnSpanXs
	 * @param columnSpanSm
	 * @param columnSpanMd
	 * @param columnSpanLg
	 * @return this
	 */
	public HWBContainer addColumn(HWComponent component, int columnSpanXs, int columnSpanSm, int columnSpanMd, int columnSpanLg) {
		int componentCount = getComponentCount();
		if (componentCount == 0) {
			addRow();
		}
		HWContainer rowComponent = getComponentAt(componentCount - 1);
		HWContainer container = new HWContainer(HWTagEnum.DIV);
		container.addComponent(component);
		HWClassEnum[] values = HWClassEnum.values();
		container.addClass(values[HWClassEnum.COL_XS_1.ordinal() + (columnSpanXs - 1)]);
		container.addClass(values[HWClassEnum.COL_SM_1.ordinal() + (columnSpanSm - 1)]);
		container.addClass(values[HWClassEnum.COL_MD_1.ordinal() + (columnSpanMd - 1)]);
		container.addClass(values[HWClassEnum.COL_LG_1.ordinal() + (columnSpanLg - 1)]);
		rowComponent.addComponent(container);
		return this;
	}
	
	/**
	 * @param component
	 * @param columnSpan
	 * @return this
	 */
	public HWBContainer addColumn(HWComponent component, int columnSpan) {
		return addColumn(component, columnSpan, columnSpan, columnSpan, columnSpan);
	}
	
}
