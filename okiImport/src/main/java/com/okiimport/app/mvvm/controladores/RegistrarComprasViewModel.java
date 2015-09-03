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
import com.okiimport.app.modelo.Compra;
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



public class RegistrarComprasViewModel extends AbstractRequerimientoViewModel 
{
	
	@Wire("#winCompras")
	private Window winCompras;
	
	private Requerimiento requerimiento;

    private Compra compra;
    private DetalleOferta detalleOferta;
    private Cotizacion cotizacion;
    
    private boolean cerrar;
    
    @BeanInjector("sMaestros")
	private SMaestros sMaestros;
    
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
    private List <Oferta> listaOferta;
    
    private List<ModeloCombo<Boolean>> listaTipoRepuesto;
    
    
	//	private List <Requerimiento> listaRequerimientos;
    
    
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento)
			//Lo que obtenemos de la lista es un requerimiento no una oferta
	{	
		super.doAfterCompose(view);	
		this.requerimiento = requerimiento;
		//cargarOferta();
	    //aca llamamos es al servicio y buscamos la oferta de acuerdo al requerimiento
	   //se puede hacer en un metodo aparte para que se pueda usar mas adelante
		
	}
	
	/*/**GLOBAL COMMAND*/
	/*@GlobalCommand
	@NotifyChange("oferta")
	public void cargarOferta(){
		
		//compra = sTransaccion.consultarOfertaEnviadaPorRequerimiento(requerimiento.getIdRequerimiento());

	}
	*/
	
	/*@Command
	@NotifyChange({ "oferta" })
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar) {
		
		//1ero Actualizar Estatus de la Oferta
		
		//compra.setEstatus("recibida");
		//sTransaccion.actualizarOferta(oferta); // Se implementara la cascada
		
		/*if (checkIsFormValid()) {

			if (oferta.getDetalleOfertas().size() > 0)
			{
				
				btnEnviar.setDisabled(true);
				
				registrarOferta(cerrar);
			}

			else
				mostrarMensaje("Informaci�n", "Desea Revisar Otra Oferta?",
						null, null, null, null);

		}
	}*/
	
	
	
	/*protected Oferta registrarOferta(boolean enviarEmail)
	{
	
		compra = sTransaccion.actualizarOferta(compra);

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
			winCompras.onClose();
		}
		return oferta;
	}*/

	

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	

	public Window getWinCompras() {
		return winCompras;
	}

	public void setWinCompras(Window winCompras) {
		this.winCompras = winCompras;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
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
