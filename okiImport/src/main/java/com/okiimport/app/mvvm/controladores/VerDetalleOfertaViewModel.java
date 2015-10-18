package com.okiimport.app.mvvm.controladores;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;

public class VerDetalleOfertaViewModel extends AbstractRequerimientoViewModel {

	 //GUI
    @Wire("#winOferta")
	private Window winOferta;
    
	//Atributos
	private Requerimiento requerimiento;
	private Oferta oferta;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: verDetalleOferta.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("oferta") Oferta oferta)
	// Lo que obtenemos de la lista es un requerimiento no una oferta
	{
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.oferta = oferta;

	}

	/** METODOS PROPIOS DE LA CLASE */

	/** GETTERS Y SETTERS */
	
	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

}
