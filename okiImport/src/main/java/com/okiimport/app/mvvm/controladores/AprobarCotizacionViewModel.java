package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
import com.okiimport.app.resource.service.AbstractServiceImpl;
import com.okiimport.app.service.configuracion.SControlUsuario;
import com.okiimport.app.service.transaccion.STransaccion;

public class AprobarCotizacionViewModel extends AbstractRequerimientoViewModel
		implements EventListener<SortEvent> {

	// Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;

	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;

	private List<DetalleCotizacion> listaDetalleCotizacion;
	private List<DetalleCotizacion> listaDetalleSeleccion;
	private List<DetalleCotizacion> listaDetalleSeleccionado;
	private List<DetalleCotizacion> eliminarDetalle;

	// GUI
	@Wire("#gridDetalleCotizacion")
	private Listbox gridDetalleCotizacion;
	@Wire("#gridDetalleSeleccion")
	private Listbox gridDetalleSeleccion;

	@Wire("#pagDetalleCotizacion")
	private Paging pagDetalleCotizacion;

	//Atributos
	private static final Comparator<DetalleCotizacion> COMPR_DETALLE_COTIZACION = DetalleCotizacion.getComparator();
	
	private String titulo = "Repuestos Cotizados del Requerimiento N° ";
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
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento) {
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.titulo = this.titulo + requerimiento.getIdRequerimiento();
		detalleCotizacionFiltro = new DetalleCotizacion(new Cotizacion(
				new Proveedor()), new DetalleRequerimiento());
		detalleCotizacionFiltro.eliminarPrecios();
		listaDetalleSeleccionado = new ArrayList<DetalleCotizacion>();

		consultarDetalleCotizacion(0, null, null);
		agregarGridSort(gridDetalleCotizacion);
		pagDetalleCotizacion.setPageSize(pageSize=5);
		cantOfertas = sTransaccion.consultarCantOfertasCreadasPorRequermiento(requerimiento.getIdRequerimiento());
	}

	/** Interface: EventListener<SortEvent> */
	@Override
	@NotifyChange("listaRequerimientos")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub
		if (event.getTarget() instanceof Listheader) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget())
					.getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("consultarDetalleCotizacion", parametros);
		}

	}

	/**
	 * Descripcion: Mueve la seleccion realizada desde listaDetalleCotizacion a
	 * ListaDetalleSeleccionado 
	 * Parametros: @param view: aprobarCotizaciones.zul
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void agregarSeleccion(){
		if(listaDetalleSeleccion!=null && !listaDetalleSeleccion.isEmpty()){
		super.moveSelection(listaDetalleCotizacion, listaDetalleSeleccionado, listaDetalleSeleccion, 
				COMPR_DETALLE_COTIZACION, false, "No se puede agregar Detalle Cotizacion");
		}
		else 
			mostrarMensaje("Informaci\u00F3n", "Seleccione al menos un item", null, null, null, null);

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
		if(listaDetalleSeleccion!=null && !listaDetalleSeleccion.isEmpty())
			listaDetalleSeleccionado.removeAll(listaDetalleSeleccion);
	}

	/**
	 * Descripcion: Guarda la Seleccion realizada en listaDetalleSeleccionado
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@NotifyChange({ "*" })
	@Command
	public void guardar() {
		if (cantOfertas == 3){
			
			mostrarMensaje("Informaci\u00F3n", "Ya se alcanz\u00F3 el n\u00FAmero m\u00E1ximo de ofertas", null, null, null, null);
		}
		else {
			if(listaDetalleSeleccion!=null && !listaDetalleSeleccion.isEmpty()){
				sTransaccion.guardarSeleccionRequerimiento(listaDetalleSeleccionado);
				cantOfertas++;
				limpiarDetalleSeleccionado();
				mostrarMensaje("Informaci\u00F3n", "Selecci\u00F3n"+cantOfertas+"Guardada Exitosamente", null,
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
	@NotifyChange({ "*" })
	private void limpiarDetalleSeleccionado(){
		if(listaDetalleSeleccion!=null && !listaDetalleSeleccion.isEmpty()){
		if(listaDetalleSeleccionado!=null){
			listaDetalleSeleccionado.removeAll(listaDetalleSeleccion);
		}
		}
		else 
			mostrarMensaje("Informaci\u00F3n", "Seleccione al menos un item", null, null, null, null);
	}
	

	/**
	 * Descripcion: Consulta el detalle Cotizacion 
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@NotifyChange("*")
	public void consultarDetalleCotizacion(
			@Default("0") @BindingParam("page") int page,
			@BindingParam("fieldSort") String fieldSort,
			@BindingParam("sortDirection") Boolean sortDirection) {

		/** FALTA FILTRAR POR ESTATUS DE DETALLE COTIZACION */
		Map<String, Object> parametros = sTransaccion
				.consultarDetallesCotizacion(detalleCotizacionFiltro,
						requerimiento.getIdRequerimiento(), fieldSort,
						sortDirection, page, pageSize);

		listaDetalleCotizacion = (List<DetalleCotizacion>) parametros
				.get("detallesCotizacion");
		Integer total = (Integer) parametros.get("total");
		gridDetalleCotizacion.setMultiple(true);
		gridDetalleCotizacion.setCheckmark(true);
		pagDetalleCotizacion.setActivePage(page);
		pagDetalleCotizacion.setTotalSize(total);
	}

	/** COMMAND */

	/**
	 * Descripcion: permite cambiar la paginacion de acuerdo a la pagina activa
	 * de Paging 
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista() {
		int page = pagDetalleCotizacion.getActivePage();
		consultarDetalleCotizacion(page, null, null);
	}

	/**
	 * Descripcion: permite filtrar los datos de la grid de acuerdo al campo
	 * establecido en el evento 
	 * Parametros: @param view: aprobarCotizaciones.zul
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("listaDetalleCotizacion")
	public void aplicarFiltro() {
		agregarUbicacion();
		consultarDetalleCotizacion(0, null, null);
	}

	/** METODOS PROPIOS DE LA CLASE */

	/**
	 * Descripcion: Asigna la ubicacion del proveedor 
	 * Parametros: @param view: aprobarCotizaciones.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	private void agregarUbicacion() {
		Proveedor proveedor = this.detalleCotizacionFiltro.getCotizacion()
				.getProveedor();
		proveedor.setCiudad(new Ciudad(ubicacion, new Estado(ubicacion)));
	}

	/**METODOS PROPIOS DE LA CLASE*/
	/** SETTERS Y GETTERS */
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}

	public DetalleCotizacion getDetalleCotizacionFiltro() {
		return detalleCotizacionFiltro;
	}

	public void setDetalleCotizacionFiltro(
			DetalleCotizacion detalleCotizacionFiltro) {
		this.detalleCotizacionFiltro = detalleCotizacionFiltro;
	}

	public List<DetalleCotizacion> getListaDetalleCotizacion() {
		return listaDetalleCotizacion;
	}

	public void setListaDetalleCotizacion(
			List<DetalleCotizacion> listaDetalleCotizacion) {
		this.listaDetalleCotizacion = listaDetalleCotizacion;
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
