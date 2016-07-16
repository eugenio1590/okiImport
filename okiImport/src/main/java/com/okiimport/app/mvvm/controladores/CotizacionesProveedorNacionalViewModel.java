package com.okiimport.app.mvvm.controladores;

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
//
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
//


import com.okiimport.app.model.Cotizacion;
import com.okiimport.app.model.DetalleCotizacion;
//
import com.okiimport.app.model.Persona;
import com.okiimport.app.model.Requerimiento;
//
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.constraint.CustomConstraint;
//
import com.okiimport.app.mvvm.resource.BeanInjector;
//
import com.okiimport.app.service.transaccion.STransaccion;

public class CotizacionesProveedorNacionalViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@Wire("#gridCotizaciones")
	private Listbox gridCotizaciones;
	
	@Wire("#pagCotizaciones")
	private Paging pagCotizaciones;
	
	

	//Atributos
	public static final String TITULO_EMPTY_COTIZACIONES = "No existen mas solicituces de cotizacion";
	private String titulo = "Solicitudes de Cotizacion del Requerimiento N° ";
	private CustomConstraint constraintPrecioFlete = null;
	private List<Cotizacion> listaCotizacion;
	private Persona persona;
	private Requerimiento requerimiento;
	private Cotizacion cotizacionFiltro;
	private Cotizacion cotizacionSelecionada=null;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("persona") Persona persona, 
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento){
		super.doAfterCompose(view);
		
		this.persona = persona;
		this.requerimiento = requerimiento;
		this.requerimiento.especificarInformacionVehiculo();
		cotizacionFiltro = new Cotizacion((Date) null);
		titulo = titulo + requerimiento.getIdRequerimiento();
		cambiarCotizaciones(0, null, null);
		
		agregarGridSort(gridCotizaciones);
		gridCotizaciones.setEmptyMessage(TITULO_EMPTY_COTIZACIONES);
		pagCotizaciones.setPageSize(pageSize);
		
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaCotizacion")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarCotizaciones", parametros );
		}
	}
	
	/**GLOBAL COMMAND*/
	 /**
	 * Descripcion: permitira cambiar las cotizaciones de la grid de acuerdo a la pagina dada como parametro
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaCotizacion")
	public void cambiarCotizaciones(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.consultarSolicitudCotizaciones(cotizacionFiltro, fieldSort, 
				sortDirection, requerimiento.getIdRequerimiento(), persona.getId(), page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaCotizacion = (List<Cotizacion>) parametros.get("cotizaciones");
		pagCotizaciones.setActivePage(page);
		pagCotizaciones.setTotalSize(total);
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: permite cambiar la paginacion de acuerdo a la pagina activa
	 * de Paging 
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagCotizaciones.getActivePage();
		cambiarCotizaciones(page, null, null);
	}
	
	/**
	 * Descripcion: permite filtrar los datos de la grid de acuerdo al campo
	 * establecido en el evento 
	 * Parametros: @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Ninguno
	 * Nota:Ninguna
	 * */
	@Command
	@NotifyChange("listaCotizacion")
	public void aplicarFiltro(){
		cambiarCotizaciones(0, null, null);
	}
	
	/**
	 * Descripcion: permite cargar la lista de detalles de la cotizacion seleccionada
	 * Parametros: requerimiento seleccionado @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion","cotizacionSelecionada"})
	public void cotizar(@BindingParam("cotizacion") Cotizacion cotizacion){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("persona", this.persona);
		parametros.put("requerimiento", this.requerimiento);
		parametros.put("cotizacion", cotizacion);
		parametros.put("obligatorioTodosCampos", false);
		crearModal(BasePackageSistemaFunc+"en_proceso/cotizarProveedorNacional.zul", parametros);
	}
	/**
	 * Descripcion: Permitira cargar nuevamente las listas al cerrar la pantalla
	 * Parametros: Ninguno @param view: listaCotizacionesProveedorNacional.zul 
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@Command
	public void onCloseWindow(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
		ejecutarGlobalCommand("cambiarCotizaciones", null);
		ejecutarGlobalCommand("cambiarProveedores", null);
	}
	
	@Command
	@NotifyChange("listaDetalleCotizacion")
	public void QuitarCotizacion(@BindingParam("detalle") DetalleCotizacion detalle){
		detalle.setVisible(false);
		detalle.setCantidad((long) 0);
		
	}
	
	/**METODOS PROPIOS Y DE LA CLASE*/
	
	/**SETTERS Y GETTERS*/	
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<Cotizacion> getListaCotizacion() {
		return listaCotizacion;
	}

	public void setListaCotizacion(List<Cotizacion> listaCotizacion) {
		this.listaCotizacion = listaCotizacion;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Cotizacion getCotizacionSelecionada() {
		return cotizacionSelecionada;
	}

	public void setCotizacionSelecionada(Cotizacion cotizacionSelecionada) {
		this.cotizacionSelecionada = cotizacionSelecionada;
	}

	public Cotizacion getCotizacionFiltro() {
		return cotizacionFiltro;
	}

	public void setCotizacionFiltro(Cotizacion cotizacionFiltro) {
		this.cotizacionFiltro = cotizacionFiltro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public CustomConstraint getConstraintPrecioFlete() {
		return constraintPrecioFlete;
	}

	public void setConstraintPrecioFlete(CustomConstraint constraintPrecioFlete) {
		this.constraintPrecioFlete = constraintPrecioFlete;
	}
}
