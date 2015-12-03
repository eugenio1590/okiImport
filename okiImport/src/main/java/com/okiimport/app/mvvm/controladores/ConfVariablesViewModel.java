package com.okiimport.app.mvvm.controladores;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;

import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.Estado;
import com.okiimport.app.model.Moneda;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.configuracion.SControlConfiguracion;
import com.okiimport.app.service.transaccion.STransaccion;


	
public class ConfVariablesViewModel extends AbstractRequerimientoViewModel {

	
	//Servicios
	
	@BeanInjector("sControlConfiguracion")
	private SControlConfiguracion sControlConfiguracion;
	
	
	//GUI
		@Wire("#txtValorlibra")
		public Textbox txtValorLibra;
		
		@Wire("#txtPorcentajeGanancia")
		public Textbox txtPorcentajeGanancia;
		
		@Wire("#txtPorcentajeIVA")
		public Textbox txtPorcentajeIVA;
		
		@Wire("#bandbMoneda")
		private Bandbox bandbMoneda;
		
		@Wire("#pagMonedas")
		private Paging pagMonedas;
		
	//Atributos
		private List<Moneda> listaMonedas;
		private Moneda monedaSeleccionada;
		
		
	public ConfVariablesViewModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("Moneda") List<Moneda> listaMonedas)
	{
		super.doAfterCompose(view);
		
		
		
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



	public Bandbox getBandbMoneda() {
		return bandbMoneda;
	}

	public void setBandbMoneda(Bandbox bandbMoneda) {
		this.bandbMoneda = bandbMoneda;
	}



	public SControlConfiguracion getsControlConfiguracion() {
		return sControlConfiguracion;
	}


	public void setsControlConfiguracion(SControlConfiguracion sControlConfiguracion) {
		this.sControlConfiguracion = sControlConfiguracion;
	}


	public Paging getPagMonedas() {
		return pagMonedas;
	}


	public void setPagMonedas(Paging pagMonedas) {
		this.pagMonedas = pagMonedas;
	}



	public List<Moneda> getListaMonedas() {
		return listaMonedas;
	}


	public void setListaMonedas(List<Moneda> listaMonedas) {
		this.listaMonedas = listaMonedas;
	}



	public Moneda getMonedaSeleccionada() {
		return monedaSeleccionada;
	}


	public void setMonedaSeleccionada(Moneda monedaSeleccionada) {
		this.monedaSeleccionada = monedaSeleccionada;
	}

	
	
	
	
}
