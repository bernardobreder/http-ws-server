package httpws.component.bootstrap;

import java.io.IOException;

import httpws.component.HWContainer;
import httpws.component.HWLink;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

public class HWBFixedMenuBar extends HWContainer {
	
	protected HWContainer containerDiv;
	
	protected HWContainer navbarHeaderDiv;
	
	protected HWContainer navbarCollapse;
	
	protected HWContainer navbarLeftMenu;
	
	protected HWLink navbarBrand;
	
	/**
	 * @param title
	 * @param url
	 * @throws IOException
	 */
	public HWBFixedMenuBar(String title, String url) {
		super(HWTagEnum.DIV);
		addClass(HWClassEnum.NAVBAR).addClass(HWClassEnum.NAVBAR_DEFAULT).addClass(HWClassEnum.NAVBAR_FIXED_TOP);
		containerDiv = new HWContainer(HWTagEnum.DIV).addClass(HWClassEnum.CONTAINER);
		this.addComponent(containerDiv);
		navbarHeaderDiv = new HWContainer(HWTagEnum.DIV).addClass(HWClassEnum.NAVBAR_HEADER);
		containerDiv.addComponent(navbarHeaderDiv);
		navbarBrand = new HWLink(title, "#").addClass(HWClassEnum.NAVBAR_BRAND);
		navbarHeaderDiv.addComponent(navbarBrand);
		navbarCollapse = new HWContainer(HWTagEnum.DIV).addClass(HWClassEnum.COLLAPSE).addClass(HWClassEnum.NAVBAR_COLLAPSE);
		containerDiv.addComponent(navbarCollapse);
		navbarLeftMenu = new HWContainer(HWTagEnum.UL).addClass(HWClassEnum.NAV).addClass(HWClassEnum.NAVBAR_NAV);
		navbarCollapse.addComponent(navbarLeftMenu);
	}
	
	public HWBFixedMenuBar addMenu(HWLink menu) {
		navbarLeftMenu.addComponent(new HWContainer(HWTagEnum.LI).addComponent(menu));
		return this;
	}
	
	public HWBFixedMenuBar addMenu(HWBMenu menu) {
		navbarLeftMenu.addComponent(menu);
		return this;
	}
	
}
