package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zul.Listheader;

import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;



public class ListaOrdenesCompraProveedorViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	
	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaOrdenesCompraProveedor.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		
	
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaOrdenesDeCompra")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarOrdenesDeCompra", parametros );
		}
		
	}

	
	
}