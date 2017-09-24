package httpws.component.bootstrap;

import httpws.component.HWComponent;
import httpws.component.HWContainer;
import httpws.component.HWLink;
import httpws.opcode.HWAttributeKeyEnum;
import httpws.opcode.HWAttributeValueEnum;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

public class HWBMenu extends HWContainer {
	
	private HWContainer menuComponent;
	
	public HWBMenu(String title) {
		super(HWTagEnum.LI);
		addClass(HWClassEnum.DROPDOWN);
		addComponent(new HWLink(title, "#").addClass(HWClassEnum.DROPDOWN_TOGGLE).addAttribute(HWAttributeKeyEnum.DATA_TOGGLE, HWAttributeValueEnum.DROPDOWN).addAttribute(HWAttributeKeyEnum.ROLE,
			HWAttributeValueEnum.BUTTON).addAttribute(HWAttributeKeyEnum.ARIA_HASPOPUP, HWAttributeValueEnum.TRUE).addAttribute(HWAttributeKeyEnum.ARIA_EXPANDED, HWAttributeValueEnum.FALSE));
		addComponent(menuComponent = new HWContainer(HWTagEnum.UL).addClass(HWClassEnum.DROPDOWN_MENU));
		
		// <li class="dropdown">
		// <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
		// aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
		// <ul class="dropdown-menu">
		// <li><a href="#">Action</a></li>
		// <li><a href="#">Another action</a></li>
		// <li><a href="#">Something else here</a></li>
		// <li role="separator" class="divider"></li>
		// <li><a href="#">Separated link</a></li>
		// <li role="separator" class="divider"></li>
		// <li><a href="#">One more separated link</a></li>
		// </ul>
		// </li>
	}
	
	/**
	 * Adiciona um item de menu
	 *
	 * @param item
	 * @return this
	 */
	public <E extends HWContainer> E addMenuItem(HWBMenuItem item) {
		menuComponent.addComponent(item);
		return cast();
	}
	
	/**
	 * Adiciona um separador
	 *
	 * @return this
	 */
	public <E extends HWContainer> E addSeparator() {
		menuComponent.addComponent(new HWComponent(HWTagEnum.LI).addClass(HWClassEnum.DIVIDER).addAttribute(HWAttributeKeyEnum.ROLE, HWAttributeValueEnum.SEPARATOR));
		return cast();
	}
	
}
