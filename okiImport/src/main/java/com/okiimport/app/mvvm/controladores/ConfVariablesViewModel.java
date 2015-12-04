package com.okiimport.app.mvvm.controladores;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;


	
public class ConfVariablesViewModel extends AbstractRequerimientoViewModel {

	
	//GUI
		@Wire("#txtValorlibra")
		public Textbox txtValorLibra;
		
		@Wire("#txtPorcentajeGanancia")
		public Textbox txtPorcentajeGanancia;
		
		@Wire("#txtPorcentajeIVA")
		public Textbox txtPorcentajeIVA;
		
		
	public ConfVariablesViewModel() {
		// TODO Auto-generated constructor stub
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: Permite limpiar los campos de la pantalla ConfVariables
	 * Parametros: @param view: confVariables.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "*" })
	public void limpiar() {
		txtValorLibra.setValue("");
		txtPorcentajeGanancia.setValue("");
		txtPorcentajeIVA.setValue("");
		super.cleanConstraintForm();
	}

}
