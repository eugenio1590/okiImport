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
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.okiimport.app.maestros.servicios.SMaestros;
import com.okiimport.app.modelo.Proveedor;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.BeanInjector;

public class ListaProveedoresViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sMaestros")
	protected SMaestros sMaestros;
	
	//GUI
	@Wire("#gridProveedores")
	private Listbox gridProveedores;
	
	@Wire("#pagProveedores")
	protected Paging pagProveedores;
	
	//Atributos
	
	Window window = null;
	int idcount = 0;
	private boolean makeAsReadOnly;
	protected List<Proveedor> proveedores;
	protected Proveedor proveedorFiltro;

	/**
	 * Descripcion: Llama a inicializar la clase 
	 * Parametros: @param view: listaProveedores.zul 
	 * Retorno: Clase Inicializada 
	 * Nota: Ninguna
	 * */
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view){
		super.doAfterCompose(view);
		proveedorFiltro = new Proveedor();
		pagProveedores.setPageSize(pageSize);
		agregarGridSort(gridProveedores);
		cambiarProveedores(0, null, null);
	}
	
	/**Interface: EventListener<SortEvent>*/
	@Override
	@NotifyChange("proveedores")
	public void onEvent(SortEvent event) throws Exception {
		// TODO Auto-generated method stub		
		if(event.getTarget() instanceof Listheader){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fieldSort",  event.getTarget().getId().toString());
			parametros.put("sortDirection", event.isAscending());
			ejecutarGlobalCommand("cambiarProveedores", parametros );
		}
		
	}
	
	/**GLOBAL COMMAND*/
	/**
	 * Descripcion: Llama a consultar proveedores  
	 * Parametros: @param view: listaProveedores.zul 
	 * Retorno: proveedores consultados
	 * Nota: Ninguna
	 * */
	@GlobalCommand
	@NotifyChange("proveedores")
	public void cambiarProveedores(@Default("0") @BindingParam("page") int page, 
			@BindingParam("fieldSort") String fieldSort, 
			@BindingParam("sortDirection") Boolean sortDirection){
		Map<String, Object> parametros = sMaestros.consultarProveedores(proveedorFiltro, page, pageSize);
		Integer total = (Integer) parametros.get("total");
		proveedores = (List<Proveedor>) parametros.get("proveedores");
		pagProveedores.setActivePage(page);
		pagProveedores.setTotalSize(total);
	}
	
	/**COMMAND*/
	/**
	 * Descripcion: Permitira cambiar la paginacion de acuerdo a la pagina activa del Paging 
	 * Parametros: @param view: listaProveedores.zul   
	 * Retorno: Ninguno
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagProveedores.getActivePage();
		cambiarProveedores(page, null, null);
	}
	
	/**
	 * Descripcion: Permitira filtrar por los campos del proveedor
	 * Parametros: @param view: listaProveedores.zul   
	 * Retorno: Campos filtrados
	 * Nota: Ninguna
	 * */
	@Command
	@NotifyChange("*")
	public void aplicarFiltro(){
		cambiarProveedores(0, null, null);
	}
	
	/**
	 * Descripcion: Llama a un modal para crear o registrar un proveedor
	 * Parametros: @param view: listaProveedores.zul 
	 * Retorno: Formulario Proveedor Cargado para registrar un nuevo proveedor
	 * Nota: Ninguna
	 * */
	@Command
	public void nuevoProveedor(){
		llamarFormulario("/WEB-INF/views/sistema/maestros/formularioProveedor.zul", null);
	}
	
	/**
	 * Descripcion: Llama a un modal para editar los datos del proveedor
	 * Parametros: Proveedor @param view: listaProveedores.zul 
	 * Retorno: Modal cargado con los datos del proveedor
	 * Nota: Ninguna
	 * */
	@Command
	public void editarProveedor(@BindingParam("proveedor") Proveedor proveedor){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("proveedor", proveedor);
		map.put("recordMode", "EDIT");
		map.put("cerrar", false);
		Sessions.getCurrent().setAttribute("allmyvalues", map);
		if (window != null) {
			window.detach();
			window.setId(null);
		}
		window = (Window) Executions.createComponents(
				"/WEB-INF/views/sistema/maestros/formularioProveedor.zul", null, map);
		window.setMaximizable(true);
		window.doModal();
		window.setId("doModal" + "" + idcount + "");
		
		
	}
	
	
	/**
	 * Descripcion: Llama a un modal para ver los datos del proveedor
	 * Parametros: Proveedor @param view: listaProveedores.zul 
	 * Retorno: Modal cargado con los datos del proveedor
	 * Nota: Ninguna
	 * */
	@Command
	public void verProveedor(
			@BindingParam("proveedor") Proveedor proveedor) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("proveedor", proveedor);
		map.put("recordMode", "READ");
		map.put("cerrar", false);
		Sessions.getCurrent().setAttribute("allmyvalues", map);
		if (window != null) {
			window.detach();
			window.setId(null);
		}
		window = (Window) Executions.createComponents(
				"/WEB-INF/views/sistema/maestros/formularioProveedor.zul", null, map);
		window.setMaximizable(true);
		window.doModal();
		window.setId("doModal" + "" + idcount + "");
	}
	
	/**
	 * Descripcion: Llama a un modal para eliminar los datos del proveedor
	 * Parametros: Proveedor @param view: listaProveedores.zul 
	 * Retorno: Modal cargado con los datos del proveedor
	 * Nota: Ninguna
	 * */
	@NotifyChange("proveedores")
	@Command
	public void eliminarProveedor(@BindingParam("proveedor") Proveedor proveedor){
		proveedor.setEstatus("eliminado");
		sMaestros.acutalizarPersona(proveedor);
	}
	
	
	/**METODOS PROPIOS DE LA CLASE*/
	
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/sistema/maestros/"+ruta, parametros);
	}

	/**SETTERS Y GETTERS*/


	@Command
	public void registrarProveedor(){
		window = crearModal("/WEB-INF/views/sistema/maestros/formularioProveedor.zul", null);
		window.setMaximizable(true);
	}

	public SMaestros getsMaestros() {
		return sMaestros;
	}

	public List<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public Proveedor getProveedorFiltro() {
		return proveedorFiltro;
	}

	public void setProveedorFiltro(Proveedor proveedorFiltro) {
		this.proveedorFiltro = proveedorFiltro;
	}

	public void setsMaestros(SMaestros sMaestros) {
		this.sMaestros = sMaestros;
	}

	public boolean isMakeAsReadOnly() {
		return makeAsReadOnly;
	}

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}
	
	
}