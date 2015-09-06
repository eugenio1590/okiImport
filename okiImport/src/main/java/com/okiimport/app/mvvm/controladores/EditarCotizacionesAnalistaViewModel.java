package com.okiimport.app.mvvm.controladores;

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

import com.okiimport.app.modelo.Cotizacion;
import com.okiimport.app.modelo.Persona;
import com.okiimport.app.modelo.Requerimiento;
import com.okiimport.app.modelo.Usuario;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;
import com.okiimport.app.transaccion.servicios.STransaccion;

public class EditarCotizacionesAnalistaViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sTransaccion")
	private STransaccion sTransaccion;
	
	//GUI
	@Wire("#gridCotizaciones")
	private Listbox gridCotizaciones;
	
	@Wire("#pagCotizaciones")
	private Paging pagCotizaciones;
	
	//Atributos
	private static String titulo = "Cotizacion del Requerimiento Nro ";
	
	private String constraint_precio_flete;
	
	private List<Cotizacion> listaCotizacion;
	
	private Requerimiento requerimiento;
	private Cotizacion cotizacionFiltro;
	private Cotizacion cotizacionSelecionada=null;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("requerimiento") Requerimiento requerimiento){
		super.doAfterCompose(view);
		this.requerimiento = requerimiento;
		this.requerimiento.especificarInformacionVehiculo();
		cotizacionFiltro = new Cotizacion();
		titulo = titulo + requerimiento.getIdRequerimiento();
		cambiarCotizaciones(0, null, null);
		agregarGridSort(gridCotizaciones);
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
	/*
	 * Descripcion: permitira cambiar las cotizaciones de la grid de acuerdo a la pagina dada como parametro
	 * @param page: pagina a consultar, si no se indica sera 0 por defecto
	 * @param fieldSort: campo de ordenamiento, puede ser nulo
	 * @param sorDirection: valor boolean que indica el orden ascendente (true) o descendente (false) del ordenamiento
	 * Retorno: Ninguno
	 * */
	@GlobalCommand
	@SuppressWarnings("unchecked")
	@NotifyChange("listaCotizacion")
	public void cambiarCotizaciones(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sTransaccion.consultarCotizacionesParaEditar(cotizacionFiltro, fieldSort, 
				sortDirection, requerimiento.getIdRequerimiento(), page, pageSize);
		Integer total = (Integer) parametros.get("total");
		listaCotizacion = (List<Cotizacion>) parametros.get("cotizaciones");
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
	@NotifyChange("listaCotizacion")
	public void aplicarFiltro(){
		cambiarCotizaciones(0, null, null);
	}
	
	/*
	 * Descripcion: permitira cargar la lista de detalles de la cotizacion seleccionada
	 * @param requerimiento: requerimiento seleccionado
	 * Retorno: Ninguno
	 * */
	@Command
	@NotifyChange({"listaDetalleCotizacion","cotizacionSelecionada"})
	public void cotizar(@BindingParam("cotizacion") Cotizacion cotizacion){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("persona", cotizacion.getProveedor());
		parametros.put("requerimiento", this.requerimiento);
		parametros.put("cotizacion", cotizacion);
		parametros.put("obligatorioTodosCampos", true);
		crearModal("/WEB-INF/views/sistema/funcionalidades/cotizarProveedorInternacional.zul", parametros);
	}
	
	/*
	 * Descripcion: Permitira cargar nuevamente las listas al cerrar la pantalla
	 * @param: Ninguno
	 * Retorno: Ninguno
	 */
	@Command
	public void onCloseWindow(){
		ejecutarGlobalCommand("cambiarRequerimientos", null);
	}
	
	/**METODOS PROPIOS DE LA CLASE*/
	
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

	public String getConstraint_precio_flete() {
		return constraint_precio_flete;
	}

	public void setConstraint_precio_flete(String constraint_precio_flete) {
		this.constraint_precio_flete = constraint_precio_flete;
	}
}