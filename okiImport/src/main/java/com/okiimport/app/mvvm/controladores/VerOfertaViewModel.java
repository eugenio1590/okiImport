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
import org.zkoss.zul.A;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.model.enumerados.EEstatusOferta;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.configuracion.SControlUsuario;
import com.okiimport.app.service.transaccion.STransaccion;

public class VerOfertaViewModel extends AbstractRequerimientoViewModel {
	
	//Enum de la clase que contiene los enumerados del carrito de compra
	public enum Carrito {
		AGREGAR_CARRITO("fa fa-cart-arrow-down bigger-200 cyan", "Agregar respuesto al carrito"),
		QUITAR_CARRITO("fa fa-share bigger-200 cyan", "Quitar repuesto del carrito");
		
		private String css;
		private String tool;
		
		Carrito(String css, String tool){
			this.css=css;
			this.tool=tool;
		}

		public String getCss() {
			return css;
		}

		public String getTool() {
			return tool;
		}
	}
	
	//Servicios
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;
	
    @BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
    
    //GUI
    @Wire("#winOferta")
	private Window winOferta;
    
    //Variables Estaticas
    public static final Carrito AGREGAR_CARRITO = Carrito.AGREGAR_CARRITO;
    public static final Carrito QUITAR_CARRITO = Carrito.QUITAR_CARRITO;
	
    //Atributos
	private Requerimiento requerimiento;
	private List<DetalleOferta> listaDetOferta;
    private Oferta oferta;
    
