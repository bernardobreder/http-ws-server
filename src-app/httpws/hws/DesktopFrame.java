package httpws.hws;

import httpws.component.HWButton;
import httpws.component.HWFrame;
import httpws.component.bootstrap.HWBFixedMenuBar;
import httpws.component.bootstrap.HWBMenu;
import httpws.component.bootstrap.HWBMenuItem;

public class DesktopFrame extends HWFrame {
	
	private HWButton cancelButton;
	
	private HWButton okButton;
	
	public DesktopFrame() {
		addComponent(new HWBFixedMenuBar("BandeiraBR", "#").addMenu(createScenariumMenu()).addMenu(createInputDataMenu()).addMenu(createReportMenu()).addMenu(createGraphMenu()).addMenu(
			createEnvironmentMenu()).addMenu(createAdminMenu()).addMenu(createHelpMenu()));
		// HWBContainer container = new HWBContainer(true);
		// container.addRow().addColumn(cancelButton = new HWButton("Cancelar").<HWButton>
		// addClass("m-r").setOkAction(new HSClickAction() {
		//
		// @Override
		// public void action() {
		// String text = cancelButton.getText();
		// cancelButton.setText(text + "?");
		// }
		// }), 6).addColumn(okButton = new HWButton("Ok").setOkAction(new HSClickAction() {
		//
		// @Override
		// public void action() {
		// String text = okButton.getText();
		// okButton.setText(text + "!");
		// okButton.removeClass("btn-default").addClass("btn-warning");
		// if (cancelButton.getParent() != null) {
		// cancelButton.getParent().remove(cancelButton);
		// }
		// }
		// }), 6);
		// container.addRow().addColumn(new HWBList().addItem(new HWLink("aaa", "#")).addItem(new
		// HWLink("aaa", "#")).addItem(new HWLink("aaa", "#")).addItem(new HWLink("aaa",
		// "#")).addItem(new HWBList()
		// .addItem(new HWLink("aaa", "#")).addItem(new HWLink("aaa", "#")).addItem(new
		// HWLink("aaa", "#")).addItem(new HWLink("aaa", "#")).addItem(new HWLink("aaa",
		// "#")).addItem(new HWLink("aaa",
		// "#")).addItem(new HWLink("aaa", "#"))).addItem(new HWLink("aaa", "#")).addItem(new
		// HWLink("aaa", "#")), 3);
		// addComponent(container);
	}
	
	protected HWBMenu createScenariumMenu() {
		HWBMenu menu = new HWBMenu("Cen�rio");
		menu.addMenuItem(new HWBMenuItem("Criar com Dados Corporativo...", "#"));
		menu.addMenuItem(new HWBMenuItem("Criar a partir de um Cen�rio...", "#"));
		return menu;
	}
	
	protected HWBMenu createInputDataMenu() {
		HWBMenu menu = new HWBMenu("Entrada de Dados");
		menu.addMenuItem(new HWBMenuItem("Edi��o de Estoque de Abertura...", "#"));
		menu.addMenuItem(new HWBMenuItem("Edi��o de Estoque Meta...", "#"));
		return menu;
	}
	
	protected HWBMenu createReportMenu() {
		HWBMenu menu = new HWBMenu("Relat�rio");
		menu.addMenuItem(new HWBMenuItem("Estoque de Abertura Di�rio...", "#"));
		menu.addMenuItem(new HWBMenuItem("Estoque Di�rio Operacional e Certificado...", "#"));
		menu.addMenuItem(new HWBMenuItem("Acompanhamento Previsto x Realizado...", "#"));
		menu.addMenuItem(new HWBMenuItem("Realizado x Proje��o de Produ��o...", "#"));
		menu.addMenuItem(new HWBMenuItem("Carga de Unidade de Processo...", "#"));
		menu.addMenuItem(new HWBMenuItem("Personalizado...", "#"));
		return menu;
	}
	
	protected HWBMenu createGraphMenu() {
		HWBMenu menu = new HWBMenu("Gr�fico");
		menu.addMenuItem(new HWBMenuItem("Previsto x Realizado...", "#"));
		menu.addMenuItem(new HWBMenuItem("Estoques �s 24h...", "#"));
		menu.addMenuItem(new HWBMenuItem("Estoques M�dios...", "#"));
		return menu;
	}
	
	protected HWBMenu createEnvironmentMenu() {
		HWBMenu menu = new HWBMenu("Ambiente");
		menu.addMenuItem(new HWBMenuItem("Regi�es e Pontos Operacionais...", "#"));
		menu.addMenuItem(new HWBMenuItem("Grupamentos e Produtos...", "#"));
		menu.addMenuItem(new HWBMenuItem("Vis�es...", "#"));
		menu.addMenuItem(new HWBMenuItem("Consultar Rotas...", "#"));
		menu.addMenuItem(new HWBMenuItem("Arcos...", "#"));
		menu.addSeparator();
		menu.addMenuItem(new HWBMenuItem("Log do Planab...", "#"));
		menu.addMenuItem(new HWBMenuItem("Log do Plano de Produ��o por Refinaria...", "#"));
		menu.addMenuItem(new HWBMenuItem("Log do SGPV...", "#"));
		return menu;
	}
	
	protected HWBMenu createAdminMenu() {
		HWBMenu menu = new HWBMenu("Administra��o");
		menu.addMenuItem(new HWBMenuItem("Usu�rios...", "#"));
		menu.addMenuItem(new HWBMenuItem("Pastas...", "#"));
		menu.addMenuItem(new HWBMenuItem("Rotas...", "#"));
		menu.addMenuItem(new HWBMenuItem("Regi�es...", "#"));
		menu.addMenuItem(new HWBMenuItem("Relat�rios Personalizados...", "#"));
		menu.addSeparator();
		menu.addMenuItem(new HWBMenuItem("Analisar Banco de Dados...", "#"));
		menu.addMenuItem(new HWBMenuItem("Analise de Desempenho do Sistema...", "#"));
		menu.addMenuItem(new HWBMenuItem("Ver Propriedades Globais...", "#"));
		menu.addSeparator();
		menu.addMenuItem(new HWBMenuItem("Ver Estado da Conex�o com o Barramento...", "#"));
		return menu;
	}
	
	protected HWBMenu createHelpMenu() {
		HWBMenu menu = new HWBMenu("Ajuda");
		menu.addMenuItem(new HWBMenuItem("Sobre", "#"));
		return menu;
	}
	
}
