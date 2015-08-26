package com.okiimport.app.mvvm.controladores;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Oferta;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.DetalleOferta;
import com.okiimport.app.modelo.Compra;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.DetalleCotizacion;
import com.okiimport.app.modelo.DetalleCotizacionInternacional;

import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.mvvm.ModeloCombo;
import com.okiimport.app.transaccion.servicios.STransaccion;



public class VerOfertaViewModel extends AbstractRequerimientoViewModel 
{
	private Requerimiento requerimiento;

    private Oferta oferta;
    private DetalleOferta detalleOferta;
    private Cotizacion cotizacion;
    
    @BeanInjector("sMaestros")
	private SMaestros sMaestros;
    
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
    private List <Oferta> listaOferta;
    
    private List<ModeloCombo<Boolean>> listaTipoRepuesto;
    
	//	private List <Requerimiento> listaRequerimientos;
    
    
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			/*@ExecutionArgParam("oferta") Oferta oferta*/
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento)
			//Lo que obtenemos de la lista es un requerimiento no una oferta
	{
		super.doAfterCompose(view);
		
		//this.oferta = oferta; //aca llamamos es al servicio y buscamos la oferta de acuerdo al requerimiento
								//se puede hacer en un metodo aparte para que se pueda usar mas adelante
		
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

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
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

	public List<Oferta> getListaOferta() {
		return listaOferta;
	}

	public void setListaOferta(List<Oferta> listaOferta) {
		this.listaOferta = listaOferta;
	}

	public List<ModeloCombo<Boolean>> getListaTipoRepuesto() {
		return listaTipoRepuesto;
	}

	public void setListaTipoRepuesto(List<ModeloCombo<Boolean>> listaTipoRepuesto) {
		this.listaTipoRepuesto = listaTipoRepuesto;
	}
	
	
    
    
}

