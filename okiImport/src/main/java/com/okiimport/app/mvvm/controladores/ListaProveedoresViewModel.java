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

import com.okiimport.app.model.Proveedor;
import com.okiimport.app.mvvm.AbstractRequerimientoViewModel;
import com.okiimport.app.mvvm.resource.BeanInjector;
import com.okiimport.app.service.maestros.SMaestros;

public class ListaProveedoresViewModel extends AbstractRequerimientoViewModel implements EventListener<SortEvent>{
	
	//Servicios
	@BeanInjector("sMaestros")
	protected SMaestros sMaestros;
	
	//GUI
	@Wire("#gridProveedores")
	private Listbox gridProveedores;
	
	@Wire("#pagProveedores")
	protected Paging pagProveedores;
	
	Window window = null;
	int idcount = 0;
	private boolean makeAsReadOnly;

	
	//Modelos
	protected List<Proveedor> proveedores;
	protected Proveedor proveedorFiltro;
	
	//Atributos

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
	@Command
	@NotifyChange("*")
	public void paginarLista(){
		int page=pagProveedores.getActivePage();
		cambiarProveedores(page, null, null);
	}
	
	@Command
	@NotifyChange("*")
	public void aplicarFiltro(){
		cambiarProveedores(0, null, null);
	}
	
	/*@Command
	@NotifyChange("analistas")
	public void limpiarRadios(){
		this.analistaFiltro.setActivo(null);
		radEstado.setSelectedIndex(-1);
		aplicarFiltro();
	}*/
	
	@Command
	public void nuevoProveedor(){
		llamarFormulario("/WEB-INF/views/sistema/maestros/formularioProveedor.zul", null);
	}
	
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
	
	@NotifyChange("proveedores")
	@Command
	public void eliminarProveedor(@BindingParam("proveedor") Proveedor proveedor){
		proveedor.setEstatus("eliminado");
		sMaestros.acutalizarPersona(proveedor);
	}
	
	/*@Command
	public void editarAnalista(@BindingParam("analista") Analista analista){
		Usuario userSession = consultarUsuarioSession();
		if(userSession.getId()!=analista.getId()){
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("analista", analista);
			llamarFormulario("editarAnalista.zul", parametros);
		}
		else
			mostrarMensaje("Error", "No se puede Editar el Usuario de la Session", Messagebox.ERROR, null, null, null);
	}*/
	
	/*@Command
	@NotifyChange("analistas")
	public void actualizarEstado(@BindingParam("usuario") Usuario usuario, @BindingParam("estado") Boolean estado){
		Usuario userSession = consultarUsuarioSession();
		if(userSession.getId()!=usuario.getId()){
			if(sControlUsuario.cambiarEstadoUsuario(usuario, estado)){
				String mensaje = (estado) ? "Activado" : "Desactivado";
				mostrarMensaje("Informacion", "Usuario "+mensaje+" Exitosamente", null, null, null, null);
				paginarLista();
			}
		}
		else
			mostrarMensaje("Error", "No se puede Desactivar el Usuario de la Session", Messagebox.ERROR, null, null, null);
	}*/
	
	/**METODOS PROPIOS DE LA CLASE*/
	/*private Usuario consultarUsuarioSession(){
		UserDetails user = this.getUser();
		return sControlUsuario.consultarUsuario(user.getUsername(), user.getPassword());
	}*/
	
	private void llamarFormulario(String ruta, Map<String, Object> parametros){
		crearModal("/WEB-INF/views/sistema/maestros/"+ruta, parametros);
	}

	/**SETTERS Y GETTERS*/
	/*public SControlUsuario getsControlUsuario() {
		return sControlUsuario;
	}

	public void setsControlUsuario(SControlUsuario sControlUsuario) {
		this.sControlUsuario = sControlUsuario;
	}*/
	

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