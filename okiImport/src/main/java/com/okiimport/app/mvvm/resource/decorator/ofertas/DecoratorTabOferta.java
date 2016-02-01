package com.okiimport.app.mvvm.resource.decorator.ofertas;

import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.okiimport.app.model.Oferta;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;

public class DecoratorTabOferta {

	//GUI
	private Tabs tabs; //Paneles de Navegacion
	
	private Tabpanels tabpanels; //Contenidos
	
	//Atributos
	private Oferta oferta;

	public DecoratorTabOferta(Tabs tabs, Tabpanels tabpanels, Oferta oferta) {
		super();
		this.tabs = tabs;
		this.tabpanels = tabpanels;
		this.oferta = oferta;
	}

	/**METODOS PROPIOS DE LA CLASE*/
	public void agregarOferta(AbstractRequerimientoViewModel viewModel) {
		//Se agrego el nuevo panel de navegacion para la oferta
		Tab newTab = new Tab();
		newTab.setLabel("Oferta Nro. "+oferta.getNroOferta());
		newTab.setSclass("waves-color-blue waves-effect");
		tabs.appendChild(newTab);
		
		//Se agrega el contenido del panel
		Tabpanel newPanel = new Tabpanel();
		Include include = new Include();
		include.setSrc("/WEB-INF/views/template/decorator_oferta/decorator_oferta.zul");
		include.setAttribute("vm", viewModel);
		include.setAttribute("detallesOferta", oferta.getDetalleOfertas());
		include.setAttribute("numero", 54, true);
		newPanel.appendChild(include);
		tabpanels.appendChild(newPanel);
	}

}
