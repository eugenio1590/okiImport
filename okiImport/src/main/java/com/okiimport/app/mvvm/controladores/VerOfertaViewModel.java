package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class VerOfertaViewModel extends AbstractRequerimientoViewModel {
	
    //Servicios
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
    
    //GUI
    @Wire("#winOferta")
	private Window winOferta;
	
    //Atributos
	private Requerimiento requerimiento;
	private List<DetalleOferta> listaDetOferta;
    private Oferta oferta;
    
    /**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: formularioOferta.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento, 
			@ExecutionArgParam("detallesOfertas") List<DetalleOferta> listaDetOferta )
	{	
		super.doAfterCompose(view);	
		this.requerimiento = requerimiento;
		this.listaDetOferta = (listaDetOferta == null) ? new ArrayList<DetalleOferta>() : listaDetOferta;
		cargarOferta();
		
	}
	
	/**COMMAND
	/* Descripcion: Permitira limpiar el campo aceptar de cada uno de los repuestos de la oferta
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("oferta")
	public void limpiar(){
		for ( DetalleOferta detalleOferta : this.oferta.getDetalleOfertas()){
			detalleOferta.setAprobado(null);
		}
	}
	
	/**
	 * Descripcion: Permite Cargar La Oferta
	 * Parametros: @param view: formularioOferta.zul 
	 * Retorno: Oferta Cargada
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("oferta")
	public void cargarOferta(){
		
		oferta = sTransaccion.consultarOfertaEnviadaPorRequerimiento(requerimiento.getIdRequerimiento());
	
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: Permite Registrar Una Oferta
	 * Parametros: @param btnEnviar: boton presionado
	 * Retorno: Oferta Registrada
	 * Nota: Ninguna
	 * */
	@Command
	public void registrar(@BindingParam("btnEnviar") Button btnEnviar) {		
		if ( checkIsFormValid() ) {
			
			oferta.setEstatus("recibida");
			llenarListAprobados();
			oferta = sTransaccion.actualizarOferta(oferta);
			//sTransaccion.actualizarRequerimiento(requerimiento);  Falta definir estatus
			cargarOferta();
			
			final Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("requerimiento", requerimiento);
			parametros.put("detallesOfertas", listaDetOferta);


			if (oferta != null)
			{
				super.mostrarMensaje("Informaci\u00F3n", "¿Desea continuar viendo mas ofertas?", null, 
						new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO, Messagebox.Button.CANCEL}, new EventListener<Event>(){
							@Override
							public void onEvent(Event event) throws Exception {
								Messagebox.Button button = (Messagebox.Button) event.getData();
								if (button == Messagebox.Button.YES) {
									ejecutarGlobalCommand("verOferta", parametros);
									winOferta.onClose();
								}
								else if(button == Messagebox.Button.NO )
									if(listaDetOferta.size()>0)
										redireccionarASolicitudDePedido(parametros);
									else 
										reactivarRequerimiento();
							}
				}, null);
				
			}
			else {
				if(listaDetOferta.size()>0)
					redireccionarASolicitudDePedido(parametros);
				else
					reactivarRequerimiento();
			}
		}
	}
	
	/**
	 * Descripcion: Permite Aprobar el detalle de la oferta
	 * Parametros: @param checkbox: componente de la vista checkbox que fue presionado
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void aprobarDetalleOferta(@ContextParam(ContextType.COMPONENT) Checkbox checkbox,
			@BindingParam("detalleOferta") DetalleOferta detalleOferta)
	{
		
		detalleOferta.setAprobado(checkbox.isChecked());
	}
	
	/**METODOS PRIVADOS DE LA CLASE*/
	/**
	 * Descripcion: Permite llenar la lista con las ofertas aprobadas
	 * Parametros: Ninguno.
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void llenarListAprobados() {
		for ( DetalleOferta detalleOferta : this.oferta.getDetalleOfertas()){
			if (detalleOferta.getAprobado() != null && detalleOferta.getAprobado()){
				listaDetOferta.add(detalleOferta);
			}
		}
	}
	
	/**
	 * Descripcion: Permitira redirigir a la pantalla de solicitud de pedido
	 * Parametros: @param parametros: parametros a pasar al .zul
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 */
	private void redireccionarASolicitudDePedido(Map<String, Object> parametros){
		this.winOferta.onClose();
		this.crearModal(BasePackagePortal+"formularioSolicituddePedido.zul", parametros);
	}
	
	/**
	 * Descripcion: Permitira reactivar el requerimiento con otro analista
	 * Parametros: Ninguno.
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void reactivarRequerimiento(){
		super.mostrarMensaje("Informaci\u00F3n", "¿Desea que volvamos a reactivar su requerimiento?", null, 
				new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO, Messagebox.Button.CANCEL}, new EventListener<Event>(){
					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.Button button = (Messagebox.Button) event.getData();
						if (button == Messagebox.Button.YES) {
							ejecutarGlobalCommand("cambiarRequerimientos", null);
							sTransaccion.reactivarRequerimiento(requerimiento, sMaestros);
							winOferta.onClose();
						}
						else if(button == Messagebox.Button.NO )
							mostrarMensaje("Informaci\u00F3n", "", null, null, null, null);
					}
		}, null);
	}
	
	/**GETTERS Y SETTERS*/
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

	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}
	
}
