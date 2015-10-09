package com.okiimport.app.mvvm.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
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

import com.okiimport.app.model.Cotizacion;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.model.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.configuracion.SControlUsuario;
import com.okiimport.app.service.transaccion.STransaccion;

public class CotizacionesViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	@BeanInjector("sControlUsuario")
	private SControlUsuario sControlUsuario;

	private List <Cotizacion> listaCotizaciones;
	private List<Cotizacion> listaCotizacionesSeleccionadas;
	
	//GUI
	@Wire("#gridCotizaciones")
	private Listbox gridCotizaciones;
	
	@Wire("#pagCotizaciones")
	private Paging pagCotizaciones;
	
	//Atributos
	private Usuario usuario;
	private Cotizacion cotizacionFiltro;
	private Requerimiento requerimiento;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: cotizaciones.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento")Requerimiento requerimiento){
		super.doAfterCompose(view);
		UserDetails user = this.getUser();
		cotizacionFiltro = new Cotizacion();
		cotizacionFiltro.setProveedor(new Proveedor());
		listaCotizacionesSeleccionadas=new ArrayList<Cotizacion>();
		this.requerimiento = requerimiento;
		usuario = sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
		cambiarCotizaciones(0, null, null);
		agregarGridSort(gridCotizaciones);
		pagCotizaciones.setPageSize(pageSize);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("listaRequerimientosCotizados")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort", ((Listheader) event.getTarget()).getValue().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarRequerimientos", parametros );
		}
		
	}
	
	/**GLOBAL COMMAND*/
	/**
	 * Descripcion: permitira cambiar las cotizaciones de la grid de acuerdo a la pagina dada como parametro
	 * Parametros: @param view: cotizaciones.zul 
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno 
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaRequerimientosCotizados")
	public void cambiarCotizaciones(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.ConsultarCotizacionesRequerimiento(cotizacionFiltro, fieldSort, sortDirection, 
				requerimiento.getIdRequerimiento(), page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaCotizaciones = (List<Cotizacion>) parametros.get("cotizaciones");
		gridCotizaciones.setMultiple(true);
		gridCotizaciones.setCheckmark(true);
		pagCotizaciones.setActivePage(page);
		pagCotizaciones.setTotalSize(total);
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: permite cambiar la paginacion de acuerdo a la pagina activa
	 * de Paging 
	 * Parametros: @param view: cotizaciones.zul 
	 * Retorno: posicionamiento en otra pagina activa del paging 
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
	 * Parametros: @param view: cotizaciones.zul 
	 * Retorno: filtro de acuerdo al campo establecido en el evento 
	 * Nota:Ninguna
	 * */
	@Command
	@NotifyChange("listaCotizaciones")
	public void aplicarFiltro(){
		cambiarCotizaciones(0, null, null);
	}
	
	/**
	 * Descripcion: permite crear el emergente (modal) necesario para editar el requerimiento seleccionado
	 * establecido en el evento 
	 * Parametros: requerimiento seleccionado @param view: cotizaciones.zul 
	 * Retorno: emergente (modal) creado.
	 * Nota:Ninguna
	 * */
	@Command
	public void editarReguerimiento(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		crearModal(BasePackageSistemaFunc+"emitidos/editarRequerimiento.zul", parametros);
	}
	
	/**
	 * Descripcion: permite crear el emergente (modal) necesario para ver el requerimiento seleccionado
	 * establecido en el evento 
	 * Parametros: requerimiento seleccionado @param view: cotizaciones.zul 
	 * Retorno: emergente (modal) creado.
	 * Nota:Ninguna
	 * */
	@Command
	public void verDetalleCotizacion(@BindingParam("cotizacion") Cotizacion cotizacion){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cotizacion", cotizacion);
		crearModal(BasePackageSistemaFunc+"detalleCotizacion.zul", parametros);
	}
	
	/**
	 * Descripcion: permite guardar la cotizacion 
	 * Parametros: cotizacion seleccionada @param view: cotizaciones.zul 
	 * Retorno: cambio estatus a la cotizacion, mensaje emergente.
	 * Nota:Ninguna
	 * */
	@NotifyChange("*")
	@Command
	public void aprobar(@BindingParam("cotizacion") Cotizacion cotizacion){
		
		if (cotizacion != null){
			cotizacion.setEstatus("A");
			sTransaccion.ActualizarCotizacion(cotizacion);
			cambiarCotizaciones(0, null, null);
		}
		
		else if (listaCotizacionesSeleccionadas.size() > 0) { 
			for (Cotizacion cotizacionSeleccionada:listaCotizacionesSeleccionadas){
				cotizacionSeleccionada.setEstatus("A");
				sTransaccion.ActualizarCotizacion(cotizacionSeleccionada);
			}
			cambiarCotizaciones(0, null, null);
			listaCotizacionesSeleccionadas.clear();
		}
		else mostrarMensaje("Informaci\u00F3n","Seleccione al menos una Cotizaci\u00F3n",null,null,null,null);
	}
	
	/**SETTERS Y GETTERS*/
	public STransaccion getsTransaccion() {
		return sTransaccion;
	}

	public void setsTransaccion(STransaccion sTransaccion) {
		this.sTransaccion = sTransaccion;
	}

	public List<Cotizacion> getListaCotizaciones() {
		return listaCotizaciones;
	}

	public void setListaRequerimientos(List<Cotizacion> listaCotizaciones) {
		this.listaCotizaciones = listaCotizaciones;
	}

	public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}


	public Cotizacion getCotizacionFiltro() {
		return cotizacionFiltro;
	}

	public void setCotizacionFiltro(Cotizacion cotizacionFiltro) {
		this.cotizacionFiltro = cotizacionFiltro;
	}

	public List<Cotizacion> getListaCotizacionesSeleccionadas() {
		return listaCotizacionesSeleccionadas;
	}

	public void setListaCotizacionesSeleccionadas(
			List<Cotizacion> listaCotizacionesSeleccionadas) {
		this.listaCotizacionesSeleccionadas = listaCotizacionesSeleccionadas;
	}
	
	

}
