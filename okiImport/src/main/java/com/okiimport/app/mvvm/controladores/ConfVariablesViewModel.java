package com.okiimport.app.mvvm.controladores;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.Configuracion;
import com.okiimport.app.model.Moneda;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;


public class ConfVariablesViewModel extends AbstractRequerimientoViewModel  {

	
	//Servicios
	
	//GUI
	    
	    @Wire("#winConfVariables")
		private Window winConfVariables;
	    
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
		
		private Configuracion configuracion;
	
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		super.doAfterCompose(view);
		this.configuracion = sControlConfiguracion.consultarConfiguracionActual();
		this.monedaSeleccionada = sControlConfiguracion.consultarMonedaBase();
		
		cambiarMonedas(0);
		
	}
	
	
	@Command
	@NotifyChange("*")
	public void enviar(@BindingParam("btnEnviar") Button btnEnviar,
			@BindingParam("btnLimpiar") Button btnLimpiar){
		if(checkIsFormValid()){
		
			sControlConfiguracion.guardarConfiguracion(configuracion, monedaSeleccionada);
			mostrarMensaje("Informaci\u00F3n", "La configuracion ha sido modificada existosamente ", null, null, new EventListener()
			{
						public void onEvent(Event event) throws Exception {
							
							winConfVariables.onClose();
						}
					},null);
		}
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

	
	/**
	 * Descripcion: Cambia la paginacion de acuerdo a la pagina activa
	 * de Paging 
	 * Parametros: @param view: confVariable.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarListaMonedas(){
		int page=pagMonedas.getActivePage();
		cambiarMonedas(page);
	}
	
	
	/**
	 * Descripcion: Carga la lista de monedas de acuerdo a la pagina dada como parametro
	 * Parametros: @param view: confVariables.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@SuppressWarnings("unchecked")
	@NotifyChange("listaMonedas")
	private void cambiarMonedas(@Default("0") @BindingParam("page") int page){
		Map<String, Object> parametros = this.sControlConfiguracion.consultarMonedasConHistorico(page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaMonedas = (List<Moneda>) parametros.get("monedas");
		pagMonedas.setActivePage(page);
		pagMonedas.setTotalSize(total);
		pagMonedas.setPageSize(pageSize);
	}
	
	
	
	//

	public Bandbox getBandbMoneda() {
		return bandbMoneda;
	}

	public void setBandbMoneda(Bandbox bandbMoneda) {
		this.bandbMoneda = bandbMoneda;
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
