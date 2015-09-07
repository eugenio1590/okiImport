package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Oferta;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;



public class VerDetalleOfertaViewModel extends AbstractRequerimientoViewModel 
{
	
	@Wire("#winOferta")
	private Window winOferta;
	
	private Requerimiento requerimiento;

    private Oferta oferta;
    private DetalleOferta detalleOferta;
    
    @BeanInjector("sMaestros")
	private SMaestros sMaestros;
    
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
    
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento, 
			@ExecutionArgParam("detallesOfertas") List<DetalleOferta> listaDetOferta )
			//Lo que obtenemos de la lista es un requerimiento no una oferta
	{	
		super.doAfterCompose(view);	
		this.requerimiento = requerimiento;	
		
	}
	
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/"+ruta, parametros);
	}
	
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

	public DetalleOferta getDetalleOferta() {
		return detalleOferta;
	}

	public void setDetalleOferta(DetalleOferta detalleOferta) {
		this.detalleOferta = detalleOferta;
	}


	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

    
}
