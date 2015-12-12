package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;

import com.okiimport.app.model.Ciudad;
import com.okiimport.app.model.Cotizacion;
import com.okiimport.app.model.DetalleCotizacion;
import com.okiimport.app.model.DetalleRequerimiento;
import com.okiimport.app.model.Estado;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.transaccion.STransaccion;

public class ListaCotizacionesSeleccionadasViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	private List<DetalleCotizacion> listaDetalleSeleccion;
	private List<DetalleCotizacion> listaDetalleSeleccionado;
	private List<DetalleCotizacion> eliminarDetalle;

	// GUI
	@Wire("#gridDetalleSeleccion")
	private Listbox gridDetalleSeleccion;

	//Atributos
	private static final Comparator<DetalleCotizacion> COMPR_DETALLE_COTIZACION = DetalleCotizacion.getComparator();
	
	private String titulo = "Repuestos Seleccionados del Requerimiento N° ";
	private Requerimiento requerimiento;
	private DetalleCotizacion detalleCotizacionFiltro;
	private DetalleCotizacion detalleCotizacion;
	private String ubicacion;
	private int cantOfertas;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento,
			@ExecutionArgParam("listaDetalleSeleccion")  List<DetalleCotizacion> listaDetalleSeleccionado) {
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.titulo = this.titulo + requerimiento.getIdRequerimiento();
		this.listaDetalleSeleccionado = listaDetalleSeleccionado;
		detalleCotizacionFiltro = new DetalleCotizacion(new Cotizacion(
				new Proveedor()), new DetalleRequerimiento());
		detalleCotizacionFiltro.eliminarPrecios();
		listaDetalleSeleccionado = new ArrayList<DetalleCotizacion>();
		cantOfertas = sTransaccion.consultarCantOfertasCreadasPorRequermiento(requerimiento.getIdRequerimiento());
		
	}

	/** Interface: EventListener<SortEvent> */
	@Override
	@NotifyChange("listaRequerimientos")
	public void onEvent(SortEvent event) throws Exception {
		if (event.getTarget() instanceof Listheader) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget())
					.getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("consultarDetalleCotizacion", parametros);
		}

	}


	/**
	 * Descripcion: Elimina la seleccion de listaDetalleSeleccion 
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno
	 * Nota:Ninguna
	 * */
	@Command
	@NotifyChange({ "*" })
	public void eliminarSeleccion() {
		if(listaDetalleSeleccion!=null && !listaDetalleSeleccion.isEmpty()){
			listaDetalleSeleccionado.removeAll(listaDetalleSeleccion);
		}
		else 
			mostrarMensaje("Informaci\u00F3n", "Seleccione al menos un item", null, null, null, null);
	}

	/**
	 * Descripcion: Guarda la Seleccion realizada en listaDetalleSeleccionado
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({ "*" })
	public void guardar() {
		if (cantOfertas == 3){
			
			mostrarMensaje("Informaci\u00F3n", "Ya se alcanz\u00F3 el n\u00FAmero m\u00E1ximo de ofertas", null, null, null, null);
		}
		else {
			if(listaDetalleSeleccionado!=null && !listaDetalleSeleccionado.isEmpty()){
				sTransaccion.guardarSeleccionRequerimiento(listaDetalleSeleccionado);
				cantOfertas++;
				limpiarDetalleSeleccionado();
				mostrarMensaje("Informaci\u00F3n", "Selecci\u00F3n "+cantOfertas+" Guardada Exitosamente", null,
						null, null, null);
			}
			else 
				mostrarMensaje("Informaci\u00F3n", "Seleccione al menos un item", null, null, null, null);
		}
	}

	/**
	 * Descripcion: Permitira limpiar la informacion de la seleccion
	 * Parametros: Ninguno @param view: aprobarCotizaciones.zul  
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "listaDetalleSeleccionado" })
	private void limpiarDetalleSeleccionado(){
		listaDetalleSeleccionado.clear();
	}

	/**METODOS PROPIOS DE LA CLASE*/
	/** SETTERS Y GETTERS */
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public DetalleCotizacion getDetalleCotizacionFiltro() {
		return detalleCotizacionFiltro;
	}

	public void setDetalleCotizacionFiltro(
			DetalleCotizacion detalleCotizacionFiltro) {
		this.detalleCotizacionFiltro = detalleCotizacionFiltro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<DetalleCotizacion> getListaDetalleSeleccion() {
		return listaDetalleSeleccion;
	}

	public void setListaDetalleSeleccion(
			List<DetalleCotizacion> listaDetalleSeleccion) {
		this.listaDetalleSeleccion = listaDetalleSeleccion;
	}

	public List<DetalleCotizacion> getListaDetalleSeleccionado() {
		return listaDetalleSeleccionado;
	}

	public void setListaDetalleSeleccionado(
			List<DetalleCotizacion> listaDetalleSeleccionado) {
		this.listaDetalleSeleccionado = listaDetalleSeleccionado;
	}

	public DetalleCotizacion getDetalleCotizacion() {
		return detalleCotizacion;
	}

	public void setDetalleCotizacion(DetalleCotizacion detalleCotizacion) {
		this.detalleCotizacion = detalleCotizacion;
	}

	public List<DetalleCotizacion> getEliminarDetalle() {
		return eliminarDetalle;
	}

	public void setEliminarDetalle(List<DetalleCotizacion> eliminarDetalle) {
		this.eliminarDetalle = eliminarDetalle;
	}
}