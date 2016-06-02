package com.okiimport.app.mvvm.controladores;

import java.util.Map;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;


public class ListaPagosDeClientesViewModel extends AbstractMisRequerimientosViewModel {

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaMisRequerimientosOfertados.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
	}

	@Override
	protected Map<String, Object> consultarMisRequerimientos(String fieldSort,
			Boolean sortDirection, int page) {
		// TODO Auto-generated method stub
		return null;
	}	
}