    private Map<String, Object> parametros;
    private boolean cerrar = false;
    private int cantArticulos;
    
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
		this.parametros = new HashMap<String, Object>();
		this.requerimiento = requerimiento;
		this.listaDetOferta = (listaDetOferta == null) ? new ArrayList<DetalleOferta>() : listaDetOferta;
		this.cantArticulos = this.listaDetOferta.size();
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
		List<DetalleRequerimiento> detallesRequerimiento = obtenerDetallesRequerimientos();
		oferta = sTransaccion.consultarOfertaEnviadaPorRequerimiento(requerimiento.getIdRequerimiento(), detallesRequerimiento);
	
	}
	
	/**
	 * Descripcion: Permite Registrar Una Oferta
	 * Parametros: @param btnEnviar: boton presionado
	 * Retorno: Oferta Registrada
	 * Nota: Ninguna
	 * */
	@Command
	public void enviar() {		
		if ( checkIsFormValid() ) {
			
			oferta.setEstatus(EEstatusOferta.RECIBIDA);
			llenarListAprobados();
			oferta = sTransaccion.actualizarOferta(oferta);
			//sTransaccion.actualizarRequerimiento(requerimiento);  Falta definir estatus
			boolean seguir = true;
			while(seguir){
				cargarOferta();
				
				if(oferta==null) {
					seguir = false;
				}
				else if(oferta.getDetalleOfertas().size()==0){
					oferta.setEstatus(EEstatusOferta.INVALIDA);
					sTransaccion.actualizarOferta(oferta);
					seguir = true;
					oferta = null;
				}
				else {
					seguir = false;
				}
			}
			
			parametros.clear();
			parametros.put("requerimiento", requerimiento);
			parametros.put("detallesOfertas", listaDetOferta);

			if (oferta != null)
				verMasOfertas();
			else {
				if(listaDetOferta.size()>0)
					redireccionarASolicitudDePedido(parametros);
				else
					reactivarRequerimiento();
			}
		}
	}
	
	/**
	 * Descripcion: Permitira mostrar mas ofertas en caso de que existan.
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void verMasOfertas(){
		super.mostrarMensaje("Informaci\u00F3n", "¿Realmente desea continuar viendo mas ofertas?", null, 
				new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO, Messagebox.Button.CANCEL}, new EventListener<Event>(){
					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.Button button = (Messagebox.Button) event.getData();
						if (button == Messagebox.Button.YES) {
							cerrar = true;
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
	
	/**
	 * Descripcion: permitira quitar los articulos seleccionados del carrito de compra.
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"oferta", "cantArticulos"})
	public void vaciarCarrito(){
		for ( DetalleOferta detalleOferta : this.oferta.getDetalleOfertas()){
			if (detalleOferta.getAprobado() != null && detalleOferta.getAprobado()){
				detalleOferta.setAprobado(false);
				cantArticulos--;
			}
		}
	}
	
	/**
	 * Descripcion: Permite Aprobar el detalle de la oferta
	 * Parametros: @param a: componente de la vista A que fue presionado
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"oferta", "cantArticulos"})
	public void aprobarDetalleOferta(@ContextParam(ContextType.COMPONENT) A a,
			@BindingParam("detalleOferta") DetalleOferta detalleOferta)
	{
		boolean aprobado = (a.getSclass().equalsIgnoreCase(AGREGAR_CARRITO.getCss())) ? true : false;
		detalleOferta.setAprobado(aprobado);
		if(aprobado)
			cantArticulos++;
		else
			cantArticulos--;
	}
	
	/**
	 * Descripcion: Evento que se ejecuta al cerrar la ventana y que valida si el proceso actual de la compra se perdera o no
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	public void onCloseWindow(@ContextParam(ContextType.TRIGGER_EVENT) Event onClose){
		if(!cerrar){
			onClose.stopPropagation();
			super.mostrarMensaje("Informaci\u00F3n", "Si cierra la ventana el proceso realizado se perdera, ¿Desea continuar?", null, 
					new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){
				@Override
				public void onEvent(Event event) throws Exception {
					Messagebox.Button button = (Messagebox.Button) event.getData();
					if (button == Messagebox.Button.YES) {
						cerrar = true;
						requerimiento.cerrarSolicitud();
						sTransaccion.actualizarRequerimiento(requerimiento);
						ejecutarGlobalCommand("cambiarRequerimientos", null);
						winOferta.onClose();
					}
				}
			}, null);
		}
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
	private void redireccionarASolicitudDePedido(final Map<String, Object> parametros){
		super.mostrarMensaje("Informaci\u00F3n", "No existen mas ofertas para mostrar, ¿Desea continuar con la solicitud de pedido?", null, 
				new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.Button button = (Messagebox.Button) event.getData();
				if (button == Messagebox.Button.YES) {
					cerrar = true;
					winOferta.onClose();
					crearModal(BasePackagePortal+"formularioSolicituddePedido.zul", parametros);
				}
				else
					mostrarMensaje("Informaci\u00F3n", "El proceso realizado se perdera, ¿Desea continuar?", null, 
							new Messagebox.Button[]{Messagebox.Button.YES, Messagebox.Button.NO}, new EventListener<Event>(){

								@Override
								public void onEvent(Event event) throws Exception {
									Messagebox.Button button = (Messagebox.Button) event.getData();
									if (button == Messagebox.Button.YES)
										cerrarRequerimiento();
								}
					}, null);
			}
		}, null);
	}
	
	/**
	 * Descripcion: Obtendra todos los objetos detalles requerimiento de los objetos detalle oferta seleccionados
	 * Parametros: Ninguno
	 * Retorno: @return detallesRequerimiento: lista de los detalles requerimientos asociados 
	 * a los objetos detalle oferta seleccionados
	 * Nota: Ninguna
	 * */
	private List<DetalleRequerimiento> obtenerDetallesRequerimientos(){
		List<DetalleRequerimiento> detallesRequerimiento = null;
		if(listaDetOferta!=null && !listaDetOferta.isEmpty()){
			detallesRequerimiento = new ArrayList<DetalleRequerimiento>();
			for(DetalleOferta detalleO : listaDetOferta){
				detallesRequerimiento.add(detalleO.getDetalleCotizacion().getDetalleRequerimiento());
			}
			 
		}
		return detallesRequerimiento;
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
							cerrarVentana();
						}
						else if(button == Messagebox.Button.NO )
							cerrarRequerimiento();
					}
		}, null);
	}
	
	/**
	 * Descripcion: permitira cerrar el requerimiento asociado a la oferta.
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void cerrarRequerimiento(){
		sTransaccion.cerrarRequerimiento(requerimiento, sMaestros, sControlUsuario, false);
		mostrarMensaje("Informaci\u00F3n", "Requerimiento Cerrado!", null, null, new EventListener<Event>(){
			@Override
			public void onEvent(Event event) throws Exception {
				cerrarVentana();
			}	
		}, null);
	}
	
	/**
	 * Descripcion: metodo que actualiza la variable cerrar y llama al comman respectivo al cerrar la ventana.
	 * Parametros: Ninguno
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	private void cerrarVentana(){
		cerrar = true;
		winOferta.onClose();
	}
	
	/**GETTERS Y SETTERS*/
	public Carrito getAgregarCarrito() {
		return AGREGAR_CARRITO;
	}

	public Carrito getQuitarCarrito() {
		return QUITAR_CARRITO;
	}
	
	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}
	
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
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

	public int getCantArticulos() {
		return cantArticulos;
	}

	public void setCantArticulos(int cantArticulos) {
		this.cantArticulos = cantArticulos;
	}
	
}
