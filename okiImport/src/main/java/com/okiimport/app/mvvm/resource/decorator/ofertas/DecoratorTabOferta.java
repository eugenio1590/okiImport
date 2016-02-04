package com.okiimport.app.mvvm.resource.decorator.ofertas;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.enumerados.EEstatusOferta;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;

public class DecoratorTabOferta {
	
	public static final String LISTITEM_RED = "listitem-red";
	public static final String LISTITEM_GREEN = "listitem-green";

	//GUI
	private Tabs tabs; //Paneles de Navegacion
	
	private Tabpanels tabpanels; //Contenidos
	
	private Include include; //Para la busqueda de los componentes
	
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
		include = new Include();
		include.setSrc("/WEB-INF/views/template/decorator_oferta/decorator_oferta.zul");
		include.setAttribute("vm", viewModel);
		include.setAttribute("detallesOferta", oferta.getDetalleOfertas());
		include.setAttribute("oferta", oferta);
		include.setAttribute("decorator", this);
		newPanel.appendChild(include);
		tabpanels.appendChild(newPanel);
	}
	
	public void updateDetalleOferta(Integer id, Button buttonAction, boolean acept){
		StringBuilder builder;
		
		//Row
		builder = new StringBuilder("row").append(oferta.getNroOferta()).append("-").append(id);
		Listitem row = findComponent(builder.toString());
		row.setSclass((acept) ? LISTITEM_GREEN : LISTITEM_RED);
		
		if(!acept){
			oferta.setEstatus(EEstatusOferta.INVALIDA);
			updateButton("acp", id, true);
			buttonAction.setVisible(false);
		}
		else {
			if(oferta.isAprobar())
				oferta.setEstatus(EEstatusOferta.SELECCIONADA);
			updateButton("dec", id, true);
			buttonAction.setVisible(false);
		}
		
		updateEstado(oferta.getEstatus().getNombre());
	}
	
	private void updateEstado(String estatus){
		Label label = findComponent("estatusOferta"+this.oferta.getNroOferta());
		label.setValue(estatus);
	}
	
	private void updateButton(String type, Integer id, boolean visible){
		StringBuilder builder = new StringBuilder(type).append(oferta.getNroOferta()).append("-").append(id);
		Button button = findComponent(builder.toString());
		if(button!=null)
			button.setVisible(visible);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Component> T findComponent(String id){
		if(include!=null)
			return (T) include.getFellow(id);
		return null;
	}
}
