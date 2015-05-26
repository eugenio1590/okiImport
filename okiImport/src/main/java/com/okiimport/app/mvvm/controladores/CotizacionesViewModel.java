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

import com.okiimport.app.configuracion.servicios.SControlUsuario;
import com.okiimport.app.modelo.Cliente;
import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

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
	private static final int PAGE_SIZE = 3;
	
	private Usuario usuario;
	private Cotizacion cotizacionFiltro;
	private Requerimiento requerimiento;

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
		pagCotizaciones.setPageSize(PAGE_SIZE);
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
	/*
	 * Descripcion: permitira cambiar los requerimientos de la grid de acuerdo a la pagina dada como parametro
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaRequerimientosCotizados")
	public void cambiarCotizaciones(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.ConsultarCotizacionesRequerimiento(cotizacionFiltro, fieldSort, sortDirection, requerimiento.getIdRequerimiento(), page, PAGE_SIZE);
		Integer total = (Integer) parametros.get("total");
		listaCotizaciones = (List<Cotizacion>) parametros.get("cotizaciones");
		gridCotizaciones.setMultiple(true);
		gridCotizaciones.setCheckmark(true);
		pagCotizaciones.setActivePage(page);
		pagCotizaciones.setTotalSize(total);
	}
	
	/**COMMAND*/
	/*
	 * Descripcion: permitira cambiar la paginacion de acuerdo a la pagina activa del Paging
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagCotizaciones.getActivePage();
		cambiarCotizaciones(page, null, null);
	}
	
	/*
	 * Descripcion: permitira filtrar los datos de la grid de acuerdo al campo establecido en el evento
	 * @param Ninguno
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange("listaCotizaciones")
	public void aplicarFiltro(){
		cambiarCotizaciones(0, null, null);
	}
	
	/*
	 * Descripcion: permitira crear el emergente (modal) necesario para editar el requerimiento seleccionado
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 * */
	@Command
	public void editarReguerimiento(@BindingParam("requerimiento") Requerimiento requerimiento){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("requerimiento", requerimiento);
		crearModal("/WEB-INF/views/sistema/funcionalidades/editarRequerimiento.zul", parametros);
	}
	
	@Command
	public void verDetalleCotizacion(@BindingParam("cotizacion") Cotizacion cotizacion){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cotizacion", cotizacion);
		crearModal("/WEB-INF/views/sistema/funcionalidades/detalleCotizacion.zul", parametros);
	}
	
	@NotifyChange("*")
	@Command
	public void aprobar(@BindingParam("cotizacion") Cotizacion cotizacion){
		
		if (cotizacion != null){
			System.out.println("Cotizacion");
			cotizacion.setEstatus("A");
			sTransaccion.ActualizarCotizacion(cotizacion);
			cambiarCotizaciones(0, null, null);
		}
		
		else if (listaCotizacionesSeleccionadas.size() > 0) { 
			System.out.println("CotizacionSeleccionada");
			for (Cotizacion cotizacionSeleccionada:listaCotizacionesSeleccionadas){
				cotizacionSeleccionada.setEstatus("A");
				sTransaccion.ActualizarCotizacion(cotizacionSeleccionada);
			}
			cambiarCotizaciones(0, null, null);
			listaCotizacionesSeleccionadas.clear();
		}
		else mostrarMensaje("Informacion","Seleccione al menos una Cotización",null,null,null,null);
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
