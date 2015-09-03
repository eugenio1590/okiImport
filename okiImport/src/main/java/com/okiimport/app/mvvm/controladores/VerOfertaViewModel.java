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
import com.okiimport.app.modelo.Proveedor;
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
	
	@Wire("#winOferta")
	private Window winOferta;
	
	private Requerimiento requerimiento;

    private Oferta oferta;
    private DetalleOferta detalleOferta;
    private Cotizacion cotizacion;
    
    private boolean cerrar;
    
    @BeanInjector("sMaestros")
	private SMaestros sMaestros;
    
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
    private List <Oferta> listaOferta;
    
    private List<ModeloCombo<Boolean>> listaTipoRepuesto;
    
    private List<DetalleOferta> listaDetOferta;
    
    
	//	private List <Requerimiento> listaRequerimientos;
    
    
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento, 
			@ExecutionArgParam("detallesOfertas") List<DetalleOferta> listaDetOferta )
			//Lo que obtenemos de la lista es un requerimiento no una oferta
	{	
		super.doAfterCompose(view);	
		this.requerimiento = requerimiento;
		cargarOferta();
	    //aca llamamos es al servicio y buscamos la oferta de acuerdo al requerimiento
	   //se puede hacer en un metodo aparte para que se pueda usar mas adelante
		
		this.listaDetOferta = (listaDetOferta == null ) ? new ArrayList<DetalleOferta>(): listaDetOferta;
		
	}
	
	/**GLOBAL COMMAND*/
	@GlobalCommand
	@NotifyChange("oferta")
	public void cargarOferta(){
		
		oferta = sTransaccion.consultarOfertaEnviadaPorRequerimiento(requerimiento.getIdRequerimiento());

	}
	
	
	@Command
	@NotifyChange({ "oferta" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar) {
		
		
		if ( checkIsFormValid() )
		{
			
		
		//1ero Actualizar Estatus de la Oferta
		
		oferta.setEstatus("recibida");
		sTransaccion.actualizarOferta(oferta); // Se implementara la cascada
		
		//sTransaccion.actualizarRequerimiento(requerimiento);  Falta definir estatus
		
		cargarOferta();
		
		registrarOferta(cerrar);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		parametros.put("detallesOfertas", listaDetOferta);
		
		
		if (oferta != null)
		{
			//redireccionar
			ejecutarGlobalCommand("verOferta", parametros);
			
		}
		else
		{
			
			
			//antes cerrar formulario de oferta
			
			llamarFormulario("formularioSolicituddePedido.zul", parametros);
			
		}
		}
		
	}
	
	
	private void llenarListAprobados() {
		
		for ( DetalleOferta detalleOferta : this.oferta.getDetalleOfertas())
		{
			if (detalleOferta.getAprobado() != null)
				
			{
				if(detalleOferta.getAprobado()) 
					listaDetOferta.add(detalleOferta);
				
				
			}
		}
			
	}
	
	
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/"+ruta, parametros);
	}
	
	
	
	protected Oferta registrarOferta(boolean enviarEmail)
	{
	
		oferta = sTransaccion.actualizarOferta(oferta);

		if(enviarEmail){
			Map<String, Object> model = new HashMap<String, Object>();
			 
			

			//mailService.send(oferta.getCorreo(), "Oferta Enviada al Analista", "enconstruccion.html", model);
			
			String str = "Su Oferta Ha sido Enviada Exitosamente para ser confirmada, Se Enviara su pedido en 48 Horas ";

			mostrarMensaje("Informacion", str, null, null,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							redireccionar("/");
						}
					}, null);
		}
		else {
			winOferta.onClose();
		}
		return oferta;
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

